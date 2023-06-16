package io.lazyegg.boot.customermanage.customer;

import com.alibaba.cola.exception.SysException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.lazyegg.boot.customermanage.domain.customer.Customer;
import io.lazyegg.boot.customermanage.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.customermanage.dto.CustomerDelCmd;
import io.lazyegg.boot.customermanage.dto.CustomerUpdateCmd;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
