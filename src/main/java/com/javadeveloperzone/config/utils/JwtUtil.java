package com.javadeveloperzone.config.utils;

import com.javadeveloperzone.entity.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private static final String JWT_SECRET = "c88d74ba-1554-48a4-b549-b926f5d77c9e";

    // 토큰 유효시간
    private static final long JWT_EXPIRATION_MS = 604800000;

    // jwt 토큰 생성
    public static String createToken(Authentication authentication) {
        String secretKeyEncodeBase64 = Encoders.BASE64.encode(JWT_SECRET.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(authentication.getPrincipal().toString()) // 사용자
                .signWith(key)
                .claim("userEmail", authentication.getName())
                .claim("role", authentication.getAuthorities().stream().findFirst().get())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .compact();

    }

    // Jwt 토큰에서 아이디 추출
    public static String getUserIdFromJWT(String token) {
        String secretKeyEncodeBase64 = Encoders.BASE64.encode(JWT_SECRET.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.info("id:"+claims.getId());
        log.info("issuer:"+claims.getIssuer());
        log.info("issue:"+claims.getIssuedAt().toString());
        log.info("subject:"+claims.getSubject());
        log.info("Audience:"+claims.getAudience());
        log.info("expire:"+claims.getExpiration().toString());
        log.info("userEmail:"+claims.get("userEmail"));
        log.info("role:"+claims.get("role"));

        return claims.get("userEmail").toString();
    }

    // Jwt 토큰 유효성 검사
    public static boolean validateToken(String token) {
        String secretKeyEncodeBase64 = Encoders.BASE64.encode(JWT_SECRET.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        }
        return false;
    }
}
