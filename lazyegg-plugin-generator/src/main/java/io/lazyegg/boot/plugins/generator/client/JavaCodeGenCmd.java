package io.lazyegg.boot.plugins.generator.client;

import lombok.Data;

/**
 * JavaCodeGenCmd
 *
 * @author DifferentW  nsmeng3@163.com
 */


@Data
public class JavaCodeGenCmd {

    private String moduleName;
    private String entityName;
    private String packagePrefix;

    public JavaCodeGenCmd(String moduleName, String entityName) {
        this.moduleName = moduleName;
        this.entityName = entityName;
    }
}
