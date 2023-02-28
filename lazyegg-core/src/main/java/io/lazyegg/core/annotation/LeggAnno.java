package io.lazyegg.core.annotation;

import java.lang.annotation.*;

/**
 * 接口放行
 *
 * @author DifferentW  nsmeng3@163.com 2023/2/28 17:18
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LeggAnno {
}
