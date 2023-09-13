package io.lazyegg.sdk;


public class DefaultCredentialsProvider implements CredentialsProvider {
    private volatile Credentials credentials;

    public DefaultCredentialsProvider(Credentials credentials) {
        setCredentials(credentials);
    }

    public DefaultCredentialsProvider(String accessKeyId, String secretAccessKey) {
        checkCredentials(accessKeyId, secretAccessKey);
        setCredentials(new DefaultCredentials(accessKeyId, secretAccessKey));
    }

    @Override
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public Credentials getCredentials() {
        return credentials;
    }
    private static void checkCredentials(String accessKeyId, String secretAccessKey) {
        if (accessKeyId == null || accessKeyId.equals("")) {
            throw new InvalidCredentialsException("Access key id should not be null or empty.");
        }

        if (secretAccessKey == null || secretAccessKey.equals("")) {
            throw new InvalidCredentialsException("Secret access key should not be null or empty.");
        }
    }
}
