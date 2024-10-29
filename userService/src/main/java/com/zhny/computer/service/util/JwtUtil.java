package com.zhny.computer.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "ADMIN_COMPUTER_USER_SERVICE"; // 应用程序的密钥
    private static final long EXPIRATION_TIME = 3600000; // 1小时

    // 生成JWT的方法
    public static String generateToken(String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建JWT
        JwtBuilder builder = Jwts.builder()
                .setSubject(username) // 用户名
                .setIssuedAt(now) // 签发时间
                .setExpiration(new Date(nowMillis + EXPIRATION_TIME)) // 过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY); // 签名算法和密钥

        return builder.compact(); // 返回JWT字符串
    }

    // 验证JWT的方法
    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
