package io.lazyegg.auth.core.handler;

import io.lazyegg.auth.core.util.LeggResponsePrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * JwtLogoutSuccessHandler
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(JwtLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // TODO jwt失效

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("message", "登出成功");
        LeggResponsePrintUtil.writeJson(response, result);
    }
}

