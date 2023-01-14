package io.lazyegg.boot.usermanagement.customer.executor;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.lazyegg.boot.usermanagement.dto.CustomerUpdateCmd;
import io.lazyegg.boot.usermanagement.customer.CustomerDO;
import io.lazyegg.boot.usermanagement.customer.CustomerDbService;
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

    public void execute(CustomerUpdateCmd cmd) {
        CustomerDO customerDO = new CustomerDO();
        BeanUtils.copyProperties(cmd, customerDO);
        LambdaUpdateWrapper<CustomerDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CustomerDO::getId, cmd.getId());
        customerDbService.update(customerDO, updateWrapper);
    }
}

