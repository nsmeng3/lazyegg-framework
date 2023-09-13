package io.lazyegg.sdk;


import io.lazyegg.sdk.utils.SDKUtils;
import lombok.Getter;

import java.net.URI;
import java.util.List;

import static io.lazyegg.sdk.SDKConstants.DEFAULT_CHARSET_NAME;
import static io.lazyegg.sdk.utils.CommonUtils.assertParameterNotNull;
import static io.lazyegg.sdk.utils.LogUtils.logException;
import static io.lazyegg.sdk.utils.SDKUtils.safeCloseResponse;

public abstract class SDKOperation {
    @Getter
    protected volatile URI endpoint;
    protected CredentialsProvider credentialsProvider;
    protected ServiceClient client;
    protected static ErrorResponseHandler errorResponseHandler = new ErrorResponseHandler();
    protected static ResponseParsers.EmptyResponseParser emptyResponseParser = new ResponseParsers.EmptyResponseParser();
    protected static RetryStrategy noRetryStrategy = new NoRetryStrategy();

    protected SDKOperation(ServiceClient client, CredentialsProvider credentialsProvider) {
        this.client = client;
        this.credentialsProvider = credentialsProvider;
    }

    public URI getEndpoint(WebServiceRequest request) {
        String reqEndpoint = request.getEndpoint();
        if (reqEndpoint == null) {
            return getEndpoint();

        }
        String defaultProto = this.client.getClientConfiguration().getProtocol().toString();
        URI ret = SDKUtils.toEndpointURI(reqEndpoint, defaultProto);
        SDKUtils.ensureEndpointValid(ret.getHost());
        return ret;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = URI.create(endpoint.toString());
    }

    protected ServiceClient getInnerClient() {
        return this.client;
    }

    protected ResponseMessage send(RequestMessage request, ExecutionContext context)
            throws APIException, ClientException {
        return send(request, context, false);
    }

    protected ResponseMessage send(RequestMessage request, ExecutionContext context, boolean keepResponseOpen)
            throws APIException, ClientException {
        ResponseMessage response = null;
        try {
            response = client.sendRequest(request, context);
            return response;
        } catch (ServiceException e) {
            assert (e instanceof APIException);
            throw (APIException) e;
        } finally {
            if (response != null && !keepResponseOpen) {
                safeCloseResponse(response);
            }
        }
    }

    protected <T> T doOperation(RequestMessage request, ResponseParser<T> parser)
            throws APIException, ClientException {
        return doOperation(request, parser, false);
    }

    protected <T> T doOperation(RequestMessage request, ResponseParser<T> parser,
                                boolean keepResponseOpen) throws APIException, ClientException {
        return doOperation(request, parser, keepResponseOpen, null, null);
    }

    protected <T> T doOperation(RequestMessage request, ResponseParser<T> parser,
                                boolean keepResponseOpen, List<RequestHandler> requestHandlers,
                                List<ResponseHandler> responseHandlers)
            throws APIException, ClientException {

        final WebServiceRequest originalRequest = request.getOriginalRequest();
        request.getHeaders().putAll(client.getClientConfiguration().getDefaultHeaders());
        request.getHeaders().putAll(originalRequest.getHeaders());
        request.getParameters().putAll(originalRequest.getParameters());

        ExecutionContext context = createDefaultContext(request.getMethod(), originalRequest);

        if (requestHandlers != null) {
            for (RequestHandler handler : requestHandlers)
                context.addRequestHandler(handler);
        }

        if (responseHandlers != null) {
            for (ResponseHandler handler : responseHandlers)
                context.addResponseHandler(handler);
        }

        List<RequestSigner> signerHandlers = this.client.getClientConfiguration().getSignerHandlers();
        if (signerHandlers != null) {
            for (RequestSigner signer : signerHandlers) {
                context.addSignerHandler(signer);
            }
        }

        ResponseMessage response = send(request, context, keepResponseOpen);

        try {
            return parser.parse(response);
        } catch (ResponseParseException rpe) {
            APIException oe = ExceptionFactory.createInvalidResponseException(response.getRequestId(), rpe.getMessage(),
                    rpe);
            logException("Unable to parse response error: ", rpe);
            throw oe;
        }
    }


    protected ExecutionContext createDefaultContext(HttpMethod method, WebServiceRequest originalRequest) {
        ExecutionContext context = new ExecutionContext();
        Credentials credentials = credentialsProvider.getCredentials();
        assertParameterNotNull(credentials, "credentials");
        context.setCharset(DEFAULT_CHARSET_NAME);
//        context.setSigner(createSigner(credentials, client.getClientConfiguration()));
        context.addResponseHandler(errorResponseHandler);
        if (method == HttpMethod.POST && !isRetryablePostRequest(originalRequest)) {
            context.setRetryStrategy(noRetryStrategy);
        }

        if (client.getClientConfiguration().getRetryStrategy() != null) {
            context.setRetryStrategy(client.getClientConfiguration().getRetryStrategy());
        }

        context.setCredentials(credentials);
        return context;
    }

    protected boolean isRetryablePostRequest(WebServiceRequest request) {
        return false;
    }
}
