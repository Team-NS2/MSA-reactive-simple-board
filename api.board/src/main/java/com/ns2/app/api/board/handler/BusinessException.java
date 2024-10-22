package com.ns2.app.api.board.handler;

import com.ns2.app.api.board.domain.enums.BusinessExceptionStatus;

public class BusinessException extends RuntimeException {

    public BusinessExceptionStatus status;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(BusinessExceptionStatus status) {
        this.status = status;
    }
}
