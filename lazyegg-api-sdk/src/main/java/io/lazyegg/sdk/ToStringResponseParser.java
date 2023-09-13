package io.lazyegg.sdk;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ToStringResponseParser implements ResponseParser<String> {
    @Override
    public String parse(ResponseMessage response) {
        InputStream content = response.getContent();
        if (content != null) {
            try {
                return IOUtils.toString(content, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
