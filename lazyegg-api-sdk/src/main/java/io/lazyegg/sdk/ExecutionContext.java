package io.lazyegg.sdk;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * HTTP request context.
 */
@Data
public class ExecutionContext {

    /**
     * 请求签名
     */
    private RequestSigner signer;

    /**
     * 请求流水线处理器
     */
    private List<RequestHandler> requestHandlers = new LinkedList<RequestHandler>();

    /**
     * 响应流水线处理器
     */
    private List<ResponseHandler> responseHandlers = new LinkedList<ResponseHandler>();
    /**
     * 签名流水线处理器
     */
    private List<RequestSigner> signerHandlers = new LinkedList<RequestSigner>();

    private String charset = SDKConstants.DEFAULT_CHARSET_NAME;

    /**
     * 请求失败重试策略
     */
    private RetryStrategy retryStrategy;

    /**
     * 凭证
     */
    private Credentials credentials;
//
//    public RetryStrategy getRetryStrategy() {
//        return retryStrategy;
//    }
//
//    public void setRetryStrategy(RetryStrategy retryStrategy) {
//        this.retryStrategy = retryStrategy;
//    }
//
//    public String getCharset() {
//        return charset;
//    }
//
//    public void setCharset(String defaultEncoding) {
//        this.charset = defaultEncoding;
//    }
//
//    public RequestSigner getSigner() {
//        return signer;
//    }
//
//    public void setSigner(RequestSigner signer) {
//        this.signer = signer;
//    }
//
//    public List<ResponseHandler> getResponseHandlers() {
//        return responseHandlers;
//    }
//
    public void addResponseHandler(ResponseHandler handler) {
        responseHandlers.add(handler);
    }

    public void insertResponseHandler(int position, ResponseHandler handler) {
        responseHandlers.add(position, handler);
    }
//
    public void removeResponseHandler(ResponseHandler handler) {
        responseHandlers.remove(handler);
    }
//
//    public List<RequestHandler> getRequestHandlers() {
//        return requestHandlers;
//    }

    public void addRequestHandler(RequestHandler handler) {
        requestHandlers.add(handler);
    }

    public void insertRequestHandler(int position, RequestHandler handler) {
        requestHandlers.add(position, handler);
    }

    public void removeRequestHandler(RequestHandler handler) {
        requestHandlers.remove(handler);
    }
//
//    public List<RequestSigner> getSignerHandlers() {
//        return signerHandlers;
//    }

    public void addSignerHandler(RequestSigner handler) {
        signerHandlers.add(handler);
    }

    public void insertSignerHandler(int position, RequestSigner handler) {
        signerHandlers.add(position, handler);
    }

    public void removeSignerHandler(RequestSigner handler) {
        signerHandlers.remove(handler);
    }
//
//    public Credentials getCredentials() {
//        return credentials;
//    }
//
//    public void setCredentials(Credentials credentials) {
//        this.credentials = credentials;
//    }

}
