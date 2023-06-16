package io.lazyegg.boot.customermanage.dto;

import com.alibaba.cola.dto.Query;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerListQry extends Query {
    private String id;
}
