package com.ns2.app.api.board.service;

import com.ns2.app.api.board.domain.dto.RegBoardRequestDto;
import com.ns2.app.api.board.domain.entity.Board;
import com.ns2.app.api.board.domain.enums.BusinessExceptionStatus;
import com.ns2.app.api.board.handler.BusinessException;
import com.ns2.app.api.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardModelConverter boardModelConverter;

    public Mono<Board> save(RegBoardRequestDto boardRequestDto) {
        return boardRepository.save(boardModelConverter.toBoardEntity(boardRequestDto))
                .doOnError(throwable -> {
                    throw new BusinessException(BusinessExceptionStatus.FAIL_TO_REG_BOARD);
                });
    }

    public Mono<Board> findById(Long id) {
        return boardRepository.findById(id);
    }
}
