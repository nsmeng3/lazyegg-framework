package io.lazyegg.auth.web;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.fastjson.JSONObject;
import io.lazyegg.auth.JwtTokenCache;
import io.lazyegg.auth.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenCache jwtTokenCache;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JSONObject loginRequest) {
            String username = loginRequest.getString("username");
            String password = loginRequest.getString("password");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = JwtTokenUtil.generateToken(authentication);
            jwtTokenCache.add(token, 60*1000L);
            return ResponseEntity.ok(SingleResponse.of(token));
    }

//    private AuthenticationManager authenticationManager() {
//        Map<String, AuthenticationProvider> authenticationProviderNames = AuthenticationFactory.getAuthenticationProvider();
//        return new ProviderManager(authenticationProviderNames.values().toArray(new AuthenticationProvider[0]));
//    }
}
