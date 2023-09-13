package io.lazyegg.sdk;

import lombok.Getter;

@Getter
public class APIException extends ServiceException {
    public APIException() {
    }

    public APIException(String errorMessage) {
        super(errorMessage);
    }

    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIException(Throwable cause) {
        super(cause);
    }

    public APIException(String errorMessage, String errorCode, String requestId, String rawResponseError, Throwable cause) {
        super(errorMessage, errorCode, requestId, rawResponseError, cause);
    }
}
