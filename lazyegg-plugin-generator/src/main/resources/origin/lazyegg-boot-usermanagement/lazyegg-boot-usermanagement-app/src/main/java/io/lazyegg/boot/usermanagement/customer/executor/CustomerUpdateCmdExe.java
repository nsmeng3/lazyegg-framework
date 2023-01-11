package io.lazyegg.boot.usermanagement.customer.executor;

import com.alibaba.cola.dto.Response;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
import io.lazyegg.boot.usermanagement.dto.CustomerUpdateCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private CustomerGateway customerGateway;

    public void execute(CustomerUpdateCmd cmd) {
        customerGateway.updateCustomer(cmd);
        return;
    }
}

