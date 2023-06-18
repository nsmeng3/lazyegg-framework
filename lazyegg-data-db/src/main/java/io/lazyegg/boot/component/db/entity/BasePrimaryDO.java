package io.lazyegg.boot.component.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String tenantId;

}
