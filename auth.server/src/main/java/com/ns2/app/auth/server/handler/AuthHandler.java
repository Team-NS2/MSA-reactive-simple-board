package com.ns2.app.auth.server.handler;


import com.ns2.app.auth.server.domain.dto.LoginRequest;
import com.ns2.app.auth.server.domain.dto.SignUpRequest;
import com.ns2.app.auth.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthHandler {

    private final AuthService authService;

    public Mono<ServerResponse> signup(ServerRequest request) {
        return request.bodyToMono(SignUpRequest.class)
                .flatMap(authService::signUp)
                .then(ServerResponse.ok().build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));

    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(authService::login)
                .flatMap(loginResponse -> ServerResponse.ok().bodyValue(loginResponse))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
