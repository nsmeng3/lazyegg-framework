package io.lazyegg.boot.plugins.generator.client;

import io.lazyegg.boot.plugins.generator.domain.CodeFile;
import io.lazyegg.boot.plugins.generator.domain.TemplateFileInfo;
import io.lazyegg.boot.plugins.generator.domain.TemplateVariable;
import lombok.Data;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaCodeGenContext
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Data
public class JavaCodeGenContext {

    private TemplateVariable variable;

    private VelocityEngine velocityEngine;
    private List<TemplateFileInfo> templateFileInfos;

    private List<File> templateFiles = new ArrayList<>();

    private List<CodeFile> codeFiles;
}

