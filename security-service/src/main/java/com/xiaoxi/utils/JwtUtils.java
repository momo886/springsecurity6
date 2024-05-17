package com.xiaoxi.utils;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

/**
 * @Author： momo
 *
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private static final String SECRET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final long defaultExpire = 1000 * 60 * 60 * 24 * 7L;//7天
    private static final SecretKey key=Jwts.SIG.HS256.key().random(new SecureRandom(SECRET.getBytes(StandardCharsets.UTF_8))).build();


    /**
     * 生产token
     *
     * @param claims
     * @return
     */
    public static String createToken(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .claims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + defaultExpire)) // 设置过期时间（1小时后）
                .signWith(key) // 使用 HMAC 和密钥进行签名
                .compact(); // 生成紧凑的 JWT 字符串
        return jwt;

    }

    /**
     * @Description: 解析令牌
     */
    public static Claims claims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                //现在不需要使用 claims.getExpiration().before(new Date());
                // 判断JWT是否过期了 如果过期会抛出ExpiredJwtException异常
                throw new RuntimeException("token已过期");
            }
            if (e instanceof JwtException) {
                throw new RuntimeException("token已失效");
            }
            throw new RuntimeException("token解析失败");
        }
    }

    public static void main(String[] args) {
        Map<String, Object> claims = Map.of("id","1","name", "张三");
        String token = createToken(claims);
        System.out.println(token);
        Claims claims1 = claims(token);
        System.out.println(claims1);


    }
}
