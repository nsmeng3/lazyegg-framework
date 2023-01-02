package io.lazyegg.auth.core.handler;

import io.jsonwebtoken.Claims;
import io.lazyegg.auth.core.exception.InvalidTokenException;
import io.lazyegg.auth.core.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JwtAuthenticationFilter
 *
 * @author DifferentW  nsmeng3@163.com
 */

public class LeggAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(LeggAuthenticationFilter.class);

    public LeggAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearer = request.getHeader("Authorization");

        if (StringUtils.isBlank(bearer)) {
            chain.doFilter(request, response);
            return;
        }
        String token = bearer.replace("Bearer ", "");
        if (!JwtUtil.verifyToken(token)) {
            throw new InvalidTokenException();
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Claims claims = JwtUtil.parseJwt(token);
        String username = claims.get("username", String.class);

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);


    }
}

