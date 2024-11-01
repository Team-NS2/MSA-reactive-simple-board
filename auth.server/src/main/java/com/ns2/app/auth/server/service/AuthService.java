package com.ns2.app.auth.server.service;

import com.ns2.app.auth.server.domain.dto.LoginRequest;
import com.ns2.app.auth.server.domain.dto.LoginResponse;
import com.ns2.app.auth.server.domain.dto.SignUpRequest;
import com.ns2.app.auth.server.domain.entity.Member;
import com.ns2.app.auth.server.jwt.JwtProvider;
import com.ns2.app.auth.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final TokenService tokenService;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<Void> signUp(SignUpRequest signUpRequest) {
        return memberRepository.findByUsername(signUpRequest.username())
                .flatMap(existingMember -> Mono.error(new IllegalStateException()))
                .switchIfEmpty(Mono.defer(() -> {
                    String encodedPassword = passwordEncoder.encode(signUpRequest.password());
                    Member newMember = Member.createMember(signUpRequest.username(), encodedPassword, signUpRequest.name());
                    return memberRepository.save(newMember).then();
                }))
                .then();
    }

    public Mono<LoginResponse> login(LoginRequest loginRequest) {
        return memberRepository.findByUsername(loginRequest.username())
                .flatMap(member -> {
                    if(passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
                        return jwtProvider.generateToken(loginRequest.username())
                                .flatMap(accessToken -> jwtProvider.generateRefreshToken(member.getUsername())
                                        .flatMap(refreshToken -> tokenService.saveRefreshToken(refreshToken, loginRequest.username()))
                                .thenReturn(new LoginResponse(accessToken)));
                    } else {
                        return Mono.error(new IllegalArgumentException());
                    }
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException()));

    }
}
