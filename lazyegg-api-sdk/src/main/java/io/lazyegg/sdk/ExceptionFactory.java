package io.lazyegg.sdk;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.NonRepeatableRequestException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static io.lazyegg.sdk.utils.SDKUtils.COMMON_RESOURCE_MANAGER;

/**
 * A simple factory used for creating instances of <code>ClientException</code>
 * and <code>APIException</code>.
 */
public class ExceptionFactory {

    public static ClientException createNetworkException(IOException ex) {
        String requestId = "Unknown";
        String errorCode = ClientErrorCode.UNKNOWN;

        if (ex instanceof SocketTimeoutException) {
            errorCode = ClientErrorCode.SOCKET_TIMEOUT;
        } else if (ex instanceof SocketException) {
            errorCode = ClientErrorCode.SOCKET_EXCEPTION;
        } else if (ex instanceof ConnectTimeoutException) {
            errorCode = ClientErrorCode.CONNECTION_TIMEOUT;
        } else if (ex instanceof UnknownHostException) {
            errorCode = ClientErrorCode.UNKNOWN_HOST;
        } else if (ex instanceof HttpHostConnectException) {
            errorCode = ClientErrorCode.CONNECTION_REFUSED;
        } else if (ex instanceof NoHttpResponseException) {
            errorCode = ClientErrorCode.CONNECTION_TIMEOUT;
        } else if (ex instanceof SSLException) {
            errorCode = ClientErrorCode.SSL_EXCEPTION;
        } else if (ex instanceof ClientProtocolException) {
            Throwable cause = ex.getCause();
            if (cause instanceof NonRepeatableRequestException) {
                errorCode = ClientErrorCode.NONREPEATABLE_REQUEST;
                return new ClientException(cause.getMessage(), errorCode, requestId, cause);
            }
        }

        return new ClientException(ex.getMessage(), errorCode, requestId, ex);
    }
//
    public static APIException createInvalidResponseException(String requestId, Throwable cause) {
        return createInvalidResponseException(requestId,
                COMMON_RESOURCE_MANAGER.getFormattedString("FailedToParseResponse", cause.getMessage()));
    }

    public static APIException createInvalidResponseException(String requestId, String rawResponseError,
            Throwable cause) {
        return createInvalidResponseException(requestId,
                COMMON_RESOURCE_MANAGER.getFormattedString("FailedToParseResponse", cause.getMessage()),
                rawResponseError);
    }

    public static APIException createInvalidResponseException(String requestId, String message) {
        return createAPIException(requestId, ErrorCode.INVALID_RESPONSE, message);
    }

    private static APIException createAPIException(String requestId, String invalidResponse, String message) {
        return new APIException(message, ErrorCode.INVALID_RESPONSE, requestId, invalidResponse, null);
    }

    public static APIException createInvalidResponseException(String requestId, String message,
            String rawResponseError) {
        return createAPIException(requestId, message, rawResponseError);
    }


}
