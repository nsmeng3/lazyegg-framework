package io.lazyegg.auth.core.handler;

import io.lazyegg.auth.core.util.LeggResponsePrintUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * LoginSuccessHandler
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HashMap<String, Object> o = new HashMap<>();
        o.put("code", 500);
        o.put("message", exception.getMessage());
        if (exception instanceof BadCredentialsException) {
            o.put("message", "账号或密码错误");
        }

        LeggResponsePrintUtil.writeJson(response, o);
    }
}

