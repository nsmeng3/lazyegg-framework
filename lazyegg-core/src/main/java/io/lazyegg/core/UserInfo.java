package io.lazyegg.core;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UserInfo
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Data
public class UserInfo {
    private static final Logger log = LoggerFactory.getLogger(UserInfo.class);

    private String userId;

    private String orgId;

}

