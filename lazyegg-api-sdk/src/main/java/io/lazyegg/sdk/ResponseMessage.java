package io.lazyegg.sdk;

import lombok.Getter;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

@Getter
public class ResponseMessage extends HttpMessage {

    private static final int HTTP_SUCCESS_STATUS_CODE = 200;

    private String uri;
    private int statusCode;

    private final DefaultServiceClient.Request request;
    private CloseableHttpResponse httpResponse;

    // For convenience of logging invalid response
    private String errorResponseAsString;

    public ResponseMessage(DefaultServiceClient.Request request) {
        this.request = request;
    }

    public void setUrl(String uri) {
        this.uri = uri;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestId() {
        return getHeaders().get(Headers.HEADER_REQUEST_ID);
    }

    public boolean isSuccessful() {
        return statusCode / 100 == HTTP_SUCCESS_STATUS_CODE / 100;
    }

    public void setErrorResponseAsString(String errorResponseAsString) {
        this.errorResponseAsString = errorResponseAsString;
    }

    public void abort() throws IOException {
        if (httpResponse != null) {
            httpResponse.close();
        }
    }

    public void setHttpResponse(CloseableHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

}
