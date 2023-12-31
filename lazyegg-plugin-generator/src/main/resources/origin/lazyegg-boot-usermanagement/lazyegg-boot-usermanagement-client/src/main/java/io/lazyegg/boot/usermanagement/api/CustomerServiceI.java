package io.lazyegg.boot.usermanagement.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.boot.usermanagement.dto.*;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import io.lazyegg.core.page.PageLongResponse;

public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd cmd);

    Response removeCustomer(CustomerDelCmd cmd);

    Response updateCustomer(CustomerUpdateCmd cmd);

    SingleResponse<CustomerDTO> getCustomer(CustomerGetQry qry);

    MultiResponse<CustomerDTO> listCustomer(CustomerListQry listQry);

    PageLongResponse<CustomerDTO> pageCustomer(CustomerPageQry pageQry);

}
