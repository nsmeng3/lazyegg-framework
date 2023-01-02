package io.lazyegg.boot.usermanagement.customer.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.boot.usermanagement.domain.customer.Customer;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.usermanagement.dto.CustomerGetQry;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomerGetQryExe {

    @Autowired
    private CustomerGateway customerGateway;

    public SingleResponse<CustomerDTO> execute(CustomerGetQry qry) {
        Customer byById = customerGateway.getByById("1");
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("DifferentW");
        return SingleResponse.of(customerDTO);
    }
}
