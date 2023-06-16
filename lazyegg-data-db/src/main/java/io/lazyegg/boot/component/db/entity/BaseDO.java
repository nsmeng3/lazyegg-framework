package io.lazyegg.boot.component.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * BaseDO
 *
 * @author DifferentW  nsmeng3@163.com
 */


@Data
@MappedSuperclass
public class BaseDO extends BaseNoLogicDeleteDO {

    @TableField(fill = FieldFill.INSERT, value = "is_deleted")
    @TableLogic
    @Column(name = "is_deleted")
    private Boolean deleted;

}
