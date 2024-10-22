package com.ns2.app.api.board.service;

import com.ns2.app.api.board.domain.dto.BoardResponseDto;
import com.ns2.app.api.board.domain.dto.RegBoardRequestDto;
import com.ns2.app.api.board.domain.entity.Board;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class BoardModelConverter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BoardResponseDto toBoardResponseDto(Board board) {
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContent()
                , board.getAuthor(), board.getUpdatedAt().format(formatter)
        );
    }

    public Board toBoardEntity(RegBoardRequestDto dto) {
        return Board.builder()
                .title(dto.title())
                .content(dto.content())
                .author(dto.author())
                .build();
    }
}
