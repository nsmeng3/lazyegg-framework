package io.lazyegg.auth.util;

import io.jsonwebtoken.*;
import io.lazyegg.auth.JwtTokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.Date;

/**
 * JwtTokenProvider
 */
@Slf4j
@Component
public class JwtTokenUtil {

    /**
     * 密钥
     */
    private static final String SECRET_KEY = "mySecretKey";
    /**
     * 过期时间
     */
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L;

    /**
     * 生成token
     *
     * @param authentication
     * @return
     */
    public static String generateToken(Authentication authentication) {
        String user = String.valueOf(authentication.getPrincipal());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(user)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 验证token是否有效
     *
     * @param token
     * @return
     */
    public static boolean validateToken(String token) {
        JwtTokenCache jwtTokenCache = SpringUtil.getBean(JwtTokenCache.class);
        boolean contains = jwtTokenCache.contains(token);
        Assert.isTrue(contains, "token expire");
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    /**
     * 从token中获取用户信息
     *
     * @param token
     * @return
     */
    public static String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 从请求头中获取token
     *
     * @param request
     * @return
     */
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 从token中获取过期时间
     *
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }


}
