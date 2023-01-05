package io.lazyegg.boot.plugins.generator.app.excutor;

import io.lazyegg.boot.plugins.generator.client.VmTemplateGenContext;
import io.lazyegg.boot.plugins.generator.infrastructure.util.LeggFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * VmTemplateGenCmdExe
 * .vm模板生成
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class VmTemplateGenCmdExe {
    private static final Logger log = LoggerFactory.getLogger(VmTemplateGenCmdExe.class);

    public void execute() {
        VmTemplateGenContext context = new VmTemplateGenContext();
        // 初始化占位符
        initPlaceholder(context);
        // 读取原始文件
        readOriginFile(context);
        // 替换占位符
        replacePlaceholder(context);
        // 输出文件
        outputVmFile(context);

    }

    private void outputVmFile(VmTemplateGenContext context) {
        Map<File, StringWriter> stringWriterMap = context.getStringWriterMap();
        // 模板输出目录
        File templateDir = LeggFileUtil.getResourceDir("template");
        Map<String, String> placeholderMap = context.getPlaceholderMap();
        String javaFilePrefix = placeholderMap.get("__javaFilePrefix__");
        for (Map.Entry<File, StringWriter> fileStringWriterEntry : stringWriterMap.entrySet()) {
            File originFile = fileStringWriterEntry.getKey();
            StringWriter stringWriter = fileStringWriterEntry.getValue();
            String originFileName = originFile.getName();
            // 文件替换
            String templateFileName = originFileName.replaceAll(javaFilePrefix, "");
            // 如果替换后文件名只剩后缀,文件名使用 Domain.java
            if (".java".equals(templateFileName)) {
                templateFileName = "Domain.java";
            }
            String vmName = String.join(".", templateFileName, "vm");
            // 文件输出目录
            String templateDirAbsolutePath = templateDir.getAbsolutePath();
            LeggFileUtil.writeFile(stringWriter, templateDirAbsolutePath, vmName);

        }

    }

    private void initPlaceholder(VmTemplateGenContext context) {
        context.putPlaceholder("io.lazyegg.boot", "${package}");
        context.putPlaceholder("usermanagement", "${moduleName}");
        context.putPlaceholder("customer", "${entityName}");
        context.putPlaceholder("Customer", "${entityClassName}");
        context.putPlaceholder("__javaFilePrefix__", "Customer");
    }

    public void replacePlaceholder(VmTemplateGenContext context) {
        Map<String, String> placeholderMap = context.getPlaceholderMap();
        List<File> originFiles = context.getOriginFiles();
        for (File originFile : originFiles) {
            StringWriter stringWriter = LeggFileUtil.replaceFileContent(placeholderMap, originFile);
            context.putStringWriter(originFile, stringWriter);
        }

    }

    public void readOriginFile(VmTemplateGenContext context) {
        ArrayList<File> originFiles = new ArrayList<>();
        File originDir = LeggFileUtil.getResourceDir("origin");
        List<File> fileList = new ArrayList<>();
        LeggFileUtil.listFile(originDir, fileList, false);
        for (File originFile : fileList) {
            String name = originFile.getName();
            if (name.endsWith(".java") && !name.endsWith("Test.java")) {
                originFiles.add(originFile);
            }
        }
        context.setOriginFiles(originFiles);
    }
}

