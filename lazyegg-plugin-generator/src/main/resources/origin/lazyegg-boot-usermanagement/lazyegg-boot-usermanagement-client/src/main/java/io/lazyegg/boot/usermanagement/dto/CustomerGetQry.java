package io.lazyegg.boot.usermanagement.dto;

import com.alibaba.cola.dto.Query;
import lombok.Data;

@Data
public class CustomerGetQry extends Query {
    private String id;

    public CustomerGetQry(String id) {
        this.id = id;
    }
}
