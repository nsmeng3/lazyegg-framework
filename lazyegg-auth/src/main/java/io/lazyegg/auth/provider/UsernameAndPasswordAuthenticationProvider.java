package io.lazyegg.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录处理
 *
 * @author DifferentW  nsmeng3@163.com
 */
@Component
public class UsernameAndPasswordAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String principal = String.valueOf(authentication.getPrincipal());
        String credentials = String.valueOf(authentication.getCredentials());
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal);
        String password = userDetails.getPassword();
        boolean matches = passwordEncoder.matches(credentials, password);
        if (!matches) {
            throw new BadCredentialsException("账号或密码错误");
        }
        return new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

