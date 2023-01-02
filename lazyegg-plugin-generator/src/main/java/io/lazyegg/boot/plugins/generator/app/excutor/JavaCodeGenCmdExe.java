package io.lazyegg.boot.plugins.generator.app.excutor;

import io.lazyegg.boot.plugins.generator.client.JavaCodeGenCmd;
import io.lazyegg.boot.plugins.generator.client.JavaCodeGenContext;
import io.lazyegg.boot.plugins.generator.domain.CodeFile;
import io.lazyegg.boot.plugins.generator.domain.TemplateFileInfo;
import io.lazyegg.boot.plugins.generator.domain.TemplateVariable;
import io.lazyegg.boot.plugins.generator.infrastructure.util.LeggFileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * JavaCodeGenCmdExe
 * 代码生成命令
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class JavaCodeGenCmdExe {
    private static final Logger log = LoggerFactory.getLogger(JavaCodeGenCmdExe.class);

    public void execute(JavaCodeGenCmd cmd) {
        JavaCodeGenContext context = new JavaCodeGenContext();
        // 初始化变量
        String moduleName = cmd.getModuleName();
        String entityName = cmd.getEntityName();
        context.setVariable(new TemplateVariable(moduleName, entityName));
        /// init 模板引擎
        initEngine(context);
        // 加载vm模板文件
        loadVmTemplateInfoToCtx(context);
        // 填充模板
        fillTemplate(context);
        // 输出文件
        outFile(context);
        // 生成文件记录
        recordFile(context);
    }

    private static void initEngine(JavaCodeGenContext context) {
        VelocityEngine ve = new VelocityEngine();
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.init(prop);
        context.setVelocityEngine(ve);
    }

    private void loadVmTemplateInfoToCtx(JavaCodeGenContext ctx) {
        TemplateVariable variable = ctx.getVariable();
        Assert.notNull(variable, "模板变量不能为空");
        ArrayList<TemplateFileInfo> templateFileInfos = new ArrayList<>();

        File templateDir = LeggFileUtil.getClasspathDir("template");
        ArrayList<File> templateFileList = new ArrayList<>();
        LeggFileUtil.listFile(templateDir, templateFileList, false);
        for (File templateFile : templateFileList) {
            String name = templateFile.getName();
            if ("package-info.java.vm".equals(name)|| name.endsWith("Test.java.vm")) {
                continue;
            }
            templateFileInfos.add(new TemplateFileInfo(templateFile));
        }
        ctx.setTemplateFileInfos(templateFileInfos);

    }

    private void fillTemplate(JavaCodeGenContext context) {
        List<TemplateFileInfo> templateFileInfos = context.getTemplateFileInfos();
        Assert.notNull(templateFileInfos, "模板信息不能为空");
        VelocityEngine velocityEngine = context.getVelocityEngine();
        ArrayList<CodeFile> codeFiles = new ArrayList<>();
        for (TemplateFileInfo templateFileInfo : templateFileInfos) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = velocityEngine.getTemplate("template/" + templateFileInfo.getTemplateFile().getName(), "UTF-8");
            tpl.merge(context.getVariable().getVar(), sw);
            // 模块名前缀
            String modulePrefix = context.getVariable().getModulePrefix();
            String moduleName = context.getVariable().getModuleName();
            codeFiles.add(new CodeFile(sw, modulePrefix, moduleName));
        }

        context.setCodeFiles(codeFiles);
    }




    private void outFile(JavaCodeGenContext ctx) {
        List<CodeFile> codeFiles = ctx.getCodeFiles();
        Assert.notNull(codeFiles, "code not null");
        ArrayList<File> files = new ArrayList<>();
        try {
            for (CodeFile codeFile : codeFiles) {
                StringWriter sw = codeFile.getSw();
                File dir = new File(codeFile.absolutePackagePath());
                dir.mkdirs();
                File file = new File(codeFile.absolutePath());
                FileOutputStream fos = new FileOutputStream(file);
                IOUtils.write(sw.toString(), fos, "UTF-8");
                IOUtils.closeQuietly(sw);
                files.add(file);
            }
        } catch (Exception e) {
            // 删除已创建的文件
            deleteFile(files);
        }
    }

    private void deleteFile(List<File> files) {
        for (File file : files) {
            file.delete();
        }
    }

    private void recordFile(JavaCodeGenContext ctx) {
        ArrayList<File> files = new ArrayList<>();
        List<CodeFile> codeFiles = ctx.getCodeFiles();
        for (CodeFile codeFile : codeFiles) {
            files.add(new File(codeFile.absolutePath()));
        }
        String logFile = String.join(File.separator, "log", ctx.getVariable().getEntityName() + ".rl");
        new File("log").mkdir();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(logFile));
            objectOutputStream.writeObject(files);
            objectOutputStream.close();
        } catch (Exception e) {
            log.warn("代码记录异常，撤销已生成代码操作无法进行", e);
        }
    }

}

