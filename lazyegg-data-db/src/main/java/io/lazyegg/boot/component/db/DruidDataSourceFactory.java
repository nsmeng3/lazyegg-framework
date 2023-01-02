package io.lazyegg.boot.component.db;

import com.alibaba.cola.exception.SysException;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.lazyegg.boot.component.db.properties.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * druid 数据源工厂
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Slf4j
public class DruidDataSourceFactory extends DataSourceFactory implements DataSourceService<DruidDataSource>{

    public static DruidDataSourceFactory build() {
        return new DruidDataSourceFactory();
    }

    @Override
    public DruidDataSource create(DataSourceProperties properties) {

        DruidDataSource druidDataSource = new DruidDataSource();
        String driverClassName = properties.getDriverClassName();
        if (StringUtils.isBlank(driverClassName)) {
            throw new SysException("请配置数据库连接驱动");
        }
        druidDataSource.setDriverClassName(driverClassName);

        String url = properties.getUrl();
        if (StringUtils.isBlank(url)) {
            throw new SysException("请配置数据库连接地址");
        }
        druidDataSource.setUrl(url);
        String username = properties.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new SysException("请配置数据库连接用户名");
        }
        druidDataSource.setUsername(username);
        String password = properties.getPassword();
        if (StringUtils.isBlank(password)) {
            throw new SysException("请配置数据库连接密码");
        }
        druidDataSource.setPassword(password);

        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMinIdle(properties.getMinIdle());

        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setMaxEvictableIdleTimeMillis(properties.getMaxEvictableIdleTimeMillis());

        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setValidationQueryTimeout(properties.getValidationQueryTimeout());

        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxOpenPreparedStatements(properties.getMaxOpenPreparedStatements());
        druidDataSource.setSharePreparedStatements(properties.isSharePreparedStatements());
        try {
            druidDataSource.setFilters(properties.getFilters());
            druidDataSource.init();
        } catch (Exception e) {
            log.error("druid数据源初始化失败");
            e.printStackTrace();
        }
        return druidDataSource;
    }

}
