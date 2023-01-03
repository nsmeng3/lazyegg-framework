package io.lazyegg.auth.core.provider;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UsernameAndPasswordAuthenticationProviderTest {


    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("admin123");
        System.out.println(encode);
    }

}
