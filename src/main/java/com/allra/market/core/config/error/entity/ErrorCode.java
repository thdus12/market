package com.allra.market.core.config.error.entity;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR),

    ACCESS_DENIED(HttpStatus.FORBIDDEN),
    JSON_PARSE_ERROR(HttpStatus.UNAUTHORIZED),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED),

    BAD_VALID(HttpStatus.BAD_REQUEST),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    DUPLICATE_KEY(HttpStatus.BAD_REQUEST),
    EXIST_PARENT(HttpStatus.BAD_REQUEST),
    INVALID_DATA(HttpStatus.BAD_REQUEST),
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST),
    SOLD_OUT(HttpStatus.BAD_REQUEST),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST),
    PAYMENT_FAILED(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;

    ErrorCode(final HttpStatus status) {
        this.status = status;
    }
}
