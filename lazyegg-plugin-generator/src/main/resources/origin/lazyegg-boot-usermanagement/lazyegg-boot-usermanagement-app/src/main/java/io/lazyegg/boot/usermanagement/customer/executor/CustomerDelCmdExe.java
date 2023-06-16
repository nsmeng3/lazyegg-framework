package io.lazyegg.boot.customermanage.customer.executor;

import io.lazyegg.boot.customermanage.dto.CustomerDelCmd;
import io.lazyegg.boot.customermanage.customer.CustomerDbService;
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
