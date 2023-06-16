package io.lazyegg.boot.customermanage.customer.executor;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.lazyegg.boot.customermanage.dto.CustomerUpdateCmd;
import io.lazyegg.boot.customermanage.dto.data.CustomerDTO;
import io.lazyegg.boot.customermanage.customer.CustomerDO;
import io.lazyegg.boot.customermanage.customer.CustomerDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CustomerUpdateCmdExe
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class CustomerUpdateCmdExe {
    private static final Logger log = LoggerFactory.getLogger(CustomerUpdateCmdExe.class);

    @Resource
    private CustomerDbService customerDbService;

    public CustomerDTO execute(CustomerUpdateCmd cmd) {
        CustomerDO customerDO = new CustomerDO();
        BeanUtils.copyProperties(cmd, customerDO);
        LambdaUpdateWrapper<CustomerDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CustomerDO::getId, cmd.getId());
        customerDbService.update(customerDO, updateWrapper);
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerDO, customerDTO);
        return customerDTO;
    }
}
