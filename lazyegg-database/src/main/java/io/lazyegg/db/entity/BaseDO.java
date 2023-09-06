package io.lazyegg.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * BaseDO
 *
 * @author DifferentW  nsmeng3@163.com
 */


@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class BaseDO extends BaseNoLogicDeleteDO {

    @TableField(fill = FieldFill.INSERT, value = "is_deleted")
    @TableLogic(value = "false", delval = "true")
    @Column(name = "is_deleted")
    private Boolean deleted;

}
