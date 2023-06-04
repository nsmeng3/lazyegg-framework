package io.lazyegg.auth;

/**
 * JwtTokenCache
 * jwt token缓存
 *
 * @author DifferentW nsmeng3@163.com
 */
public interface JwtTokenCache {

    void add(String token, long expiration);

    boolean contains(String token);

    void remove(String token);
}
