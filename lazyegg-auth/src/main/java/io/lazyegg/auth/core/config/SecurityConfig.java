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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Configuration
public class SecurityConfig {
    private static final String[] URL_WHITELIST = {
        "/a/**",
        "/login",
        "/doc.html",
        "/webjars/**",
        "/swagger-resources",
        "/v2/**",

    };

    private final LoginSuccessHandler successHandler;
    private final LoginFailureHandler failureHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtLogoutSuccessHandler logoutSuccessHandler;
    private final UsernameAndPasswordAuthenticationProvider usernameAndPasswordAuthenticationProvider;
//    private final ProviderManager providerManager;



    public SecurityConfig(LoginSuccessHandler successHandler, LoginFailureHandler failureHandler, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtLogoutSuccessHandler logoutSuccessHandler, UsernameAndPasswordAuthenticationProvider usernameAndPasswordAuthenticationProvider) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.usernameAndPasswordAuthenticationProvider = usernameAndPasswordAuthenticationProvider;
//        this.providerManager = providerManager;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .logout(logout-> {
                logout.logoutUrl("/a/logout");
                logout.logoutSuccessHandler(logoutSuccessHandler);
            })
            // 禁用session
            .sessionManagement(sessionManagement -> {
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .formLogin(configurer -> {
//                configurer.loginProcessingUrl("/a/login");
                configurer.successHandler(successHandler);
                configurer.failureHandler(failureHandler);
            })
            .authorizeHttpRequests(authorizeHttpRequests -> {
                authorizeHttpRequests.mvcMatchers(URL_WHITELIST).permitAll();
                authorizeHttpRequests.anyRequest().authenticated();
            })
            // 异常处理
            .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
            })
            // 认证管理
//            .authenticationManager(providerManager)
            // 过滤器
            .addFilter(jwtAuthenticationFilter())
        ;
        return http.build();
    }

    @Bean
    LeggAuthenticationFilter jwtAuthenticationFilter() {
        LeggAuthenticationFilter leggAuthenticationFilter = new LeggAuthenticationFilter(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        });
        return leggAuthenticationFilter;
    }

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
