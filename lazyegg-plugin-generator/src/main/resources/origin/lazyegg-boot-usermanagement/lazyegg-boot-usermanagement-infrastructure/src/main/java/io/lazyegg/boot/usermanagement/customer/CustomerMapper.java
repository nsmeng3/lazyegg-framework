package io.lazyegg.boot.usermanagement.customer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;


@Mapper
public interface CustomerMapper extends JoinBaseMapper<CustomerDO> {

    CustomerDO getById(String customerId);
}
