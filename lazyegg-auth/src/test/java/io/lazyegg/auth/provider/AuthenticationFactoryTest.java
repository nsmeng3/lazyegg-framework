package io.lazyegg.auth.provider;

import io.lazyegg.auth.App;
import io.lazyegg.auth.AuthenticationFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.Map;

@SpringBootTest(classes = App.class)
class AuthenticationFactoryTest {

    @Test
    void getAuthenticationProviderNames() {
        Map<String, AuthenticationProvider> authenticationProviderNames = AuthenticationFactory.getAuthenticationProvider();
        authenticationProviderNames.forEach((k, v) -> {
            System.out.println(k);

        });
    }
}
