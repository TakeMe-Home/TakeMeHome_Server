package com.toy.takemehome.utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultRes<T> {

    public static final DefaultRes FAIL_DEFAULT_RES
            = DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final int statusCode;
    private final String message;
    private final T data;


    public DefaultRes(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <T> DefaultRes<T> res(int statusCode, String message) {
        return res(statusCode, message, null);
    }

    public static <T> DefaultRes<T> res(int statusCode, String message, T t) {
        return DefaultRes.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
