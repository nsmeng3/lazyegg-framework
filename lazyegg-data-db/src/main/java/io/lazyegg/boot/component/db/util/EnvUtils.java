package io.lazyegg.boot.component.db.util;

/**
 * 应用部署环境工具类
 */
public class EnvUtils {
    public static String currEnv() {
        // TODO 应用启动时, 读取的机器部署环境变量, 这里简化为返回固定值演示
        return "test1";
    }
}
