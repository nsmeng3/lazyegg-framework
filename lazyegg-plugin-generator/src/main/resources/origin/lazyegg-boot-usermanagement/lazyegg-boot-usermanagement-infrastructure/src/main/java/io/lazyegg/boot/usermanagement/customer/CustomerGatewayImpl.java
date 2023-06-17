package io.lazyegg.boot.usermanagement.customer;

import com.alibaba.cola.exception.SysException;
import io.lazyegg.boot.usermanagement.domain.customer.Customer;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.usermanagement.dto.CustomerDelCmd;
import io.lazyegg.boot.usermanagement.dto.CustomerUpdateCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerGatewayImpl implements CustomerGateway {
    @Autowired
    private CustomerDbService customerDbService;

    public Customer getByById(String customerId) {
        throw new SysException("getByById not implement");
    }

    @Override
    public void removeCustomer(CustomerDelCmd cmd) {
        throw new SysException("getByById not implement");
    }

    @Override
    public void updateCustomer(CustomerUpdateCmd cmd) {
        throw new SysException("getByById not implement");
    }

    @Override
    public List<Customer> listCustomer(int pageIndex, int pageSize) {
        throw new SysException("getByById not implement");
    }

}
