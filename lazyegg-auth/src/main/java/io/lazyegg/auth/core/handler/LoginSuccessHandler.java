package io.lazyegg.auth.core.handler;

import io.lazyegg.auth.core.util.JwtUtil;
import io.lazyegg.auth.core.util.LeggResponsePrintUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * LoginSuccessHandler
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        response.setContentType("application/json; charset=UTF-8");

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", authentication.getName());
        String jwt = JwtUtil.createJwt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000), claims);
        HashMap<String, Object> o = new HashMap<>();
        o.put("code", HttpStatus.OK.value());
        o.put("message", "登录成功");
//        o.put("access_token", jwt);
        HashMap<String, Object> data = new HashMap<>();
        data.put("username", "admin");
        data.put("password", "");
        data.put("avatar", "https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png");
        data.put("status", 1);
        data.put("telephone", "");
        data.put("lastLoginIp", "27.154.74.117");
        data.put("lastLoginTime", 1534837621348L);
        data.put("creatorId", "admin");
        data.put("createTime", 1497160610259L);
        data.put("deleted", 0);
        data.put("roleId", "admin");
        data.put("lang", "zh-CN");
        data.put("token", jwt);
        o.put("data", data);
        response.setHeader("Authorization", "Bearer " + jwt);
        LeggResponsePrintUtil.writeJson(response, o, HttpStatus.OK);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        onAuthenticationSuccess(request, response, authentication);
    }
}

