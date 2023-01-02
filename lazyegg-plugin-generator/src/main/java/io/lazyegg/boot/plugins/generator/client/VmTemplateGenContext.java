package io.lazyegg.boot.plugins.generator.client;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VmTemplateGenContext
 * 模板生成上下文
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Data
public class VmTemplateGenContext {
    private static final Logger log = LoggerFactory.getLogger(VmTemplateGenContext.class);

    private List<File> originFiles = new ArrayList<>();
    private Map<String, String> placeholderMap = new HashMap<>();

    private Map<File, StringWriter> stringWriterMap = new HashMap<>();

    public void putPlaceholder(String placeTxt, String placeholder) {
        placeholderMap.put(placeTxt, placeholder);
    }

    public void putStringWriter(File fileFlag, StringWriter stringWriter) {
        stringWriterMap.put(fileFlag, stringWriter);
    }

}

