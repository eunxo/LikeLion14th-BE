package com.project.likelion14thbe.global.apiPayload.exception.handler;

import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 애플리케이션에서 발생하는 커스텀 예외를 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse<Void>> handleCustomException(CustomException ex) {
        log.warn("[ CustomException ]: {}", ex.getCode().getMessage());
        CustomResponse<Void> errorResponse = CustomResponse.onFailure(
                ex.getCode().getCode(),
                ex.getCode().getMessage(),
                null
        );

        return ResponseEntity
                .status(ex.getCode().getHttpStatus())
                .body(errorResponse);
    }

    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomResponse<String>> handleAllException(Exception ex) {
        log.error("[WARNING] Internal Server Error : {} ", ex.getMessage());
        BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR_500;
        CustomResponse<String> errorResponse = CustomResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage(),
                null
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }
}