package io.lazyegg.db.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import io.lazyegg.core.current.CurrentThreadContext;
import io.lazyegg.db.tenant.TenantService;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusPluginsConfig {

    private final TenantService tenantService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MybatisPlusPluginsConfig(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 防全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 多租户
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                // 设置当前租户ID，实际情况你可以从cookie、或者缓存中拿都行
                if (tenantService == null) {
                    return new LongValue(CurrentThreadContext.DEFAULT_TENANT_ID);
                }
                return new LongValue(tenantService.getTenantId());
            }

            @Override
            public String getTenantIdColumn() {
                // 对应数据库租户ID的列名
                return tenantService.getTenantColumnName();
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                // TODO 只对user表生效
                return "user".equalsIgnoreCase(tableName);
            }
        }));

        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        return interceptor;
    }


    private GlobalConfig.DbConfig dbConfig() {
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.ASSIGN_UUID);
        return dbConfig;
    }

    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        globalConfig.setDbConfig(dbConfig());
        return globalConfig;
    }


}
