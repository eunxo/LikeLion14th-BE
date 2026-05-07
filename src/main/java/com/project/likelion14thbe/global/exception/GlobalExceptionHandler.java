package com.project.likelion14thbe.global.exception;

import com.project.likelion14thbe.global.response.CustomResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatusCode statusCode = ex.getStatusCode();
        String message = ex.getReason() != null ? ex.getReason() : "요청 처리에 실패했습니다.";
        return ResponseEntity.status(statusCode)
                .body(CustomResponse.fail(toCode(statusCode), message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("요청 값이 올바르지 않습니다.");
        return ResponseEntity.badRequest()
                .body(CustomResponse.fail(toCode(HttpStatus.BAD_REQUEST), message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest()
                .body(CustomResponse.fail(toCode(HttpStatus.BAD_REQUEST), "요청 값이 올바르지 않습니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Void>> handleException(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(CustomResponse.fail(toCode(HttpStatus.INTERNAL_SERVER_ERROR), "서버 내부 오류가 발생했습니다."));
    }

    private String toCode(HttpStatusCode statusCode) {
        HttpStatus status = HttpStatus.resolve(statusCode.value());
        if (status == null) {
            return String.valueOf(statusCode.value());
        }
        return status.value() + " " + status.getReasonPhrase();
    }
}
