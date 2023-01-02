package io.lazyegg.boot.plugins.generator;

import io.lazyegg.boot.plugins.generator.app.excutor.JavaCodeGenCmdExe;
import io.lazyegg.boot.plugins.generator.app.excutor.JavaCodeResetCmdExe;
import io.lazyegg.boot.plugins.generator.client.JavaCodeGenCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeggCodeGenerator
 *
 * @author DifferentW  nsmeng3@163.com
 */

public abstract class LeggCodeGenerator {
    private static final Logger log = LoggerFactory.getLogger(LeggCodeGenerator.class);

    public static void gen(String moduleName, String entityName) {
        new JavaCodeGenCmdExe().execute(new JavaCodeGenCmd(moduleName, entityName));
    }

    public static void reset(String entityName) {
        new JavaCodeResetCmdExe().execute(entityName);
    }
}

