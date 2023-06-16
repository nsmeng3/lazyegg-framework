package io.lazyegg.core;

import io.lazyegg.core.exception.DefaultResponseHandler;
import io.lazyegg.core.exception.ResponseHandlerI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * CorePackageScan
 * 包扫描配置
 *
 * @author DifferentW  nsmeng3@163.com
 */

@ComponentScan(basePackages = "io.lazyegg.core")
public class CorePackageScan {

    /**
     * ApplicationContextHelper
     *
     * @author Frank Zhang
     * @date 2020-11-14 1:58 PM
     */
    @Component("lazyeggCatchLogApplicationContextHelper")
    @Slf4j
    public static class ApplicationContextHelper implements ApplicationContextAware {
        private static ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            ApplicationContextHelper.applicationContext = applicationContext;
        }

        public static <T> T getBean(Class<T> targetClz) {
            T beanInstance = null;
            //优先按type查
            try {
                beanInstance = (T) applicationContext.getBean(targetClz);
            } catch (Exception e) {
            }

            //按name查
            try {
                if (beanInstance == null) {
                    String simpleName = targetClz.getSimpleName();
                    //首字母小写
                    simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
                    beanInstance = (T) applicationContext.getBean(simpleName);
                }
            } catch (Exception e) {
                log.warn("No bean found for " + targetClz.getCanonicalName());
            }
            return beanInstance;
        }

        public static Object getBean(String claz) {
            return ApplicationContextHelper.applicationContext.getBean(claz);
        }

        public static <T> T getBean(String name, Class<T> requiredType) {
            return ApplicationContextHelper.applicationContext.getBean(name, requiredType);
        }

        public static <T> T getBean(Class<T> requiredType, Object... params) {
            return ApplicationContextHelper.applicationContext.getBean(requiredType, params);
        }

        public static ApplicationContext getApplicationContext() {
            return applicationContext;
        }
    }

    public static class ResponseHandlerFactory {

        public static ResponseHandlerI get() {
            if (ApplicationContextHelper.getBean(ResponseHandlerI.class) != null) {
                return ApplicationContextHelper.getBean(ResponseHandlerI.class);
            }
            return new DefaultResponseHandler();
        }
    }
}
