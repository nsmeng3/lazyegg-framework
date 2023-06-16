package io.lazyegg.boot.customermanage.customer.executor.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.lazyegg.boot.component.db.entity.BaseDO;
import io.lazyegg.boot.customermanage.customer.CustomerDO;
import io.lazyegg.boot.customermanage.customer.CustomerDbService;
import io.lazyegg.boot.customermanage.dto.CustomerListQry;
import io.lazyegg.boot.customermanage.dto.data.CustomerDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CustomerListQryExe {

    private final CustomerDbService customerDbService;

    public CustomerListQryExe(CustomerDbService customerDbService) {
        this.customerDbService = customerDbService;
    }

    public List<CustomerDTO> execute(CustomerListQry qry) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        LambdaQueryWrapper<CustomerDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseDO::getDeleted, false);
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(qry.getId()), CustomerDO::getId, qry.getId());
        List<CustomerDO> customerDOList = customerDbService.list(lambdaQueryWrapper);
        customerDOList.forEach(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer, customerDTO);
            customerDTOList.add(customerDTO);
        });
        return customerDTOList;
    }
}
