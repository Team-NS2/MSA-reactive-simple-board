package com.ns2.app.api.board.handler;

import com.ns2.app.api.board.domain.dto.BoardResponseDto;
import com.ns2.app.api.board.domain.dto.RegBoardRequestDto;
import com.ns2.app.api.board.service.BoardModelConverter;
import com.ns2.app.api.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class BoardHandler {
    private final BoardService boardService;
    private final BoardModelConverter boardConverter;

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(boardService.findById(Long.parseLong(request.pathVariable("id")))
                                .map(boardConverter::toBoardResponseDto), BoardResponseDto.class)
                .switchIfEmpty(Mono.error(new ChangeSetPersister.NotFoundException()));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(RegBoardRequestDto.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid request body")))
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(boardService.save(dto), BoardResponseDto.class)
                );
    }
}
