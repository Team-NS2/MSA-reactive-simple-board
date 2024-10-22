package com.ns2.app.api.board.domain.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessExceptionStatus {
    /* Server Exception */
    NOT_FOUND(HttpStatus.NOT_FOUND, 1000, "요청을 찾을 수 없습니다.")
    , INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1001, "서버에서 에러가 발생하였습니다.")

    /* Board Exception */
    , FAIL_TO_REG_BOARD(HttpStatus.BAD_REQUEST, 2000, "게시글 작성에 실패하였습니다.")
    , FAIL_TO_FIND_BOARD(HttpStatus.BAD_REQUEST, 2001, "게시글을 찾는데 실패하였습니다.")
    , FAIL_TO_FIND_BOARDS(HttpStatus.BAD_REQUEST, 2002, "게시판을 찾는데 실패하였습니다.")
    , FAIL_TO_MODIFY_BOARD(HttpStatus.BAD_REQUEST, 2003, "게시글을 수정하는데 실패하였습니다.")
    , FAIL_TO_DELETE_BOARD(HttpStatus.BAD_REQUEST, 2004, "게시글을 삭제하는데 실패하였습니다.")

    ;

    private final HttpStatus status;
    private final Integer code;
    private final String message;

    BusinessExceptionStatus(HttpStatus status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
