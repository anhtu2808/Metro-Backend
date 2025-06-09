package com.metro.common_lib.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    ENTITY_NOT_FOUND(1001, "Entity not found with given ID"),
    ENTITY_NOT_FOUND_FOR_UPDATE(1002, "Entity not found for update with given ID"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;
}
