package com.ns2.app.auth.server.service;

import com.ns2.app.auth.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))
                .map(member -> (UserDetails) member);
    }
}
