package io.lazyegg.boot.customermanage.customer.executor.query;

import io.lazyegg.boot.customermanage.customer.CustomerDO;
import io.lazyegg.boot.customermanage.customer.CustomerDbService;
import io.lazyegg.boot.customermanage.dto.CustomerGetQry;
import io.lazyegg.boot.customermanage.dto.data.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerGetQryExe {

    private final CustomerDbService customerDbService;

    public CustomerGetQryExe(CustomerDbService customerDbService) {
        this.customerDbService = customerDbService;
    }

    public CustomerDTO execute(CustomerGetQry qry) {
        CustomerDO customerDO = customerDbService.getById(qry.getId());
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerDO, customerDTO);
        return customerDTO;
    }
}
