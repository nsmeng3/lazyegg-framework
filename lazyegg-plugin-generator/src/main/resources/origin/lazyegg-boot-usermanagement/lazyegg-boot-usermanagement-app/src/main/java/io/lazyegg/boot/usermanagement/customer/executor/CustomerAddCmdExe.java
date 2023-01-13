package io.lazyegg.boot.usermanagement.customer.executor;

import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.boot.usermanagement.dto.CustomerAddCmd;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import io.lazyegg.boot.usermanagement.customer.CustomerDO;
import io.lazyegg.boot.usermanagement.customer.CustomerDbService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class CustomerAddCmdExe {

    @Resource
    private CustomerDbService customerDbService;

    public SingleResponse<CustomerDTO> execute(CustomerAddCmd cmd) {
        CustomerDO entity = new CustomerDO();
        BeanUtils.copyProperties(cmd, entity);
        customerDbService.save(entity);
        CustomerDTO target = new CustomerDTO();
        BeanUtils.copyProperties(entity, target);
        return SingleResponse.of(target);
    }

}
