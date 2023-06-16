package io.lazyegg.boot.customermanage.customer.executor.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.lazyegg.boot.customermanage.dto.CustomerPageQry;
import io.lazyegg.boot.customermanage.customer.CustomerDO;
import io.lazyegg.boot.customermanage.customer.CustomerDbService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomerPageQryExe {

    private final CustomerDbService customerDbService;

    public CustomerPageQryExe(CustomerDbService customerDbService) {
        this.customerDbService = customerDbService;
    }

    public PageDTO<CustomerDO> execute(CustomerPageQry qry) {
        int pageIndex = qry.getPageIndex();
        int pageSize = qry.getPageSize();
        LambdaQueryWrapper<CustomerDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CustomerDO::getDeleted, false);
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(qry.getId()), CustomerDO::getId, qry.getId());
        return customerDbService.page(new PageDTO<>(pageIndex, pageSize), lambdaQueryWrapper);
    }
}
