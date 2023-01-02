package io.lazyegg.auth.core.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * JwtAuthenticationManager
 * 处理身份验证请求
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class OtherAuthenticationManager implements AuthenticationProvider {

    /**
     * 身份认证 认证失败，需抛出AuthenticationException异常
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        throw new BadCredentialsException("认证失败");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

}

