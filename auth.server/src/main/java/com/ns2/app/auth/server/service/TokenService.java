package com.ns2.app.auth.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public Mono<Void> saveRefreshToken(String refreshToken, String username) {
        return redisTemplate.opsForValue()
                .set(refreshToken, username)
                .then();
    }

    public Mono<String> getUsernameByRefreshToken(String refreshToken) {
        return redisTemplate.opsForValue()
                .get(refreshToken);
    }

    public Mono<Void> deleteRefreshToken(String refreshToken) {
        return redisTemplate.opsForValue()
                .delete(refreshToken)
                .then();
    }
}
