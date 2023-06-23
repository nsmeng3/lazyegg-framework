package io.lazyegg.boot.usermanagement.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import io.lazyegg.db.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("customer")
@Table(name = "customer")
@Entity
public class CustomerDO extends BaseDO{

}
