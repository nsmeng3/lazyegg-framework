package io.lazyegg.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

/**
 * CorePackageScan
 * 包扫描配置
 *
 * @author DifferentW  nsmeng3@163.com
 */

@ComponentScan(basePackages = "io.lazyegg.core")
public class CorePackageScan {
    private static final Logger log = LoggerFactory.getLogger(CorePackageScan.class);

}

