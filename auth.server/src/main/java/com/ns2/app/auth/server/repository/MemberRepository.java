package com.ns2.app.auth.server.repository;

import com.ns2.app.auth.server.domain.entity.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {

    Mono<Member> findByUsername(String username);
}
