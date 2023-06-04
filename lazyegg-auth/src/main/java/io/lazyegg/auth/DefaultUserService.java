package io.lazyegg.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class DefaultUserService implements UserService {

    private final static HashMap<String, List<GrantedAuthority>> USER_MAP = new HashMap<>();
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    static {
        USER_MAP.put("admin", new ArrayList());
        USER_MAP.put("user", new ArrayList());
        USER_MAP.put("test", new ArrayList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("DefaultUserService");
        String password = bCryptPasswordEncoder.encode("admin");
        List<GrantedAuthority> list = USER_MAP.get(username);

        return new User(username, password, list);
    }

    @Override
    public String getAuthSourceName() {
        return "本地认证源";
    }
}
