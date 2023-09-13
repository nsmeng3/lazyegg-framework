package io.lazyegg.sdk;

/**
 * 请求签名
 */
public interface RequestSigner {
    void sign(RequestMessage request) throws ClientException;

}
