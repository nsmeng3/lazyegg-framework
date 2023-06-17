package io.lazyegg.boot.usermanagement.dto;

import com.alibaba.cola.dto.Query;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CustomerListQry extends Query {
    private String id;

    public CustomerListQry(String id) {
        this.id = id;
    }
}
