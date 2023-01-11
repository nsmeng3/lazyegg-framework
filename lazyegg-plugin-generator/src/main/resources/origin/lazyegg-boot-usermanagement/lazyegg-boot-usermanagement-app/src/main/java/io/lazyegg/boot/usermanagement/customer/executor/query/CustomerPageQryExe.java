package io.lazyegg.boot.usermanagement.customer.executor.query;

import io.lazyegg.boot.usermanagement.domain.customer.Customer;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.usermanagement.dto.CustomerPageQry;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CustomerPageQryExe {

    @Autowired
    private CustomerGateway customerGateway;

    public List<CustomerDTO> execute(CustomerPageQry qry) {
        List<Customer> customerList = customerGateway.pageCustomer(qry.getPageIndex(), qry.getPageSize());

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        return customerDTOS;
    }
}
