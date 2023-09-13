package io.lazyegg.sdk;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.entity.BasicHttpEntity;

import java.io.InputStream;
import java.util.Map;

class HttpRequestFactory {

    /**
     * 创建http请求
     * 指定请求地址、指定请求方法、指定请求头、指定请求体
     *
     * @param request
     * @param context
     * @return
     */
    public HttpRequestBase createHttpRequest(DefaultServiceClient.Request request, ExecutionContext context) {

        String uri = request.getUri();

        HttpRequestBase httpRequest;
        HttpMethod method = request.getMethod();
        InputStream content = request.getContent();
        if (method == HttpMethod.POST) {
            HttpPost postMethod = new HttpPost(uri);

            if (content != null) {
                BasicHttpEntity entity = new BasicHttpEntity();
                entity.setContent(content);
                postMethod.setEntity(entity);
            }

            httpRequest = postMethod;
        } else if (method == HttpMethod.PUT) {
            HttpPut putMethod = new HttpPut(uri);

            if (content != null) {
                BasicHttpEntity entity = new BasicHttpEntity();
                entity.setContent(content);
                putMethod.setEntity(entity);
            }
            httpRequest = putMethod;
        } else if (method == HttpMethod.GET) {
            httpRequest = new HttpGet(uri);
        } else if (method == HttpMethod.DELETE) {
            httpRequest = new HttpDelete(uri);
        } else if (method == HttpMethod.HEAD) {
            httpRequest = new HttpHead(uri);
        } else if (method == HttpMethod.OPTIONS) {
            httpRequest = new HttpOptions(uri);
        } else {
            throw new ClientException("Unknown HTTP method name: " + method.toString());
        }

        configureRequestHeaders(request, context, httpRequest);

        return httpRequest;
    }

    private HttpEntity buildChunkedInputStreamEntity(DefaultServiceClient.Request request) {
        return new ChunkedInputStreamEntity(request);
    }

    private void configureRequestHeaders(DefaultServiceClient.Request request, ExecutionContext context,
                                         HttpRequestBase httpRequest) {

        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)
                    || entry.getKey().equalsIgnoreCase(HttpHeaders.HOST)) {
                continue;
            }

            httpRequest.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
