//package io.lazyegg.auth;
//
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//
//@Component
//public class RedisJwtTokenCache implements JwtTokenCache {
//    private final RedisTemplate<String, Long> redisTemplate;
//    private final String keyPrefix;
//
//    public RedisJwtTokenCache(RedisTemplate<String, Long> redisTemplate, String keyPrefix) {
//        this.redisTemplate = redisTemplate;
//        this.keyPrefix = keyPrefix;
//    }
//
//    @Override
//    public void add(String token, long expiration) {
//        String key = getKey(token);
//        redisTemplate.opsForValue().set(key, expiration, Duration.ofSeconds(expiration));
//    }
//
//    @Override
//    public boolean contains(String token) {
//        String key = getKey(token);
//        return redisTemplate.hasKey(key);
//    }
//
//    @Override
//    public void remove(String token) {
//        String key = getKey(token);
//        redisTemplate.delete(key);
//    }
//
//    private String getKey(String token) {
//        return keyPrefix + token;
//    }
//}
