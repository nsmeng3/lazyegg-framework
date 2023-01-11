package io.lazyegg.boot.usermanagement.customer.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.usermanagement.dto.CustomerCountQry;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CustomerCountQryExe {

    @Autowired
    private CustomerGateway customerGateway;

    public CustomerDTO execute(CustomerCountQry qry) {
        long count = customerGateway.getCount();
        CustomerDTO customerDTO = new CustomerDTO();
        return customerDTO;
    }

    @Data
    public static class Context {

    }
}
