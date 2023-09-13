package io.lazyegg.sdk;

import lombok.Data;
import lombok.Getter;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class ClientConfiguration {
    public static final int DEFAULT_MAX_RETRIES = 3;

    public static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = -1;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 50 * 1000;
    public static final int DEFAULT_SOCKET_TIMEOUT = 50 * 1000;
    public static final int DEFAULT_MAX_CONNECTIONS = 1024;
    public static final long DEFAULT_CONNECTION_TTL = -1;
    public static final long DEFAULT_IDLE_CONNECTION_TIME = 60 * 1000;
    public static final int DEFAULT_VALIDATE_AFTER_INACTIVITY = 2 * 1000;
    public static final int DEFAULT_THREAD_POOL_WAIT_TIME = 60 * 1000;
    public static final int DEFAULT_REQUEST_TIMEOUT = 5 * 60 * 1000;
    public static final long DEFAULT_SLOW_REQUESTS_THRESHOLD = 5 * 60 * 1000;

    public static final boolean DEFAULT_USE_REAPER = true;

    /**
     * -- GETTER --
     *  Gets the max retry count upon a retryable error. By default it's 3.
     *
     * @return The max retry count.
     */
    @Getter
    protected int maxErrorRetry = DEFAULT_MAX_RETRIES;
    /**
     * -- GETTER --
     *  Gets the timeout in millisecond for retrieving an available connection
     *  from the connection manager. 0 means infinite and -1 means not defined.
     *  By default it's -1.
     *
     * @return The timeout in millisecond.
     */
    @Getter
    protected int connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
    /**
     * -- GETTER --
     *  Gets the socket connection timeout in millisecond.
     *
     * @return The socket connection timeout in millisecond.
     */
    @Getter
    protected int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    /**
     * -- GETTER --
     *  Gets the socket timeout in millisecond. 0 means infinite timeout, not
     *  recommended.
     *
     * @return The socket timeout in millisecond.
     */
    @Getter
    protected int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    /**
     * -- GETTER --
     *  Gets the max connection count.
     *
     * @return The max connection count. By default it's 1024.
     */
    @Getter
    protected int maxConnections = DEFAULT_MAX_CONNECTIONS;
    /**
     * -- GETTER --
     *  Gets the connection TTL (time to live). Http connection is cached by the
     *  connection manager with a TTL.
     *
     * @return The connection TTL in millisecond.
     */
    @Getter
    protected long connectionTTL = DEFAULT_CONNECTION_TTL;
    /**
     * -- GETTER --
     *  Gets the flag of using
     *  to manage expired
     *  connection.
     *
     * @return True if it's enabled; False if it's disabled.
     */
    @Getter
    protected boolean useReaper = DEFAULT_USE_REAPER;
    /**
     * -- GETTER --
     *  Gets the connection's max idle time. If a connection has been idle for
     *  more than this number, it would be closed.
     *
     * @return The connection's max idle time in millisecond.
     */
    @Getter
    protected long idleConnectionTime = DEFAULT_IDLE_CONNECTION_TIME;

    /**
     * -- GETTER --
     *  Gets the OSS's protocol (HTTP or HTTPS).
     *
     * @return The OSS's protocol.
     */
    @Getter
    protected Protocol protocol = Protocol.HTTP;

    protected String proxyHost = null;
    /**
     * -- GETTER --
     *  Gets the proxy host's port.
     *
     * @return The proxy host.
     */
    @Getter
    protected int proxyPort = -1;
    /**
     * -- GETTER --
     *  Gets the proxy user name.
     *
     * @return The user name.
     */
    @Getter
    protected String proxyUsername = null;
    /**
     * -- GETTER --
     *  Gets the proxy user password.
     *
     * @return The proxy user password.
     */
    @Getter
    protected String proxyPassword = null;
    /**
     * -- GETTER --
     *  Gets the proxy server's domain, which could do the NTLM authentiation
     *  (optional).
     *
     * @return The proxy domain name.
     */
    @Getter
    protected String proxyDomain = null;
    /**
     * -- GETTER --
     *  Gets the proxy host's NTLM authentication server.
     *
     * @return The NTLM authentication server name.
     */
    @Getter
    protected String proxyWorkstation = null;

    protected boolean supportCname = true;
    protected List<String> cnameExcludeList = new ArrayList<String>();
    protected Lock rlock = new ReentrantLock();

    protected boolean sldEnabled = false;

    protected int requestTimeout = DEFAULT_REQUEST_TIMEOUT;
    protected boolean requestTimeoutEnabled = false;
    protected long slowRequestsThreshold = DEFAULT_SLOW_REQUESTS_THRESHOLD;

    protected Map<String, String> defaultHeaders = new LinkedHashMap<String, String>();

    protected List<RequestSigner> signerHandlers = new LinkedList<RequestSigner>();


    protected long tickOffset = 0;

    private RetryStrategy retryStrategy;

    private boolean redirectEnable = true;

    private boolean verifySSLEnable = true;
    private KeyManager[] keyManagers = null;
    private X509TrustManager[] x509TrustManagers = null;
    private SecureRandom secureRandom = null;
    private HostnameVerifier hostnameVerifier = null;

    protected boolean logConnectionPoolStats = false;

    protected boolean useSystemPropertyValues = false;

    private boolean extractSettingFromEndpoint = true;

    public ClientConfiguration() {
        super();
    }
}
