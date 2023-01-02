package io.lazyegg.auth.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

/**
 * InvalidTokenException
 *
 * @author DifferentW  nsmeng3@163.com
 */

public class InvalidTokenException extends AuthenticationException {
    private static final Logger log = LoggerFactory.getLogger(InvalidTokenException.class);

    public InvalidTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidTokenException() {
        super("无效令牌");
    }
}

