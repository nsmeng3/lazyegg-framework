package io.lazyegg.tenant;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import io.lazyegg.core.current.CurrentThreadContext;
import io.lazyegg.db.tenant.AbstractTenantService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * TenantService
 * 租户配置
 *
 * @author DifferentW nsmeng3@163.com
 */
@Configuration
@ConditionalOnClass({MybatisPlusAutoConfiguration.class})
public class TenantService extends AbstractTenantService {

    public long getTenantId() {
        return CurrentThreadContext.getTenantId();
    }
}
