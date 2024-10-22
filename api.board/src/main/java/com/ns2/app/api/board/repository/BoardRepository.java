package com.ns2.app.api.board.repository;

import com.ns2.app.api.board.domain.entity.Board;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BoardRepository extends ReactiveCrudRepository<Board, Long> {
}
