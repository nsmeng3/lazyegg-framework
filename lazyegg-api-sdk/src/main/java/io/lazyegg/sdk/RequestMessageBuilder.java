package io.lazyegg.sdk;

import lombok.Getter;

import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class RequestMessageBuilder {

    private URI endpoint;
    private String resourcePath;
    private HttpMethod method = HttpMethod.GET;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> parameters = new LinkedHashMap<String, String>();
    private InputStream inputStream;
    private ServiceClient innerClient;
    private WebServiceRequest originalRequest;

    public RequestMessageBuilder(ServiceClient innerClient) {
        this.innerClient = innerClient;
    }

    public RequestMessageBuilder setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public RequestMessageBuilder setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        return this;
    }

    public RequestMessageBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public RequestMessageBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestMessageBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public RequestMessageBuilder setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public RequestMessageBuilder addParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public RequestMessageBuilder setInputStream(InputStream instream) {
        this.inputStream = instream;
        return this;
    }

    public RequestMessageBuilder setInputStreamWithLength(InputStream instream) {
        this.inputStream = instream;
        return this;
    }

    public RequestMessageBuilder setOriginalRequest(WebServiceRequest originalRequest) {
        this.originalRequest = originalRequest;
        return this;
    }

    public RequestMessage build() {
        ClientConfiguration clientConfig = this.innerClient.getClientConfiguration();
        Map<String, String> sentHeaders = new HashMap<String, String>(this.headers);
        Map<String, String> sentParameters = new LinkedHashMap<String, String>(this.parameters);

        RequestMessage request = new RequestMessage(this.originalRequest);
        request.setEndpoint(this.endpoint);
        request.setResourcePath(this.resourcePath);
        request.setHeaders(sentHeaders);
        request.setParameters(sentParameters);
        request.setMethod(this.method);
        request.setContent(this.inputStream);

        return request;
    }

}
