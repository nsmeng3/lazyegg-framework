package io.lazyegg.sdk.utils;


import io.lazyegg.sdk.ResponseMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.aliyun.oss.internal.OSSUtils.OSS_RESOURCE_MANAGER;
import static io.lazyegg.sdk.SDKConstants.RESOURCE_NAME_COMMON;
import static io.lazyegg.sdk.SDKConstants.RESOURCE_NAME_CUSTOM;

public class SDKUtils {

    public static final ResourceManager CUSTOM_RESOURCE_MANAGER = ResourceManager.getInstance(RESOURCE_NAME_CUSTOM);
    public static final ResourceManager COMMON_RESOURCE_MANAGER = ResourceManager.getInstance(RESOURCE_NAME_COMMON);

    private static final String ENDPOINT_REGEX = "^[a-zA-Z0-9._-]+$";

    public static void safeCloseResponse(ResponseMessage response) {
        try {
            response.close();
        } catch (IOException e) {
        }
    }


    public static URI toEndpointURI(String endpoint, String defaultProtocol) throws IllegalArgumentException {
        if (endpoint != null && !endpoint.contains("://")) {
            endpoint = defaultProtocol + "://" + endpoint;
        }

        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void ensureEndpointValid(String endpoint) {
        if (!validateEndpoint(endpoint)) {
            throw new IllegalArgumentException(
                    OSS_RESOURCE_MANAGER.getFormattedString("EndpointInvalid", endpoint));
        }
    }

    public static boolean validateEndpoint(String endpoint) {
        if (endpoint == null) {
            return false;
        }
        return endpoint.matches(ENDPOINT_REGEX);
    }

    //
//    /**
//     * Validate endpoint.
//     */
//    public static boolean validateEndpoint(String endpoint) {
//        if (endpoint == null) {
//            return false;
//        }
//        return endpoint.matches(ENDPOINT_REGEX);
//    }
//
//    public static void ensureEndpointValid(String endpoint) {
//        if (!validateEndpoint(endpoint)) {
//            throw new IllegalArgumentException(
//                    CUSTOM_RESOURCE_MANAGER.getFormattedString("EndpointInvalid", endpoint));
//        }
//    }
//
//    /**
//     * Validate bucket name.
//     */
//    public static boolean validateBucketName(String bucketName) {
//
//        if (bucketName == null) {
//            return false;
//        }
//
//        return bucketName.matches(BUCKET_NAMING_REGEX);
//    }
//
//    public static void ensureBucketNameValid(String bucketName) {
//        if (!validateBucketName(bucketName)) {
//            throw new IllegalArgumentException(
//                    CUSTOM_RESOURCE_MANAGER.getFormattedString("BucketNameInvalid", bucketName));
//        }
//    }
//
//    /**
//     * Validate bucket creation name.
//     */
//    public static boolean validateBucketNameCreation(String bucketName) {
//
//        if (bucketName == null) {
//            return false;
//        }
//
//        return bucketName.matches(BUCKET_NAMING_CREATION_REGEX);
//    }
//
//    public static void ensureBucketNameCreationValid(String bucketName) {
//        if (!validateBucketNameCreation(bucketName)) {
//            throw new IllegalArgumentException(
//                    CUSTOM_RESOURCE_MANAGER.getFormattedString("BucketNameInvalid", bucketName));
//        }
//    }
//
//
//    /**
//     * Populate metadata to headers.
//     */
//    public static void populateRequestMetadata(Map<String, String> headers, ObjectMetadata metadata) {
//        Map<String, Object> rawMetadata = metadata.getRawMetadata();
//        if (rawMetadata != null) {
//            for (Map.Entry<String, Object> entry : rawMetadata.entrySet()) {
//                if (entry.getKey() != null && entry.getValue() != null) {
//                    String key = entry.getKey();
//                    String value = entry.getValue().toString();
//                    if (key != null)
//                        key = key.trim();
//                    if (value != null)
//                        value = value.trim();
//                    headers.put(key, value);
//                }
//            }
//        }
//
//        Map<String, String> userMetadata = metadata.getUserMetadata();
//        if (userMetadata != null) {
//            for (Map.Entry<String, String> entry : userMetadata.entrySet()) {
//                if (entry.getKey() != null && entry.getValue() != null) {
//                    String key = entry.getKey();
//                    String value = entry.getValue();
//                    if (key != null)
//                        key = key.trim();
//                    if (value != null)
//                        value = value.trim();
//                    headers.put(OSSHeaders.OSS_USER_METADATA_PREFIX + key, value);
//                }
//            }
//        }
//    }
//
//    public static void addHeader(Map<String, String> headers, String header, String value) {
//        if (value != null) {
//            headers.put(header, value);
//        }
//    }
//
//    public static void addDateHeader(Map<String, String> headers, String header, Date value) {
//        if (value != null) {
//            headers.put(header, DateUtil.formatRfc822Date(value));
//        }
//    }
//
//    public static void addStringListHeader(Map<String, String> headers, String header, List<String> values) {
//        if (values != null && !values.isEmpty()) {
//            headers.put(header, join(values));
//        }
//    }
//
//    public static void removeHeader(Map<String, String> headers, String header) {
//        if (header != null && headers.containsKey(header)) {
//            headers.remove(header);
//        }
//    }
//
//    public static String join(List<String> strings) {
//
//        StringBuilder sb = new StringBuilder();
//        boolean first = true;
//
//        for (String s : strings) {
//            if (!first) {
//                sb.append(", ");
//            }
//            sb.append(s);
//
//            first = false;
//        }
//
//        return sb.toString();
//    }
//
//    public static String trimQuotes(String s) {
//
//        if (s == null) {
//            return null;
//        }
//
//        s = s.trim();
//        if (s.startsWith("\"")) {
//            s = s.substring(1);
//        }
//        if (s.endsWith("\"")) {
//            s = s.substring(0, s.length() - 1);
//        }
//
//        return s;
//    }
//
//    public static void populateResponseHeaderParameters(Map<String, String> params,
//            ResponseHeaderOverrides responseHeaders) {
//
//        if (responseHeaders != null) {
//            if (responseHeaders.getCacheControl() != null) {
//                params.put(ResponseHeaderOverrides.RESPONSE_HEADER_CACHE_CONTROL, responseHeaders.getCacheControl());
//            }
//
//            if (responseHeaders.getContentDisposition() != null) {
//                params.put(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_DISPOSITION,
//                        responseHeaders.getContentDisposition());
//            }
//
//            if (responseHeaders.getContentEncoding() != null) {
//                params.put(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_ENCODING,
//                        responseHeaders.getContentEncoding());
//            }
//
//            if (responseHeaders.getContentLangauge() != null) {
//                params.put(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_LANGUAGE,
//                        responseHeaders.getContentLangauge());
//            }
//
//            if (responseHeaders.getContentType() != null) {
//                params.put(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_TYPE, responseHeaders.getContentType());
//            }
//
//            if (responseHeaders.getExpires() != null) {
//                params.put(ResponseHeaderOverrides.RESPONSE_HEADER_EXPIRES, responseHeaders.getExpires());
//            }
//        }
//    }
//
//    public static void safeCloseResponse(ResponseMessage response) {
//        try {
//            response.close();
//        } catch (IOException e) {
//        }
//    }
//
//    public static void mandatoryCloseResponse(ResponseMessage response) {
//        try {
//            response.abort();
//        } catch (IOException e) {
//        }
//    }
//
//    public static long determineInputStreamLength(InputStream instream, long hintLength) {
//
//        if (hintLength <= 0 || !instream.markSupported()) {
//            return -1;
//        }
//
//        return hintLength;
//    }
//
//    public static long determineInputStreamLength(InputStream instream, long hintLength, boolean useChunkEncoding) {
//
//        if (useChunkEncoding) {
//            return -1;
//        }
//
//        if (hintLength <= 0 || !instream.markSupported()) {
//            return -1;
//        }
//
//        return hintLength;
//    }
//
//    public static String joinETags(List<String> eTags) {
//
//        StringBuilder sb = new StringBuilder();
//        boolean first = true;
//
//        for (String eTag : eTags) {
//            if (!first) {
//                sb.append(", ");
//            }
//            sb.append(eTag);
//
//            first = false;
//        }
//
//        return sb.toString();
//    }
//
//    /**
//     * Encode the callback with JSON.
//     */
//    public static String jsonizeCallback(Callback callback) {
//        StringBuffer jsonBody = new StringBuffer();
//
//        jsonBody.append("{");
//        // url, required
//        jsonBody.append("\"callbackUrl\":" + "\"" + callback.getCallbackUrl() + "\"");
//
//        // host, optional
//        if (callback.getCallbackHost() != null && !callback.getCallbackHost().isEmpty()) {
//            jsonBody.append(",\"callbackHost\":" + "\"" + callback.getCallbackHost() + "\"");
//        }
//
//        // body, require
//        jsonBody.append(",\"callbackBody\":" + "\"" + callback.getCallbackBody() + "\"");
//
//        // bodyType, optional
//        if (callback.getCalbackBodyType() == CalbackBodyType.JSON) {
//            jsonBody.append(",\"callbackBodyType\":\"application/json\"");
//        } else if (callback.getCalbackBodyType() == CalbackBodyType.URL) {
//            jsonBody.append(",\"callbackBodyType\":\"application/x-www-form-urlencoded\"");
//        }
//        jsonBody.append("}");
//
//        return jsonBody.toString();
//    }
//
//    /**
//     * Encode CallbackVar with Json.
//     */
//    public static String jsonizeCallbackVar(Callback callback) {
//        StringBuffer jsonBody = new StringBuffer();
//
//        jsonBody.append("{");
//        for (Map.Entry<String, String> entry : callback.getCallbackVar().entrySet()) {
//            if (entry.getKey() != null && entry.getValue() != null) {
//                if (!jsonBody.toString().equals("{")) {
//                    jsonBody.append(",");
//                }
//                jsonBody.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\" ");
//            }
//        }
//        jsonBody.append("}");
//
//        return jsonBody.toString();
//    }
//
//    /**
//     * Ensure the callback is valid by checking its url and body are not null or
//     * empty.
//     */
//    public static void ensureCallbackValid(Callback callback) {
//        if (callback != null) {
//            CodingUtils.assertStringNotNullOrEmpty(callback.getCallbackUrl(), "Callback.callbackUrl");
//            CodingUtils.assertParameterNotNull(callback.getCallbackBody(), "Callback.callbackBody");
//        }
//    }
//
//    /**
//     * Put the callback parameter into header.
//     */
//    public static void populateRequestCallback(Map<String, String> headers, Callback callback) {
//        if (callback != null) {
//            String jsonCb = jsonizeCallback(callback);
//            String base64Cb = BinaryUtil.toBase64String(jsonCb.getBytes());
//
//            headers.put(OSSHeaders.OSS_HEADER_CALLBACK, base64Cb);
//
//            if (callback.hasCallbackVar()) {
//                String jsonCbVar = jsonizeCallbackVar(callback);
//                String base64CbVar = BinaryUtil.toBase64String(jsonCbVar.getBytes());
//                base64CbVar = base64CbVar.replaceAll("\n", "").replaceAll("\r", "");
//                headers.put(OSSHeaders.OSS_HEADER_CALLBACK_VAR, base64CbVar);
//            }
//        }
//    }
//
//    /**
//     * Checks if OSS and SDK's checksum is same. If not, throws
//     * InconsistentException.
//     */
//    public static void checkChecksum(Long clientChecksum, Long serverChecksum, String requestId) {
//        if (clientChecksum != null && serverChecksum != null && !clientChecksum.equals(serverChecksum)) {
//            throw new InconsistentException(clientChecksum, serverChecksum, requestId);
//        }
//    }
//
//    public static URI toEndpointURI(String endpoint, String defaultProtocol) throws IllegalArgumentException {
//        if (endpoint != null && !endpoint.contains("://")) {
//            endpoint = defaultProtocol + "://" + endpoint;
//        }
//
//        try {
//            return new URI(endpoint);
//        } catch (URISyntaxException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
}
