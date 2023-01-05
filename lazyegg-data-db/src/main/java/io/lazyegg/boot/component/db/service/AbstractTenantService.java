package io.lazyegg.boot.component.db.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractTenantService
 *
 * @author DifferentW  nsmeng3@163.com
 */

public abstract class AbstractTenantService implements TenantService {
    private static final Logger log = LoggerFactory.getLogger(AbstractTenantService.class);

    private final String tenantColumnName;

    public AbstractTenantService() {
        this(null);
    }

    public AbstractTenantService(String tenantColumnName) {
        if (StringUtils.isBlank(tenantColumnName)) {
            this.tenantColumnName = "tenant_id";
        } else {
            this.tenantColumnName = tenantColumnName;
        }
    }

    public abstract long getTenantId();

    @Override
    public String getTenantColumnName() {
        return this.tenantColumnName;
    }
}

