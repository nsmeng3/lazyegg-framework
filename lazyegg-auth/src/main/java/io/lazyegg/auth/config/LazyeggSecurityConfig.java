package io.lazyegg.auth.config;

import io.lazyegg.auth.handler.*;
import io.lazyegg.auth.provider.UsernameAndPasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * SecurityConfig
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Configuration
@ComponentScan(basePackages = "io.lazyegg.auth")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class LazyeggSecurityConfig {
    private static final String LOGIN_URL = "/auth/login";
    private static final String LOGOUT_URL = "/auth/logout";
    private static final String[] URL_WHITELIST = {
        "/a/**",
        LOGIN_URL,
        LOGOUT_URL,
        "/auth/logout",
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
//        ProviderManager authenticationManager = new ProviderManager(usernameAndPasswordAuthenticationProvider);
        http
            .cors()
            .and()
            .csrf().disable()
            .logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler).logoutUrl(LOGOUT_URL))
            // 禁用session
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            /// 在UsernamePasswordBodyAuthenticationFilter中实现了成功与失败的处理
//            .formLogin(configurer -> {
//                configurer.successHandler(successHandler);
//                configurer.failureHandler(failureHandler);
//                configurer.loginProcessingUrl(LOGIN_URL);
//            })
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
//            .authenticationManager(authenticationManager)
            // 过滤器
            .addFilterBefore(usernamePasswordBodyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilter(jwtAuthenticationFilter())
        ;
        return http.build();
    }

    @Bean
    LeggAuthenticationFilter jwtAuthenticationFilter() {
        LeggAuthenticationFilter leggAuthenticationFilter = new LeggAuthenticationFilter(authentication -> null);
        return leggAuthenticationFilter;
    }

    @Bean
    UsernamePasswordBodyAuthenticationFilter usernamePasswordBodyAuthenticationFilter() {
        ProviderManager providerManager = new ProviderManager(usernameAndPasswordAuthenticationProvider);
        UsernamePasswordBodyAuthenticationFilter leggAuthenticationFilter = new UsernamePasswordBodyAuthenticationFilter(LOGIN_URL, providerManager);
        return leggAuthenticationFilter;
    }


}
