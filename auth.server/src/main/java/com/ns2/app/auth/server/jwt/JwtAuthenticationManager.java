package com.ns2.app.auth.server.jwt;

import com.ns2.app.auth.server.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        return customUserDetailsService.findByUsername(username)
                .flatMap(member -> {
                    if (validatePassword(password, member.getPassword())) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                username, password, member.getAuthorities());
                        return Mono.just(token);
                    }

                    return Mono.error(new BadCredentialsException("Invalid credentials"));
                });
    }

    private boolean validatePassword(String password, String encodedPassword) {
        if(!this.passwordEncoder.matches(password, encodedPassword)) {
            throw new BadCredentialsException("Invalid Password");
        }

        return true;
    }
}
