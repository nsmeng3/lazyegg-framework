package io.lazyegg.sdk.utils;

import static com.aliyun.oss.internal.OSSUtils.COMMON_RESOURCE_MANAGER;

public abstract class CommonUtils {
    public static void assertParameterNotNull(Object param, String paramName) {
        if (param == null) {
            throw new NullPointerException(COMMON_RESOURCE_MANAGER.getFormattedString("ParameterIsNull", paramName));
        }
    }
}
