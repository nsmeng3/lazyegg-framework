package io.lazyegg.boot.usermanagement.dto;

import com.alibaba.cola.dto.PageQuery;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CustomerPageQry extends PageQuery {
    private String id;
}
