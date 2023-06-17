package io.lazyegg.boot.usermanagement.customer.executor.query;

import io.lazyegg.boot.usermanagement.customer.CustomerDO;
import io.lazyegg.boot.usermanagement.customer.CustomerDbService;
import io.lazyegg.boot.usermanagement.dto.CustomerGetQry;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
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
