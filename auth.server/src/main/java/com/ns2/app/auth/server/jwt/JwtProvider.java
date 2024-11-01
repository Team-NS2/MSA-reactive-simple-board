package com.ns2.app.auth.server.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private SecretKey secretKey;
    private long accessTokenExpirationTime;
    private long refreshTokenExpirationTime;

    public JwtProvider(@Value("${jwt.secret-key}") String key,
                       @Value("${jwt.access-token-expiration-time}") long accessTokenExpirationTime,
                       @Value("${jwt.refresh-token-expiration-time}") long refreshTokenExpirationTime) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    public Mono<String> generateToken(String username) {
        return Mono.just(Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact());
    }

    public Mono<String> generateRefreshToken(String username) {
        return Mono.just(Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact());
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (SignatureException e) {
            return false;
        } catch (JwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
