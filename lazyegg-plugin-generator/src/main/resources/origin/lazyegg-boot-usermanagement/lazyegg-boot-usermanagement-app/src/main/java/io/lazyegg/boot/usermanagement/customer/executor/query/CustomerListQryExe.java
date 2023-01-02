package io.lazyegg.boot.usermanagement.customer.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.usermanagement.dto.CustomerListQry;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CustomerListQryExe {

    @Autowired
    private CustomerGateway customerGateway;

    public MultiResponse<CustomerDTO> execute(CustomerListQry qry) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("DifferentW");
        customerDTOList.add(customerDTO);
        return MultiResponse.of(customerDTOList);
    }
}
