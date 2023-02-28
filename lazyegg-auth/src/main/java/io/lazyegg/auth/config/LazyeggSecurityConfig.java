package io.lazyegg.auth.config;

import com.alibaba.cola.exception.SysException;
import io.lazyegg.auth.handler.*;
import io.lazyegg.auth.provider.UsernameAndPasswordAuthenticationProvider;
import io.lazyegg.auth.util.SpringUtil;
import io.lazyegg.core.annotation.LeggAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import javax.annotation.Resource;
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
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Resource
    private JwtLogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private UsernameAndPasswordAuthenticationProvider usernameAndPasswordAuthenticationProvider;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            RequestMappingInfoHandlerMapping bean = SpringUtil.getBean(RequestMappingInfoHandlerMapping.class);
            if (bean == null) {
                throw new SysException("放行名单加载失败");
            }
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
            handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
                if (handlerMethod.getMethodAnnotation(LeggAnno.class) == null) {
                    return;
                }
                for (RequestMethod method : requestMappingInfo.getMethodsCondition().getMethods()) {
                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    if (patternsCondition == null) {
                        continue;
                    }
                    for (String pattern : patternsCondition.getPatterns()) {
                        switch (method) {
                            case GET: {
                                web.ignoring().mvcMatchers(HttpMethod.GET, pattern);
                                break;
                            }
                            case POST: {
                                web.ignoring().mvcMatchers(HttpMethod.POST, pattern);
                                break;
                            }
                            case PUT: {
                                web.ignoring().mvcMatchers(HttpMethod.PUT, pattern);
                                break;
                            }
                            case DELETE: {
                                web.ignoring().mvcMatchers(HttpMethod.DELETE, pattern);
                                break;
                            }
                            case OPTIONS: {
                                web.ignoring().mvcMatchers(HttpMethod.OPTIONS, pattern);
                                break;
                            }
                            case PATCH: {
                                web.ignoring().mvcMatchers(HttpMethod.PATCH, pattern);
                                break;
                            }
                            case HEAD: {
                                web.ignoring().mvcMatchers(HttpMethod.HEAD, pattern);
                                break;
                            }
                            case TRACE: {
                                web.ignoring().mvcMatchers(HttpMethod.TRACE, pattern);
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                }
            });
            log.info("放行接口名单加载完毕");
        };
    }

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
        return new LeggAuthenticationFilter(authentication -> null);
    }

    @Bean
    UsernamePasswordBodyAuthenticationFilter usernamePasswordBodyAuthenticationFilter() {
        ProviderManager providerManager = new ProviderManager(usernameAndPasswordAuthenticationProvider);
        return new UsernamePasswordBodyAuthenticationFilter(LOGIN_URL, providerManager);
    }


}
