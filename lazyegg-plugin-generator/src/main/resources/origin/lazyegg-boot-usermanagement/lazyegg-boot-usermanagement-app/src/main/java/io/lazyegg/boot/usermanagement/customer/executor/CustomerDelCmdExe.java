package io.lazyegg.boot.usermanagement.customer.executor;

import io.lazyegg.boot.usermanagement.customer.CustomerDbService;
import io.lazyegg.boot.usermanagement.dto.CustomerDelCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CustomerDelCmdExe
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class CustomerDelCmdExe {
    private static final Logger log = LoggerFactory.getLogger(CustomerDelCmdExe.class);

    @Resource
    private CustomerDbService customerDbService;

    public void execute(CustomerDelCmd customerDelCmd) {
        customerDbService.removeById(customerDelCmd.getId());
    }
}
