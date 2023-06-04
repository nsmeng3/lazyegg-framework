package io.lazyegg.auth.filter;

import com.alibaba.fastjson.JSONObject;
import io.lazyegg.auth.handler.LoginFailureHandler;
import io.lazyegg.auth.handler.LoginSuccessHandler;
import io.lazyegg.auth.util.BodyReaderHttpServletRequestWrapper;
import io.lazyegg.auth.util.HttpHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordBodyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String DEFAULT_LOGIN_URL = "/login";
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    public UsernamePasswordBodyAuthenticationFilter(String loginProcessingUrl, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginProcessingUrl, "POST"));
    }

    public UsernamePasswordBodyAuthenticationFilter(AuthenticationManager authenticationManager) {
        this(DEFAULT_LOGIN_URL, authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("UsernamePasswordBodyAuthenticationFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String reqMethod = httpServletRequest.getMethod();
        if ((!"GET".equals(reqMethod)) && (!"DELETE".equals(reqMethod))) {
            // 防止流读取一次后就没有了, 所以需要将流继续写出去
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
            super.doFilter(requestWrapper, response, chain);
        } else {
            super.doFilter(request, response, chain);
        }
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String body = getBody(request);
        JSONObject jsonObject = JSONObject.parseObject(body);
        return jsonObject.getString(getPasswordParameter());
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String body = getBody(request);
        JSONObject jsonObject = JSONObject.parseObject(body);
        return jsonObject.getString(getUsernameParameter());
    }

    private static String getBody(HttpServletRequest request) {
        return HttpHelper.getBodyString(request);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        loginSuccessHandler.onAuthenticationSuccess(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        loginFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
