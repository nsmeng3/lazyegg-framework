package io.lazyegg.db;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源上下文
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Slf4j
public class DynamicContextHolder {

    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> DATASOURCE_TYPE = new ThreadLocal<>();

    /**
     * 获取当前线程数据源变量
     */
    public static String get() {
        return DATASOURCE_TYPE.get();
    }

    /**
     * 设置当前线程数据源变量
     *
     * @param datasourceType 数据源类型
     */
    public static void set(String datasourceType) {
        DATASOURCE_TYPE.set(datasourceType);
        log.debug("数据源切换为: {}", datasourceType);
    }

    /**
     * 当前线程数据源变量
     */
    public static void clean() {
        DATASOURCE_TYPE.remove();
        log.debug("数据源切换为默认数据源");
    }
}
