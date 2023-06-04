package io.lazyegg.auth.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class GlobalExceptionHandlerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
        log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            // 异常处理逻辑
            // 比如打印日志、返回错误信息等
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":500,\"msg\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    public void destroy() {
        // 销毁操作
    }
}
