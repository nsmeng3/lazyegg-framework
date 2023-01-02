package io.lazyegg.boot.plugins.generator.domain;

import lombok.*;
import org.apache.velocity.VelocityContext;

import java.util.Map;

/**
 * TemplateVariable
 * 模板变量
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Data
public class TemplateVariable {

    private String packagePrefix;

    /**
     * 模块前缀
     */
    private String modulePrefix = "lazyegg-boot";

    @NonNull
    private String moduleName;

    @NonNull
    private String entityName;

    private String entityClassName;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private VelocityContext velocityContext = new VelocityContext();

    public TemplateVariable(@NonNull String moduleName, @NonNull String entityName) {
        this("io.lazyegg.boot", moduleName, entityName);
    }

    public TemplateVariable(String packagePrefix, @NonNull String moduleName, @NonNull String entityName) {
        this.packagePrefix = packagePrefix;
        this.moduleName = moduleName;
        this.setEntityName(entityName);
        this.init();
    }

    public void setEntityName(String entityName) {
        if (entityName.length() < 1) {
            throw new RuntimeException("entityName length must be greater than 1" );
        }
        this.entityName = initialLowerCase(entityName);
        this.entityClassName = initialUpperCase(this.entityName);

    }

    private String initialUpperCase(String entityName) {
        String initial = entityName.substring(0, 1).toUpperCase();
        String nonInitial = entityName.substring(1);
        return String.format("%s%s", initial, nonInitial);
    }

    private String initialLowerCase(String entityName) {
        String initial = entityName.substring(0, 1).toLowerCase();
        String nonInitial = entityName.substring(1);
        return String.format("%s%s", initial, nonInitial);
    }

    public void init() {
        velocityContext.put("package", this.packagePrefix);
        velocityContext.put("moduleName", this.moduleName);
        velocityContext.put("entityName", this.entityName);
        velocityContext.put("entityClassName", this.entityClassName);
    }

    public void addVar(Map<String, String> var) {
        for (Map.Entry<String, String> stringStringEntry : var.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            velocityContext.put(key, value);
        }
    }

    public VelocityContext getVar() {
        return this.velocityContext;
    }

}

