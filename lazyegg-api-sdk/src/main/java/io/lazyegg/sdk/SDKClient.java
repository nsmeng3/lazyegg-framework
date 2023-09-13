package io.lazyegg.sdk;

import io.lazyegg.sdk.utils.SDKUtils;
import lombok.Data;

import java.net.URI;

@Data
public abstract class SDKClient {

    protected CredentialsProvider credentialsProvider;

    protected URI endpoint;

    protected ServiceClient serviceClient;


    public SDKClient(String endpoint, CredentialsProvider credentialsProvider, ClientConfiguration config) {
        this.credentialsProvider = credentialsProvider;
        config = config == null ? new ClientConfiguration() : config;
        this.serviceClient = new DefaultServiceClient(config);
        initOperations();
        setEndpoint(endpoint);
    }

    private void setEndpoint(String endpoint) {
        URI uri = toUri(endpoint);
        setEndpoint(uri);
    }

    private URI toUri(String endpoint) {
        return SDKUtils.toEndpointURI(endpoint, this.serviceClient.getClientConfiguration().getProtocol().toString());
    }

    /**
     * 设置 端点地址，给operations
     * @param endpoint
     */
    public abstract void setEndpoint(URI endpoint);

    /**
     * 初始化操作
     * 比如，一个系统中包含组织和用户的开放接口，
     * 即可分别创建组织和用户的操作类
     */
    protected abstract void initOperations();
}
