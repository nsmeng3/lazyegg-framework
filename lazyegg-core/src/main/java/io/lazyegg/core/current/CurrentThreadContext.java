package io.lazyegg.core.current;

import java.util.HashMap;
import java.util.Map;

/**
 * 当前线程上下文
 */
public abstract class CurrentThreadContext {
    private static final ThreadLocal<Map<String, Object>> CURRENT_THREAD_LOCAL = new ThreadLocal<>();

    public static final long DEFAULT_TENANT_ID = 0L;
    public static final String TENANT_ID = "tenant_id";

    /**
     * 设置租户id
     *
     * @param tenantId
     */
    public static void setTenantId(long tenantId) {
        // 判断线程变量是否为空
        Map<String, Object> map = getMap();
        map.put(TENANT_ID, tenantId);
        CURRENT_THREAD_LOCAL.set(map);
    }

    /**
     * 获取租户id
     *
     * @return
     */
    public static long getTenantId() {
        Map<String, Object> map = getMap();
        Object tenantId = map.get(TENANT_ID);
        if (tenantId == null) {
            return DEFAULT_TENANT_ID;
        }
        return (long) tenantId;
    }

    /**
     * 判断线程变量是否为空，为空则赋值
     *
     * @return
     */
    private static Map<String, Object> getMap() {
        Map<String, Object> map = CURRENT_THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>();
            CURRENT_THREAD_LOCAL.set(map);
        }
        return map;
    }

}
