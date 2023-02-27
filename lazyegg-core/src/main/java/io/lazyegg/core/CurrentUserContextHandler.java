package io.lazyegg.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 当前用户上下问题
 *
 * @author DifferentW  nsmeng3@163.com
 */
public abstract class CurrentUserContextHandler {

    private static final ThreadLocal<User> CURRENT_USER = new ThreadLocal<User>();

    /**
     * 获取当前线程数据源变量
     */
    public static User get() {
        return CURRENT_USER.get();
    }

    /**
     * 设置当前线程数据源变量
     *
     * @param datasourceType 数据源类型
     */
    public static void set(User datasourceType) {
        CURRENT_USER.set(datasourceType);
    }

    /**
     * 当前线程数据源变量
     */
    public static void clean() {
        CURRENT_USER.remove();
    }

    @Data
    @AllArgsConstructor
    public static class User {
        private String userId;
        private String orgId;
    }
}
