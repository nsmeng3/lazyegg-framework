package io.lazyegg.boot.component.db.annotation;

import java.lang.annotation.*;

/**
 * 数据源
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface DataSource {
    String value() default "";
}
