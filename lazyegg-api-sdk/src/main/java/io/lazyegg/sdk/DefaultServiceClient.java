package io.lazyegg.sdk;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Slf4j
public class DefaultServiceClient extends ServiceClient {


    public DefaultServiceClient(ClientConfiguration configuration) {
        super(configuration);
    }

    /**
     * 请求核心
     *
     * @param request
     * @param context
     * @return
     * @throws IOException
     */
    protected ResponseMessage requestCore(Request request, ExecutionContext context) throws IOException {
        HttpRequestFactory httpRequestFactory = new HttpRequestFactory();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建 Http 请求
        HttpRequestBase httpRequest = httpRequestFactory.createHttpRequest(request, context);
        // 创建请求上下文
        HttpClientContext httpContext = createHttpContext();
        CloseableHttpResponse httpResponse;
        try {
            // 执行请求
            httpResponse = httpClient.execute(httpRequest, httpContext);
        } catch (IOException ex) {
            log.error("请求错误：{}", ex.getMessage(), ex);
            httpRequest.abort();
            throw ExceptionFactory.createNetworkException(ex);
        }
        // 构建响应
        return buildResponse(request, httpResponse);
    }

    @Override
    protected RetryStrategy getDefaultRetryStrategy() {
        // 默认不重试
        return new NoRetryStrategy();
    }

    /**
     * 构建响应
     *
     * @param request
     * @param httpResponse
     * @return
     * @throws IOException
     */
    protected static ResponseMessage buildResponse(DefaultServiceClient.Request request, CloseableHttpResponse httpResponse)
            throws IOException {

        assert (httpResponse != null);

        ResponseMessage response = new ResponseMessage(request);
        response.setUrl(request.getUri());
        response.setHttpResponse(httpResponse);

        if (httpResponse.getStatusLine() != null) {
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        }

        if (httpResponse.getEntity() != null) {
            if (response.isSuccessful()) {
                response.setContent(httpResponse.getEntity().getContent());
            } else {
                readAndSetErrorResponse(httpResponse.getEntity().getContent(), response);
            }
        }

        for (Header header : httpResponse.getAllHeaders()) {
            if (HttpHeaders.CONTENT_LENGTH.equalsIgnoreCase(header.getName())) {
                response.setContentLength(Long.parseLong(header.getValue()));
            }
            response.addHeader(header.getName(), header.getValue());
        }
        // 请求头编码转换
        HttpUtil.convertHeaderCharsetFromIso88591(response.getHeaders());

        return response;
    }

    /**
     * 读取并设置错误响应
     *
     * @param originalContent
     * @param response
     * @throws IOException
     */
    private static void readAndSetErrorResponse(InputStream originalContent, ResponseMessage response)
            throws IOException {
        byte[] contentBytes = IOUtils.toByteArray(originalContent);
        response.setErrorResponseAsString(new String(contentBytes));
        response.setContent(new ByteArrayInputStream(contentBytes));
    }


    private static HttpClientContext createHttpContext() {
        HttpClientContext httpContext = new HttpClientContext();
        // 请求配置 超时时间 代理等
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .build();
        httpContext.setRequestConfig(requestConfig);
        return httpContext;
    }


}
