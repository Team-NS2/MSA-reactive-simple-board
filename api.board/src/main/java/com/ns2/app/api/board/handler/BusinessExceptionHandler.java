package com.ns2.app.api.board.handler;

import com.ns2.app.api.board.domain.dto.ServerResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Mono<ServerResponse> handleBusinessException(final BusinessException e) {
        return ServerResponse
                .status(e.status.getStatus().value())
                .bodyValue(new ServerResponseDto<>(e.status.getCode(), e.status.getMessage(), null));
    }
}
