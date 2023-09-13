package io.lazyegg.sdk.demo;

import io.lazyegg.sdk.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoSDKOperation extends SDKOperation {
    public DemoSDKOperation(ServiceClient client, CredentialsProvider credentialsProvider) {
        super(client, credentialsProvider);
    }

    public void getUser() {
        RequestMessage requestMessage = new RequestMessageBuilder(client)
                .setEndpoint(this.endpoint)
                .setMethod(HttpMethod.GET)
                .setOriginalRequest(new GenericRequest())
                .build();
        String s = doOperation(requestMessage, new ToStringResponseParser(), true);
        log.info(s);
    }
}
