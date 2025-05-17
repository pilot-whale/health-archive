package com.sudobang.healtharchive.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成JWT
     * 使用HS256算法，密钥使用固定秘钥
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     * @return JWT字符串
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 处理密钥长度（确保至少32位）
        secretKey = secretKey + "0000000000".substring(0, Math.max(0,  32 - secretKey.length()));

        // 创建安全的密钥
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 计算过期时间
        long expMillis = System.currentTimeMillis()  + ttlMillis;
        Date exp = new Date(expMillis);

        // 构建JWT
        return Jwts.builder()
                .claims(claims) // 使用新的claims()方法替代setClaims()
                .expiration(exp) // 设置过期时间
                .signWith(key) // 使用新的signWith()方法，不需要指定算法
                .compact();
    }

    /**
     * 解析JWT
     *
     * @param secretKey jwt秘钥
     * @param token     要解析的token
     * @return Claims对象
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 处理密钥长度（确保至少32位）
        secretKey = secretKey + "0000000000".substring(0, Math.max(0,  32 - secretKey.length()));

        // 创建安全的密钥
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 解析JWT
        return Jwts.parser()
                .verifyWith(key) // 使用verifyWith()替代setSigningKey()
                .build()
                .parseSignedClaims(token) // 使用parseSignedClaims()替代parseClaimsJws()
                .getPayload(); // 使用getPayload()替代getBody()
    }
}