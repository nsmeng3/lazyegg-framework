package io.lazyegg.boot.usermanagement.customer;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import io.lazyegg.boot.component.db.entity.BaseDO;

@Data
@TableName("customer")
public class CustomerDO extends BaseDO{

}
