package io.lazyegg.sdk.demo;

import io.lazyegg.sdk.ClientConfiguration;
import io.lazyegg.sdk.CredentialsProvider;
import io.lazyegg.sdk.SDKClient;

import java.net.URI;

public class DemoClient extends SDKClient {

    private DemoSDKOperation demoSDKOperation;
    public DemoClient(String endpoint, CredentialsProvider credentialsProvider, ClientConfiguration config) {
        super(endpoint, credentialsProvider, config);
    }

    @Override
    public void setEndpoint(URI endpoint) {
        this.demoSDKOperation.setEndpoint(endpoint);
    }

    @Override
    protected void initOperations() {
        this.demoSDKOperation = new DemoSDKOperation(this.serviceClient, this.credentialsProvider);
    }

    public DemoSDKOperation demoSDKOperation() {
        return this.demoSDKOperation;
    }


}
