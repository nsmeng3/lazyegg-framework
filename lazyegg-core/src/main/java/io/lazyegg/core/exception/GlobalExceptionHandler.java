package io.lazyegg.core.exception;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ExceptionHandler
 *
 * @author DifferentW  nsmeng3@163.com
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BizException.class)
    public ResponseEntity<Object> bizException(BizException exception) {
        return new ResponseEntity<>(Response.buildFailure("400", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {SysException.class, RuntimeException.class, HttpMessageConversionException.class})
    public ResponseEntity<Object> sysException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(Response.buildFailure("500", "系统异常请联系管理员"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
