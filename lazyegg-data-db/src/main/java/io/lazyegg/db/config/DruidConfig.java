package io.lazyegg.db.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidFilterConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidSpringAopConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidWebStatFilterConfiguration;
import io.lazyegg.db.DruidDataSourceFactory;
import io.lazyegg.db.DynamicDataSource;
import io.lazyegg.db.aspect.DataSourceAspect;
import io.lazyegg.db.properties.DataSourceProperties;
import io.lazyegg.db.properties.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;


/**
 * 多数据源配置
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Slf4j
@Configuration
// 取消Druid自动装配
@EnableAutoConfiguration(exclude = DruidDataSourceAutoConfigure.class)
//@ConditionalOnProperty(prefix = "spring.datasource.druid", name = "enable", havingValue = "true")
@EnableConfigurationProperties({DynamicDataSourceProperties.class, DruidStatProperties.class})
@Import({DruidSpringAopConfiguration.class,
        DruidStatViewServletConfiguration.class,
        DruidWebStatFilterConfiguration.class,
        DruidFilterConfiguration.class, DataSourceAspect.class})
public class DruidConfig extends BaseDataSourceConfig {

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(DataSourceProperties dataSourceProperties) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        DruidDataSource druidDataSource = DruidDataSourceFactory.build().create(dataSourceProperties);
        dynamicDataSource.setDefaultTargetDataSource(druidDataSource);
        log.debug(">>> lazy-egg <<< {default dataSource} init");
        dynamicDataSource.setTargetDataSources(getDynamicDataSource());
        return dynamicDataSource;
    }

    private Map<Object, Object> getDynamicDataSource() {
        Map<String, DataSourceProperties> dataSourcePropertiesMap = dynamicDataSourceProperties.getDatasource();
        HashMap<Object, Object> targetDataSources = new HashMap<>(dataSourcePropertiesMap.size());
        dataSourcePropertiesMap.forEach((k, v) -> {
            DruidDataSource druidDataSource = DruidDataSourceFactory.build().create(v);
            targetDataSources.put(k, druidDataSource);
            log.debug(">>> lazy-egg <<< {dynamic dataSource-{} } init", k);
        });
        return targetDataSources;
    }


    /**
     * 去除监控页面底部的广告
     */
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @Bean
//    @ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true")
//    public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties) {
//        // 获取web监控页面的参数
//        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
//        // 提取common.js的配置路径
//        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
//        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
//        final String filePath = "support/http/resources/js/common.js";
//        // 创建filter进行过滤
//        Filter filter = new Filter() {
//            @Override
//            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
//            }
//
//            @Override
//            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//                    throws IOException, ServletException {
//                chain.doFilter(request, response);
//                // 重置缓冲区，响应头不会被重置
//                response.resetBuffer();
//                // 获取common.js
//                String text = Utils.readFromResource(filePath);
//                // 正则替换banner, 除去底部的广告信息
//                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
//                text = text.replaceAll("powered.*?shrek.wang</a>", "");
//                response.getWriter().write(text);
//            }
//
//            @Override
//            public void destroy() {
//            }
//        };
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(filter);
//        registrationBean.addUrlPatterns(commonJsPattern);
//        return registrationBean;
//    }
}
