package io.lazyegg.auth.config;

import com.alibaba.cola.exception.SysException;
import io.lazyegg.auth.AuthenticationFactory;
import io.lazyegg.auth.UserDetailsServiceImpl;
import io.lazyegg.auth.filter.GlobalExceptionHandlerFilter;
import io.lazyegg.auth.filter.JwtAuthenticationFilter;
import io.lazyegg.auth.handler.JwtAuthenticationEntryPoint;
import io.lazyegg.auth.handler.JwtLogoutSuccessHandler;
import io.lazyegg.auth.util.SpringUtil;
import io.lazyegg.core.annotation.UrlIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * SecurityConfig
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Configuration
@ComponentScan(basePackages = "io.lazyegg.auth")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class LazyeggSecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(LazyeggSecurityConfig.class);

    private static final String LOGIN_URL = "/auth/login";
    private static final String LOGOUT_URL = "/auth/logout";
    private static String[] URL_WHITELIST = {
            "/a/**",
            LOGIN_URL,
            LOGOUT_URL,
            "/auth/logout",
            "/doc.html",
            "/webjars/**",
            "/swagger-resources",
            "/v2/**",
            "/login"

    };

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Resource
    private JwtLogoutSuccessHandler logoutSuccessHandler;

    /**
     * 添加放行名单
     */
    private static void addWhiteList() {
        RequestMappingInfoHandlerMapping bean = SpringUtil.getBean(RequestMappingInfoHandlerMapping.class);
        if (bean == null) {
            throw new SysException("放行名单加载失败");
        }
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
            if (handlerMethod.getMethodAnnotation(UrlIgnore.class) == null) {
                return;
            }
            String[] array = requestMappingInfo.getPatternValues().toArray(new String[0]);
            // array 合并到 URL_WHITELIST中
            URL_WHITELIST = Arrays.copyOf(URL_WHITELIST, URL_WHITELIST.length + array.length);
            System.arraycopy(array, 0, URL_WHITELIST, URL_WHITELIST.length - array.length, array.length);
        });
    }

    /**
     * 配置安全过滤器链
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        addWhiteList();
        http
                .cors()
                .and()
                .csrf().disable()
                .logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler)
                        .logoutUrl(LOGOUT_URL))
                // 禁用session
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests.mvcMatchers(URL_WHITELIST).permitAll();
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                .authenticationManager(authenticationManager())
                // 异常处理
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                 // 过滤器
                .addFilterBefore(new GlobalExceptionHandlerFilter(), DisableEncodeUrlFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        Map<String, AuthenticationProvider> authenticationProviderNames = AuthenticationFactory.getAuthenticationProvider();
        return new ProviderManager(authenticationProviderNames.values().toArray(new AuthenticationProvider[0]));
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
