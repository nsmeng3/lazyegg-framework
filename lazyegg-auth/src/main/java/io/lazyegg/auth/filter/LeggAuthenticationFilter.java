package io.lazyegg.auth.filter;

import io.jsonwebtoken.Claims;
import io.lazyegg.auth.util.JwtUtil;
import io.lazyegg.auth.util.LeggResponsePrintUtil;
import io.lazyegg.auth.util.SpringUtil;
import io.lazyegg.core.CurrentUserContextHandler;
import io.lazyegg.core.UserInfo;
import io.lazyegg.core.ac.UserAccInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * JwtAuthenticationFilter
 *
 * @author DifferentW  nsmeng3@163.com
 */

public class LeggAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(LeggAuthenticationFilter.class);

    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    public LeggAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("LeggAuthenticationFilter");
        String bearer = request.getHeader("Authorization");

        if (StringUtils.isBlank(bearer)) {
            chain.doFilter(request, response);
            return;
        }
        String token = bearer.replace("Bearer ", "");
        if (!JwtUtil.verifyToken(token)) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("message", "无效令牌");
            LeggResponsePrintUtil.writeJson(response, result, HttpStatus.UNAUTHORIZED);
            return;
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Claims claims = JwtUtil.parseJwt(token);
        String username = claims.get("username", String.class);
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UserAccInterface userInfoService = SpringUtil.getBean(UserAccInterface.class);
        if (userInfoService != null) {
            UserInfo userInfo = userInfoService.getUserInfo(username);
            CurrentUserContextHandler.set(new CurrentUserContextHandler.User(userInfo.getUserId(), userInfo.getOrgId()));
            // 加载角色及权限 TODO 添加缓存
            List<String> roles = userInfo.getRoles();
            roles.forEach(role -> {
                role = DEFAULT_ROLE_PREFIX + role;
                authorities.add(new SimpleGrantedAuthority(role));
            });
            List<String> permissions = userInfo.getPermissions();
            permissions.forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission));
            });
        } else {
            log.warn("当前系统未加载用户管理模块(usermanagement)");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }
}
