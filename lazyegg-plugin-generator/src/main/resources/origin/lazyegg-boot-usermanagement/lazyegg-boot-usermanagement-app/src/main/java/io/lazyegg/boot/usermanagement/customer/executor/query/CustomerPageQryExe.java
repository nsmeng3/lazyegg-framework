package io.lazyegg.boot.usermanagement.customer.executor.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.lazyegg.boot.usermanagement.dto.CustomerPageQry;
import io.lazyegg.boot.usermanagement.customer.CustomerDO;
import io.lazyegg.boot.usermanagement.customer.CustomerDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomerPageQryExe {

    @Autowired
    private CustomerDbService customerDbService;

    public PageDTO<CustomerDO> execute(CustomerPageQry qry) {
        int pageIndex = qry.getPageIndex();
        int pageSize = qry.getPageSize();
        QueryWrapper<CustomerDO> queryWrapper = new QueryWrapper<>();

        return customerDbService.page(new PageDTO<>(pageIndex, pageSize), queryWrapper);
    }
}
