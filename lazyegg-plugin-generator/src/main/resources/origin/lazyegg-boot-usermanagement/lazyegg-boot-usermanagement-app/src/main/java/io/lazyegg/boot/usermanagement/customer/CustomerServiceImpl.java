package io.lazyegg.boot.usermanagement.customer;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.boot.usermanagement.api.CustomerServiceI;
import io.lazyegg.boot.usermanagement.customer.executor.CustomerAddCmdExe;
import io.lazyegg.boot.usermanagement.customer.executor.CustomerDelCmdExe;
import io.lazyegg.boot.usermanagement.customer.executor.CustomerUpdateCmdExe;
import io.lazyegg.boot.usermanagement.customer.executor.query.*;
import io.lazyegg.boot.usermanagement.dto.*;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@CatchAndLog
public class CustomerServiceImpl implements CustomerServiceI {

    @Resource
    private CustomerAddCmdExe customerAddCmdExe;
    @Resource
    private CustomerDelCmdExe customerDelCmdExe;

    @Resource
    private CustomerUpdateCmdExe customerUpdateCmdExe;
    @Resource
    private CustomerGetQryExe customerGetQryExe;
    @Resource
    private CustomerListQryExe customerListQryExe;
    @Resource
    private CustomerPageQryExe customerPageQryExe;
    @Resource
    private CustomerCountQryExe customerCountQryExe;

    @Resource
    private CustomerListByNameQryExe customerListByNameQryExe;

    public Response addCustomer(CustomerAddCmd customerAddCmd) {
        return customerAddCmdExe.execute(customerAddCmd);
    }

    @Override
    public MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry) {
        return customerListByNameQryExe.execute(customerListByNameQry);
    }

    @Override
    public void removeCustomer(CustomerDelCmd customerDelCmd) {
        customerDelCmdExe.execute(customerDelCmd);
    }

    @Override
    public Response updateCustomer(CustomerUpdateCmd cmd) {
        return customerUpdateCmdExe.execute(cmd);
    }

    @Override
    public SingleResponse<CustomerDTO> getCustomer(CustomerGetQry qry) {
        return customerGetQryExe.execute(qry);
    }

    @Override
    public MultiResponse<CustomerDTO> listCustomer(CustomerListQry listQry) {
        return customerListQryExe.execute(listQry);
    }

    @Override
    public MultiResponse<CustomerDTO> pageCustomer(CustomerPageQry pageQry) {
        return customerPageQryExe.execute(pageQry);
    }

    @Override
    public SingleResponse<CustomerDTO> countCustomer(CustomerCountQry countQry) {
        return customerCountQryExe.execute(countQry);
    }


}
