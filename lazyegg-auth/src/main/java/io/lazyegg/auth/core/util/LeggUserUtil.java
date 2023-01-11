package io.lazyegg.auth.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户工具类
 * LeggUserUtil
 *
 * @author DifferentW  nsmeng3@163.com
 */

public class LeggUserUtil {
    private static final Logger log = LoggerFactory.getLogger(LeggUserUtil.class);

    public static String getCurrentUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return String.valueOf(authentication.getPrincipal());
    }
}

