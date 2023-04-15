package io.lazyegg.boot.component.db.annotation;

import java.lang.annotation.*;

/**
 * 数据源
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface DataSource {
    String value() default "";
}
