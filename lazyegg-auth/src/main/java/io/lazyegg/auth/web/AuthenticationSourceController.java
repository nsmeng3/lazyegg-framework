package io.lazyegg.auth.web;

import io.lazyegg.auth.UserService;
import io.lazyegg.auth.util.SpringUtil;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationSourceController {

    /**
     * 获取所有认证源
     *
     * @return
     */
    @GetMapping("/sources")
    public ResponseEntity<Object> getAuthenticationProviderMap() {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Map<String, UserService> beansOfType = applicationContext.getBeansOfType(UserService.class);
        ArrayList<Map<String, String>> objects = new ArrayList<>();
        beansOfType.forEach((k, v) -> {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("name", v.getAuthSourceName());
            temp.put("code", k);
            objects.add(temp);
        });
        return ResponseEntity.ok(objects);
    }

    /**
     * 设置认证源
     *
     * @param source
     * @return
     */
    @PutMapping("/sources")
    public ResponseEntity<Object> setSource(@RequestParam String source) {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Map<String, UserService> beansOfType = applicationContext.getBeansOfType(UserService.class);
        if (!beansOfType.containsKey(source)) {
            return ResponseEntity.badRequest().body(source + "认证源不存在");
        }
        System.setProperty("lazyegg.auth.source", source);
        return ResponseEntity.ok().build();
    }
}
