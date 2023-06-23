package io.lazyegg.db.config;

import io.lazyegg.db.DynamicDataSource;
import io.lazyegg.db.properties.DataSourceProperties;
import io.lazyegg.db.properties.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author DifferentW  nsmeng3@163.com
 */
@Slf4j
public abstract class BaseDataSourceConfig {
    @Resource
    protected DynamicDataSourceProperties dynamicDataSourceProperties;

    @Bean
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public abstract DynamicDataSource dynamicDataSource(DataSourceProperties dataSourceProperties);

}
