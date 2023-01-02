package io.lazyegg.boot.component.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源路由
 *
 * @author DifferentW  nsmeng3@163.com
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // 获取当前线程的数据源类型
        return DynamicContextHolder.get();
    }
}
