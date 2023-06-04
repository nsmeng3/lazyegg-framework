package io.lazyegg.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    String getAuthSourceName();
}
