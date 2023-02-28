package io.lazyegg.auth.exception;

import com.alibaba.cola.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * AuthGlobalExceptionHandler
 *
 * @author DifferentW  nsmeng3@163.com
 */

@RestControllerAdvice
public class AuthGlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AuthGlobalExceptionHandler.class);

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedException(AccessDeniedException exception) {
        log.error("{}", exception.getMessage(), exception);
        return new ResponseEntity<>(Response.buildFailure("403", "访问权限不足，请联系管理员"), HttpStatus.FORBIDDEN);
    }
}

