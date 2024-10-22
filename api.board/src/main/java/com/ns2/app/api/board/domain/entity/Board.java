package com.ns2.app.api.board.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Table("board")
public class Board {
    @Id
    @Column("board_id")
    private Long id;
    private String title;
    private String content;
    private String author;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Board(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void modifyTitle(String title) {
        this.title = title;
        updatedAt = LocalDateTime.now();
    }

    public void modifyContent(String content) {
        this.content = content;
        updatedAt = LocalDateTime.now();
    }
}
