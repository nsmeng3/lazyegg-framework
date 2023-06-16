package io.lazyegg.boot.component.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 主键DO
 * BasePrimaryDO
 *
 * @author DifferentW  nsmeng3@163.com
 */


@Data
@MappedSuperclass
public class BasePrimaryDO {

    @Id
    private String id;

    private String tenantId;

}
