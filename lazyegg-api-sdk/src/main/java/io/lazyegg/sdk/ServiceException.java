package io.lazyegg.sdk;

import lombok.Getter;

/**
 * 服务业务异常
 */
@Getter
public class ServiceException extends RuntimeException {

    private String errorMessage;
    private String errorCode;
    private String requestId;
    private String rawResponseError;

    public ServiceException() {
        super();
    }

    public ServiceException(String errorMessage) {
        super((String) null);
        this.errorMessage = errorMessage;
    }

    public ServiceException(String message, Throwable cause) {
        super(null, cause);
        this.errorMessage = message;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String errorMessage, String errorCode, String requestId, String rawResponseError, Throwable cause) {
        this(errorMessage, cause);
        this.errorCode = errorCode;
        this.requestId = requestId;
        this.rawResponseError = rawResponseError;
    }

    private String formatRawResponseError() {
        if (rawResponseError == null || rawResponseError.isEmpty()) {
            return "";
        }
        return String.format("\n[ResponseError]:\n%s", this.rawResponseError);
    }

    public ServiceException setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ServiceException setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ServiceException setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public ServiceException setRawResponseError(String rawResponseError) {
        this.rawResponseError = rawResponseError;
        return this;
    }

    @Override
    public String getMessage() {
        String msg = getErrorMessage() + "\n[ErrorCode]: " + getErrorCode() +
                "\n[RequestId]: " + getRequestId();
        return msg + formatRawResponseError();
    }
}
