package com.zichen.service.impl;


import com.zichen.pojo.JwtUser;
import com.zichen.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final SecretKey key;
    private final long expireMillis;

    /** 单机版拉黑 */
    private final Set<String> revoked = ConcurrentHashMap.newKeySet();

    public JwtTokenServiceImpl(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expire-minutes}") long expireMinutes
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireMillis = expireMinutes * 60 * 1000;
    }

    @Override
    public String generate(JwtUser user) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("nickname", user.getNickname())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expireMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public JwtUser parse(HttpServletRequest request) {
        String token = extract(request);
        if (token == null) {
            throw new RuntimeException("Missing token");
        }
        if (revoked.contains(token)) {
            throw new RuntimeException("Token revoked");
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        JwtUser user = new JwtUser();
        user.setId(Long.parseLong(claims.getSubject()));
        user.setUsername((String) claims.get("username"));
        user.setNickname((String) claims.get("nickname"));
        return user;
    }

    @Override
    public void revoke(HttpServletRequest request) {
        String token = extract(request);
        if (token != null) {
            revoked.add(token);
        }
    }

    private String extract(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return null;
        }
        return auth.substring(7);
    }
}
