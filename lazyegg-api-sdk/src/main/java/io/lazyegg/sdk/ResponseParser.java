package io.lazyegg.sdk;

public interface ResponseParser<T> {
    T parse(ResponseMessage response);
}
