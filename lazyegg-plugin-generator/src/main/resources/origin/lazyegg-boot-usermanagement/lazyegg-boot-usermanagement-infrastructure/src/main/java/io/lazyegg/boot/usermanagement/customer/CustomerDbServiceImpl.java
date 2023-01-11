package io.lazyegg.boot.usermanagement.customer;

import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import org.springframework.stereotype.Service;

/**
 * CustomerDbServiceImpl
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Service
public class CustomerDbServiceImpl extends JoinServiceImpl<CustomerMapper, CustomerDO> implements CustomerDbService {

}

