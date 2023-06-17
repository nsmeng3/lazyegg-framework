package io.lazyegg.boot.usermanagement.domain.customer.gateway;

import io.lazyegg.boot.usermanagement.domain.customer.Customer;
import io.lazyegg.boot.usermanagement.dto.CustomerDelCmd;
import io.lazyegg.boot.usermanagement.dto.CustomerUpdateCmd;

import java.util.List;

public interface CustomerGateway {
    Customer getByById(String customerId);

    void removeCustomer(CustomerDelCmd cmd);

    void updateCustomer(CustomerUpdateCmd cmd);

    List<Customer> listCustomer(int pageIndex, int pageSize);

}
