package io.lazyegg.boot.component.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * BaseNoLogicDeleteDO
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Data
public class BaseNoLogicDeleteDO extends BasePrimaryDO {

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;
    @TableField(fill = FieldFill.INSERT)
    private String createOrg;
}
