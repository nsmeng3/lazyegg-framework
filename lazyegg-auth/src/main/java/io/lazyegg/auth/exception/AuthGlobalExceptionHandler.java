package io.lazyegg.auth.exception;

import com.alibaba.cola.dto.Response;
import io.lazyegg.auth.util.LeggUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;

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
        String currentUsername = LeggUserUtil.getCurrentUsername();
        Authentication authentication = LeggUserUtil.getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        log.error("{} - {} - {}", currentUsername, authorities, exception.getMessage());
        return new ResponseEntity<>(Response.buildFailure("403", "权限不足，请联系管理员"), HttpStatus.FORBIDDEN);
    }

    /**
     * 授权令牌未找到异常
     */
    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Object> authenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException exception) {
        log.error("{}", exception.getMessage(), exception);
        return new ResponseEntity<>(Response.buildFailure("400", "接口权限存在异常，请联系管理员"), HttpStatus.FORBIDDEN);
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception exception) {
        log.error("{}", exception.getMessage(), exception);
        return ResponseEntity.internalServerError()
                .body(Response.buildFailure("500", "系统异常，请联系管理员"));
    }
}
