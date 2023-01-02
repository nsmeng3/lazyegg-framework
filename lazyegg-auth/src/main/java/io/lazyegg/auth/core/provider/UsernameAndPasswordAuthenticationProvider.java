package io.lazyegg.auth.core.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 登录处理
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Component
public class UsernameAndPasswordAuthenticationProvider implements AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(UsernameAndPasswordAuthenticationProvider.class);


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        if (("user2").equals(authentication.getPrincipal())) {

            return new UsernamePasswordAuthenticationToken("user", "p");
        } else {
            throw new BadCredentialsException("账号或密码错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

