package io.lazyegg.core.exception;

import com.alibaba.cola.exception.BaseException;
import org.springframework.http.ResponseEntity;

public interface ResponseHandlerI {
    Object handle(Class returnType, String errCode, String errMsg);

    ResponseEntity<Object> handle(Class returnType, BaseException e);
}
