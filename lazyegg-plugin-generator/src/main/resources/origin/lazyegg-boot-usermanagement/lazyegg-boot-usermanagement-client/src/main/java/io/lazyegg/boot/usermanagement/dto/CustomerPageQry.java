package io.lazyegg.boot.customermanage.dto;

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
