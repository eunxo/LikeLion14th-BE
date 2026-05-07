package com.project.likelion14thbe.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomResponse<T>(
        boolean isSuccess,
        String code,
        String message,
        T result
) {
    public static <T> CustomResponse<T> fail(String code, String message) {
        return new CustomResponse<>(false, code, message, null);
    }

    public static <T> CustomResponse<T> success(String code, String message, T result) {
        return new CustomResponse<>(true, code, message, result);
    }
}
