package io.lazyegg.boot.usermanagement.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.boot.usermanagement.dto.*;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;

public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd cmd);

    MultiResponse<CustomerDTO> listByName(CustomerListByNameQry qry);

    Response removeCustomer(CustomerDelCmd cmd);

    Response updateCustomer(CustomerUpdateCmd cmd);

    SingleResponse<CustomerDTO> getCustomer(CustomerGetQry qry);

    MultiResponse<CustomerDTO> listCustomer(CustomerListQry listQry);

    MultiResponse<CustomerDTO> pageCustomer(CustomerPageQry pageQry);

    SingleResponse<CustomerDTO> countCustomer(CustomerCountQry countQry);
}
