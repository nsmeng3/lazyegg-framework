package io.lazyegg.boot.usermanagement.customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.lazyegg.boot.usermanagement.domain.customer.Customer;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.usermanagement.dto.CustomerDelCmd;
import io.lazyegg.boot.usermanagement.dto.CustomerUpdateCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerGatewayImpl implements CustomerGateway {
    @Autowired
    private CustomerMapper customerMapper;

    public Customer getByById(String customerId) {
        CustomerDO customerDO = customerMapper.getById(customerId);
        //Convert to Customer
        return null;
    }

    @Override
    public void removeCustomer(CustomerDelCmd cmd) {
        customerMapper.deleteById("");
    }

    @Override
    public void updateCustomer(CustomerUpdateCmd cmd) {
        customerMapper.update(new CustomerDO(), new UpdateWrapper<>());
    }

    @Override
    public List<Customer> pageCustomer(int pageIndex, int pageSize) {
        PageDTO<CustomerDO> customerDOPageDTO = customerMapper.selectPage(new PageDTO<>(pageIndex, pageSize), new QueryWrapper<>());
        return new ArrayList<>();
    }

    @Override
    public long getCount() {
        return customerMapper.selectCount(new QueryWrapper<>());
    }
}
