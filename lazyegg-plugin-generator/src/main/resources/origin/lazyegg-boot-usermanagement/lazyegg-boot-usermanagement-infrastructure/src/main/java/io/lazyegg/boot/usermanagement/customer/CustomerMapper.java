package io.lazyegg.boot.usermanagement.customer;

import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CustomerMapper extends JoinBaseMapper<CustomerDO> {

}
