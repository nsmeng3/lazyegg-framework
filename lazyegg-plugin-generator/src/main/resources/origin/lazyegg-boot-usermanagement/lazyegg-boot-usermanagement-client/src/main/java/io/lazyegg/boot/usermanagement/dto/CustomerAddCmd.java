package io.lazyegg.boot.usermanagement.dto;

import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import lombok.Data;

@Data
public class CustomerAddCmd {
    private CustomerDTO customerDTO;

}
