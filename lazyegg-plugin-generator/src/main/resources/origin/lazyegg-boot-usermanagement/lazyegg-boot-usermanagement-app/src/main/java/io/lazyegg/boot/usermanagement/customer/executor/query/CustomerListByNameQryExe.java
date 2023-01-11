package io.lazyegg.boot.usermanagement.customer.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import io.lazyegg.boot.usermanagement.domain.customer.Customer;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.usermanagement.dto.CustomerListByNameQry;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CustomerListByNameQryExe {

    @Autowired
    private CustomerGateway customerGateway;

    public MultiResponse<CustomerDTO> execute(CustomerListByNameQry cmd) {
        Customer byById = customerGateway.getByById("1");
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        return MultiResponse.of(customerDTOList);
    }
}
