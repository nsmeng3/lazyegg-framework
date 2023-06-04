package io.lazyegg.auth.provider;

import io.lazyegg.auth.JwtAuthenticationToken;
import io.lazyegg.auth.JwtTokenCache;
import io.lazyegg.auth.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("JwtAuthenticationProvider.authenticate");
        String token = (String) authentication.getCredentials();
        if (JwtTokenUtil.validateToken(token)) {
            String auth = JwtTokenUtil.getSubject(token);
            return new UsernamePasswordAuthenticationToken(auth, null, null);
        }
        throw new BadCredentialsException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
