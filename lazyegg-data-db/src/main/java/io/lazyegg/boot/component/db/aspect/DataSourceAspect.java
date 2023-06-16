package io.lazyegg.boot.component.db.aspect;

import io.lazyegg.boot.component.db.DynamicContextHolder;
import io.lazyegg.boot.component.db.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * 数据源切面处理
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Slf4j
@Aspect
@Configuration
@Order(1)
public class DataSourceAspect {

    @Pointcut("@annotation(io.lazyegg.boot.component.db.annotation.DataSource) " +
            "|| @within(io.lazyegg.boot.component.db.annotation.DataSource)")
    private void dataSource() {

    }

    @Around("dataSource()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        DataSource dataSource = getDataSource(joinPoint);
        if (dataSource != null) {
            String value = dataSource.value();
            log.info("change datasource to {}", value);
            // 将注解中的 数据源名称 添加到动态数据源中
            DynamicContextHolder.set(value);
        }
        try {
            return joinPoint.proceed();
        } finally {
            DynamicContextHolder.clean();
        }
    }

    private DataSource getDataSource(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSource annotation = null;
        Method method = methodSignature.getMethod();
        annotation = method.getAnnotation(DataSource.class);

        if (annotation != null) {
            return annotation;
        } else {
            Class<?> targetClass = joinPoint.getTarget().getClass();
            return targetClass.getAnnotation(DataSource.class);
        }
    }
}
