package io.lazyegg.boot.customermanage.customer;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.lazyegg.boot.customermanage.api.CustomerServiceI;
import io.lazyegg.boot.customermanage.customer.executor.CustomerAddCmdExe;
import io.lazyegg.boot.customermanage.customer.executor.CustomerDelCmdExe;
import io.lazyegg.boot.customermanage.customer.executor.CustomerUpdateCmdExe;
import io.lazyegg.boot.customermanage.customer.executor.query.*;
import io.lazyegg.boot.customermanage.dto.*;
import io.lazyegg.boot.customermanage.dto.data.CustomerDTO;
import io.lazyegg.core.exception.CatchAndLog;
import io.lazyegg.core.page.PageLongResponse;
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

    public Response addCustomer(CustomerAddCmd customerAddCmd) {
        customerAddCmdExe.execute(customerAddCmd);
        return Response.buildSuccess();
    }

    @Override
    public Response removeCustomer(CustomerDelCmd customerDelCmd) {
        customerDelCmdExe.execute(customerDelCmd);
        return Response.buildSuccess();
    }

    @Override
    public Response updateCustomer(CustomerUpdateCmd cmd) {
        CustomerDTO customerDTO = customerUpdateCmdExe.execute(cmd);
        return SingleResponse.of(customerDTO);
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

}
