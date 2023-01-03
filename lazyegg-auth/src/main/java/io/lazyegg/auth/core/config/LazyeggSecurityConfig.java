package io.lazyegg.auth.core.config;

import io.lazyegg.auth.core.handler.*;
import io.lazyegg.auth.core.provider.UsernameAndPasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

/**
 * SecurityConfig
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Configuration
public class LazyeggSecurityConfig {
    private static final String[] URL_WHITELIST = {
        "/a/**",
        "/login",
        "/doc.html",
        "/webjars/**",
        "/swagger-resources",
        "/v2/**",

    };

    @Resource
    private LoginSuccessHandler successHandler;
    @Resource
    private LoginFailureHandler failureHandler;
    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Resource
    private JwtLogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private UsernameAndPasswordAuthenticationProvider usernameAndPasswordAuthenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler))
            // 禁用session
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(configurer -> {
                configurer.successHandler(successHandler);
                configurer.failureHandler(failureHandler);
            })
            .authorizeHttpRequests(authorizeHttpRequests -> {
                authorizeHttpRequests.mvcMatchers(URL_WHITELIST).permitAll();
                authorizeHttpRequests.anyRequest().authenticated();
            })
            // 异常处理
            .exceptionHandling(
                httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            // 认证管理
            .authenticationManager(new ProviderManager(usernameAndPasswordAuthenticationProvider))
            // 过滤器
            .addFilter(jwtAuthenticationFilter())
        ;
        return http.build();
    }

    @Bean
    LeggAuthenticationFilter jwtAuthenticationFilter() {
        LeggAuthenticationFilter leggAuthenticationFilter = new LeggAuthenticationFilter(authentication -> null);
        return leggAuthenticationFilter;
    }

}
