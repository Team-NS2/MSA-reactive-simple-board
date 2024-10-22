package com.ns2.app.api.board.domain.dto;

public record BoardResponseDto(Long id, String title
        , String content, String author
        , String lastModifiedDate
) {
}
