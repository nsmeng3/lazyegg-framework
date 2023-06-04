package io.lazyegg.auth.handler;

import io.lazyegg.auth.util.LeggResponsePrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 认证失败处理
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        HashMap<String, Object> o = new HashMap<>();
        o.put("code", HttpStatus.UNAUTHORIZED.value());
        o.put("message", "认证失败,请登录");
        LeggResponsePrintUtil.writeJson(response, o, HttpStatus.UNAUTHORIZED);
    }
}
