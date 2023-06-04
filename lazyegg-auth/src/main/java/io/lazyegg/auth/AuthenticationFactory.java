package io.lazyegg.auth;

import io.lazyegg.auth.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.Map;

@Slf4j
public class AuthenticationFactory {
    private static final AuthenticationFactory INSTANCE = new AuthenticationFactory();

    public static Map<String, AuthenticationProvider> getAuthenticationProvider() {
        return INSTANCE.getAuthenticationProviderMap();
    }

    /**
     * 获取所有认证提供者
     *
     * @return
     */
    @Cacheable(value = "authenticationProviderMap")
    public Map<String, AuthenticationProvider> getAuthenticationProviderMap() {
        log.info("获取所有认证提供者名称");
        return SpringUtil.getApplicationContext().getBeansOfType(AuthenticationProvider.class);
    }
}
