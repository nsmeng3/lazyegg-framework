package io.lazyegg.core.exception;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<SingleResponse> runtimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(SingleResponse.buildFailure("500", "服务异常请联系管理员"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BizException.class)
    public ResponseEntity<SingleResponse> bizException(BizException exception) {
        return new ResponseEntity<>(SingleResponse.buildFailure("500", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = SysException.class)
    public ResponseEntity<SingleResponse> sysException(SysException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(SingleResponse.buildFailure("500", "系统异常请联系管理员"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

