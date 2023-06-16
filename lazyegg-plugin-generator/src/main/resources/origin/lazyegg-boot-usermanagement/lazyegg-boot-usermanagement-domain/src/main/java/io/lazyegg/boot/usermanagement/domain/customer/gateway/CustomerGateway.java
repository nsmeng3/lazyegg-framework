package io.lazyegg.boot.customermanage.domain.customer.gateway;

import io.lazyegg.boot.customermanage.domain.customer.Customer;
import io.lazyegg.boot.customermanage.dto.CustomerDelCmd;
import io.lazyegg.boot.customermanage.dto.CustomerUpdateCmd;

import java.util.List;

public interface CustomerGateway {
    Customer getByById(String customerId);

    void removeCustomer(CustomerDelCmd cmd);

    void updateCustomer(CustomerUpdateCmd cmd);

    List<Customer> listCustomer(int pageIndex, int pageSize);

}
