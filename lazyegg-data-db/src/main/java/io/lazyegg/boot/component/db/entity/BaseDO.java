package io.lazyegg.boot.component.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * BaseDO
 *
 * @author DifferentW  nsmeng3@163.com
 */


@Data
public class BaseDO extends BaseNoLogicDeleteDO{

    @TableField(fill = FieldFill.INSERT, value = "is_deleted")
    @TableLogic
    private Boolean deleted;

}
