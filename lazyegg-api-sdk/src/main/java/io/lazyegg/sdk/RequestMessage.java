package io.lazyegg.sdk;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class RequestMessage extends HttpMessage {

    /**
     * 服务端点
     */
    private URI endpoint;

    private HttpMethod method = HttpMethod.GET;

    private String resourcePath;

    /**
     * url 参数
     */
    private Map<String, String> parameters = new LinkedHashMap<>();

    /**
     * 请求绝对路径
     */
    private URL absoluteUrl;

    /**
     * 是否使用url签名
     */
    private boolean useUrlSignature = false;

    private final WebServiceRequest originalRequest;

    public RequestMessage(WebServiceRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    public boolean isRepeatable() {
        return this.getContent() == null || this.getContent().markSupported();
    }
}
