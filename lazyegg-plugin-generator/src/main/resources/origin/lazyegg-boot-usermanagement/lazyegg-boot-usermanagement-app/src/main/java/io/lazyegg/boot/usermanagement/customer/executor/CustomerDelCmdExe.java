package io.lazyegg.boot.usermanagement.customer.executor;

import com.alibaba.cola.dto.Response;
import io.lazyegg.boot.usermanagement.domain.customer.gateway.CustomerGateway;
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
    private CustomerGateway customerGateway;

    public Response execute(CustomerDelCmd customerDelCmd) {
        customerGateway.removeCustomer(customerDelCmd);
        return Response.buildSuccess();
    }
}

