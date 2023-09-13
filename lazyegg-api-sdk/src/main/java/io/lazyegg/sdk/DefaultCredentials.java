package io.lazyegg.sdk;

public class DefaultCredentials implements Credentials {
    private final String accessKeyId;
    private final String secretAccessKey;
    public DefaultCredentials(String accessKeyId, String secretAccessKey) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

}
