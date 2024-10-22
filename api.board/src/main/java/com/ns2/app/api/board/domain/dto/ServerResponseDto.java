package com.ns2.app.api.board.domain.dto;

public record ServerResponseDto<T>(Integer code, String message, T data) {
}
