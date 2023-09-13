package io.lazyegg.sdk;

public interface CredentialsProvider {

    public void setCredentials(Credentials creds);

    public Credentials getCredentials();
}
