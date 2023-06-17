package io.lazyegg.boot.usermanagement.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import io.lazyegg.boot.component.db.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("customer")
@EqualsAndHashCode(callSuper = true)
public class CustomerDO extends BaseDO {

}
