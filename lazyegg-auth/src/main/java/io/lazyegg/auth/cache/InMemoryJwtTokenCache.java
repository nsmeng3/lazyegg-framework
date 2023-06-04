package io.lazyegg.auth.cache;

import io.lazyegg.auth.JwtTokenCache;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class InMemoryJwtTokenCache implements JwtTokenCache {
    private final Map<String, Long> tokenMap = new ConcurrentHashMap<>();

    public InMemoryJwtTokenCache() {
        // 每隔一段时间清理过期的 Token 缓存
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::cleanExpiredTokens, 1, 1, TimeUnit.HOURS);
    }

    @Override
    public void add(String token, long expiration) {
        tokenMap.put(token, expiration);
    }

    @Override
    public boolean contains(String token) {
        return tokenMap.containsKey(token);
    }

    @Override
    public void remove(String token) {
        tokenMap.remove(token);
    }

    private void cleanExpiredTokens() {
        long now = System.currentTimeMillis();
        tokenMap.entrySet().removeIf(entry -> entry.getValue() < now);
    }
}
