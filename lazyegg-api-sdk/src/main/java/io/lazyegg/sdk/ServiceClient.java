package io.lazyegg.sdk;


import com.aliyun.oss.common.utils.HttpUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.aliyun.oss.common.utils.LogUtils.getLog;
import static com.aliyun.oss.internal.OSSUtils.COMMON_RESOURCE_MANAGER;
import static io.lazyegg.sdk.utils.LogUtils.logException;

/**
 * 发送请求流程
 * - 获取重试策略
 * - 加载签名提供器
 * - 请求处理
 * - 构建请求
 * - 发送请求 - 接口
 * - 响应处理
 *
 * @author DifferentW nsmeng3@163.com
 */
@Slf4j
public abstract class ServiceClient {
    protected ClientConfiguration config;

    protected ServiceClient(ClientConfiguration config) {
        this.config = config;
    }

    public ClientConfiguration getClientConfiguration() {
        return this.config;
    }

    public ResponseMessage sendRequest(RequestMessage request, ExecutionContext context) throws ClientException {
        assert (request != null);
        assert (context != null);
        try {
            return sendRequestImpl(request, context);
        } finally {
            // 请求完成关闭请求流
            try {
                request.close();
            } catch (IOException ex) {
                logException("Unexpected io exception when trying to close http request: ", ex);
                throw new ClientException("关闭http request时异常: ", ex);
            }
        }
    }

    private ResponseMessage sendRequestImpl(RequestMessage request, ExecutionContext context) throws ClientException {
        RetryStrategy retryStrategy = context.getRetryStrategy() != null ? context.getRetryStrategy()
                : this.getDefaultRetryStrategy();

        // Sign the request if a signer provided.
        if (context.getSigner() != null && !request.isUseUrlSignature()) {
            context.getSigner().sign(request);
        }

        for (RequestSigner signer : context.getSignerHandlers()) {
            signer.sign(request);
        }

        InputStream requestContent = request.getContent();
        if (requestContent != null && requestContent.markSupported()) {
            requestContent.mark(SDKConstants.DEFAULT_STREAM_BUFFER_SIZE);
        }

        int retries = 0;
        ResponseMessage response = null;

        while (true) {
            try {
                if (retries > 0) {
                    pause(retries, retryStrategy);
                    if (requestContent != null && requestContent.markSupported()) {
                        try {
                            requestContent.reset();
                        } catch (IOException ex) {
                            logException("Failed to reset the request input stream: ", ex);
                            throw new ClientException("Failed to reset the request input stream: ", ex);
                        }
                    }
                }

                // Step 1. Preprocess HTTP request.
                handleRequest(request, context.getRequestHandlers());

                // Step 2. Build HTTP request with specified request parameters
                // and context.
                Request httpRequest = buildRequest(request, context);

                // Step 3. Send HTTP request to Server.
//                String poolStatsInfo = config.isLogConnectionPoolStatsEnable() ? "Connection pool stats " + getConnectionPoolStats() : "";
                String poolStatsInfo = true ? "Connection pool stats " + getConnectionPoolStats() : "";
                long startTime = System.currentTimeMillis();
                response = requestCore(httpRequest, context);
                long duration = System.currentTimeMillis() - startTime;
                if (duration > config.getSlowRequestsThreshold()) {
                    log.warn(formatSlowRequestLog(request, response, duration) + poolStatsInfo);
                }

                // Step 4. Preprocess HTTP response.
                handleResponse(response, context.getResponseHandlers());

                return response;
            } catch (ServiceException sex) {
                logException("[Server]Unable to execute HTTP request: ", sex,
                        request.getOriginalRequest().isLogEnabled());

                // Notice that the response should not be closed in the
                // finally block because if the request is successful,
                // the response should be returned to the callers.
                closeResponseSilently(response);

                if (!shouldRetry(sex, request, response, retries, retryStrategy)) {
                    throw sex;
                }
            } catch (ClientException cex) {
                logException("[Client]Unable to execute HTTP request: ", cex,
                        request.getOriginalRequest().isLogEnabled());

                closeResponseSilently(response);

                if (!shouldRetry(cex, request, response, retries, retryStrategy)) {
                    throw cex;
                }
            } catch (Exception ex) {
                logException("[Unknown]Unable to execute HTTP request: ", ex,
                        request.getOriginalRequest().isLogEnabled());

                closeResponseSilently(response);

                throw new ClientException("ConnectionError", ex);
            } finally {
                retries++;
            }
        }
    }

    private void handleResponse(ResponseMessage response, List<ResponseHandler> responseHandlers) {
        for (ResponseHandler h : responseHandlers) {
            h.handle(response);
        }
    }

    protected abstract ResponseMessage requestCore(Request request, ExecutionContext context) throws IOException;


    private Request buildRequest(RequestMessage requestMessage, ExecutionContext context) {
        Request request = new Request();
        request.setMethod(requestMessage.getMethod());
//        request.setUseChunkEncoding(requestMessage.isUseChunkEncoding());

        if (requestMessage.isUseUrlSignature()) {
            request.setUrl(requestMessage.getAbsoluteUrl().toString());
            request.setUseUrlSignature(true);

            request.setContent(requestMessage.getContent());
            request.setContentLength(requestMessage.getContentLength());
            request.setHeaders(requestMessage.getHeaders());

            return request;
        }


        request.setHeaders(requestMessage.getHeaders());
        // The header must be converted after the request is signed,
        // otherwise the signature will be incorrect.
        if (request.getHeaders() != null) {
            com.aliyun.oss.common.utils.HttpUtil.convertHeaderCharsetToIso88591(request.getHeaders());
        }

        final String delimiter = "/";
        String uri = requestMessage.getEndpoint().toString();
        if (!uri.endsWith(delimiter) && (requestMessage.getResourcePath() == null
                || !requestMessage.getResourcePath().startsWith(delimiter))) {
            uri += delimiter;
        }

        if (requestMessage.getResourcePath() != null) {
            uri += requestMessage.getResourcePath();
        }

        String paramString = HttpUtil.paramToQueryString(requestMessage.getParameters(), context.getCharset());

        /*
         * For all non-POST requests, and any POST requests that already have a
         * payload, we put the encoded params directly in the URI, otherwise,
         * we'll put them in the POST request's payload.
         */
        boolean requestHasNoPayload = requestMessage.getContent() != null;
        boolean requestIsPost = requestMessage.getMethod() == HttpMethod.POST;
        boolean putParamsInUri = !requestIsPost || requestHasNoPayload;
        if (paramString != null && putParamsInUri) {
            uri += "?" + paramString;
        }

        request.setUrl(uri);

        if (requestIsPost && requestMessage.getContent() == null && paramString != null) {
            // Put the param string to the request body if POSTing and
            // no content.
            try {
                byte[] buf = paramString.getBytes(context.getCharset());
                ByteArrayInputStream content = new ByteArrayInputStream(buf);
                request.setContent(content);
                request.setContentLength(buf.length);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        COMMON_RESOURCE_MANAGER.getFormattedString("EncodingFailed", e.getMessage()));
            }
        } else {
            request.setContent(requestMessage.getContent());
            request.setContentLength(requestMessage.getContentLength());
        }

        return request;
    }

    private void handleRequest(RequestMessage request, List<RequestHandler> requestHandlers) {
        for (RequestHandler h : requestHandlers) {
            h.handle(request);
        }
    }

    private boolean shouldRetry(Exception exception, RequestMessage request, ResponseMessage response, int retries,
                                RetryStrategy retryStrategy) {

        if (retries >= config.getMaxErrorRetry()) {
            return false;
        }

        if (!request.isRepeatable()) {
            return false;
        }

        if (retryStrategy.shouldRetry(exception, request, response, retries)) {
            getLog().debug("Retrying on " + exception.getClass().getName() + ": " + exception.getMessage());
            return true;
        }
        return false;
    }

    public String getConnectionPoolStats() {
        log.warn("getConnectionPoolStats is not implemented");
        return "";
    }

    private String formatSlowRequestLog(RequestMessage request, ResponseMessage response, long useTimesMs) {
        return String.format(
                "Request cost %d seconds, endpoint %s, resourcePath %s, " + "method %s, Date '%s', statusCode %d, requestId %s.",
                useTimesMs / 1000, request.getEndpoint(), request.getResourcePath(), request.getMethod(), request.getHeaders().get(HttpHeaders.DATE),
                response.getStatusCode(), response.getRequestId());
    }

    private void pause(int retries, RetryStrategy retryStrategy) {
        long delay = retryStrategy.getPauseDelay(retries);
        log.debug(
                "An retriable error request will be retried after " + delay + "(ms) with attempt times: " + retries);
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new ClientException(e.getMessage(), e);
            }
        }
    }

    private void closeResponseSilently(ResponseMessage response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException ioe) {
                /* silently close the response. */
            }
        }
    }

    protected abstract RetryStrategy getDefaultRetryStrategy();

    @Getter
    @Setter
    public static class Request extends HttpMessage {
        private String uri;
        private HttpMethod method;
        private boolean useUrlSignature = false;
//        private boolean useChunkEncoding = false;

        public void setUrl(String uri) {
            this.uri = uri;
        }
    }
}
