package io.lazyegg.boot.usermanagement.customer;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.lazyegg.boot.usermanagement.api.CustomerServiceI;
import io.lazyegg.boot.usermanagement.customer.executor.CustomerAddCmdExe;
import io.lazyegg.boot.usermanagement.customer.executor.CustomerDelCmdExe;
import io.lazyegg.boot.usermanagement.customer.executor.CustomerUpdateCmdExe;
import io.lazyegg.boot.usermanagement.customer.executor.query.*;
import io.lazyegg.core.page.PageLongResponse;
import io.lazyegg.boot.usermanagement.dto.*;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


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
        customerAddCmdExe.execute(customerAddCmd);
        return Response.buildSuccess();
    }

    @Override
    public MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry) {
        List<CustomerDTO> execute = customerListByNameQryExe.execute(customerListByNameQry);
        return MultiResponse.of(execute);
    }

    @Override
    public Response removeCustomer(CustomerDelCmd customerDelCmd) {
        customerDelCmdExe.execute(customerDelCmd);
        return Response.buildSuccess();
    }

    @Override
    public Response updateCustomer(CustomerUpdateCmd cmd) {
        customerUpdateCmdExe.execute(cmd);
        return Response.buildSuccess();
    }

    @Override
    public SingleResponse<CustomerDTO> getCustomer(CustomerGetQry qry) {
        CustomerDTO execute = customerGetQryExe.execute(qry);
        return SingleResponse.of(execute);
    }

    @Override
    public MultiResponse<CustomerDTO> listCustomer(CustomerListQry listQry) {
        List<CustomerDTO> execute = customerListQryExe.execute(listQry);
        return MultiResponse.of(execute);
    }

    @Override
    public PageLongResponse<CustomerDTO> pageCustomer(CustomerPageQry pageQry) {
        PageDTO<CustomerDO> page = customerPageQryExe.execute(pageQry);
        List<CustomerDO> records = page.getRecords();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        for (CustomerDO record : records) {
            CustomerDTO e = new CustomerDTO();
            BeanUtils.copyProperties(record, e);
            customerDTOS.add(e);
        }
        return PageLongResponse.of(customerDTOS, page.getTotal(), page.getSize(), page.getCurrent());
    }

    @Override
    public SingleResponse<CustomerDTO> countCustomer(CustomerCountQry countQry) {
        CustomerDTO execute = customerCountQryExe.execute(countQry);
        return SingleResponse.of(execute);
    }


}
