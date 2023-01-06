package io.lazyegg.boot.component.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * BaseDO
 *
 * @author DifferentW  nsmeng3@163.com
 */


@Data
public class BaseDO {

    private String id;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT, value = "is_enabled")
    private Boolean enabled;

    @TableField(fill = FieldFill.INSERT, value = "is_deleted")
    private Boolean deleted;
}
