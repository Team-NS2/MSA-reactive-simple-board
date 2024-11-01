package com.ns2.app.auth.server.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;


@RequiredArgsConstructor
@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(extractToken(exchange))
                .filter(jwtProvider::validateToken)
                .map(token -> createAuthentication(token));
    }

    private Authentication createAuthentication(String token) {
        String username = jwtProvider.getSubject(token);
        return new UsernamePasswordAuthenticationToken(username, token, Collections.emptyList());
    }

    private String extractToken(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if(!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
