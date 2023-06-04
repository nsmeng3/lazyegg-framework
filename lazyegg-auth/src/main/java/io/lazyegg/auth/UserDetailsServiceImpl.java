package io.lazyegg.auth;

import io.lazyegg.auth.util.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.Map;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final String KEY = "lazyegg.auth.source";

    public UserDetailsServiceImpl() {
        System.setProperty(KEY, "defaultUserService");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Map<String, UserService> beansOfType = applicationContext.getBeansOfType(UserService.class);
        String source = System.getProperty(KEY);
        UserService userService = beansOfType.get(source);
        if (userService == null) {
            userService = SpringUtil.getBean("defaultUserService", UserService.class);
        }
        Assert.notNull(userService, "认证源不存在");
        return userService.loadUserByUsername(username);
    }
}
