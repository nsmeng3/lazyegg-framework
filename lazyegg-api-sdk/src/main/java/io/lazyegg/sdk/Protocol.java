package io.lazyegg.sdk;

public enum Protocol {

    HTTP("http"),

    HTTPS("https");

    private final String protocol;

    Protocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return protocol;
    }
}
