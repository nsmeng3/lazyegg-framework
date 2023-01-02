package io.lazyegg.boot.usermanagement.dto;

import lombok.Data;

/**
 * CustomerDelCmd
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Data
public class CustomerDelCmd {

    private String id;

    public CustomerDelCmd(String id) {
        this.id = id;
    }
}
