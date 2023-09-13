package io.lazyegg.sdk;

/**
 * http请求失败不重试
 */
public class NoRetryStrategy extends RetryStrategy {

    @Override
    public boolean shouldRetry(Exception ex, RequestMessage request, ResponseMessage response, int retries) {
        return false;
    }

}
