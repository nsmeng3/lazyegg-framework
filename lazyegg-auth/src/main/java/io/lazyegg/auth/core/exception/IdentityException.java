package io.lazyegg.auth.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IdentityException
 * 身份异常
 *
 * @author DifferentW  nsmeng3@163.com
 */

public class IdentityException extends RuntimeException{

    public IdentityException() {
        super("身份异常");
    }

    public IdentityException(Throwable cause) {
        super("身份异常", cause);
    }
}

