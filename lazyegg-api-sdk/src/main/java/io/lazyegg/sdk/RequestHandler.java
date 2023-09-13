package io.lazyegg.sdk;

public interface RequestHandler {
    void handle(RequestMessage request) throws ClientException;

}
