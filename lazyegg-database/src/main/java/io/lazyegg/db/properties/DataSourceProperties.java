package io.lazyegg.db.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据源配置
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DataSourceProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    /**
     * Druid默认参数
     */
    // 初始化大小、最小、最大
    private int initialSize = 2;
    private int maxActive = 10;
    private int minIdle = -1;

    // 获取连接等待超时的时间
    private long maxWait = 60 * 1000L;
    // 间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    private long timeBetweenEvictionRunsMillis = 60 * 1000L;
    // 一个连接在池中最小、最大生存的时间，单位是毫秒
    private long minEvictableIdleTimeMillis = 1000L * 60L * 30L;
    private long maxEvictableIdleTimeMillis = 1000L * 60L * 60L * 7;

    // 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
    // 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
    private String validationQuery = "select 1";
    // 单位：秒，检测连接是否有效的超时时间。底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法
    private int validationQueryTimeout = -1;

    // 建议配置为true，不影响性能，并且保证安全性。
    // 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
    private boolean testWhileIdle = true;
    // 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
    private boolean testOnBorrow = false;
    // 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
    private boolean testOnReturn = false;
    // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
    private boolean poolPreparedStatements = false;
    // 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
    // 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
    private int maxOpenPreparedStatements = -1;
    private boolean sharePreparedStatements = false;
    // 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
    // 监控统计用的filter:stat 日志用的filter:log4j 防御sql注入的filter:wall
    private String filters = "stat,wall";
}
