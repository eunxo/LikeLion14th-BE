package com.project.likelion14thbe.global.apiPayload.exception.handler;

import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //애플리케이션에서 발생하는 커스텀 예외를 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse<Void>> handleCustomException(CustomException ex) {
        //예외가 발생하면 로그 기록
        log.warn("[ CustomException ]: {}", ex.getCode().getMessage());
        //커스텀 예외에 정의된 에러 코드와 메시지를 포함한 응답 제공
        return ResponseEntity.status(ex.getCode().getHttpStatus())
                .body(ex.getCode().getErrorResponse());
    }

    // @Valid DTO 필드 검증 실패 (request body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Map<String, String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(),
                        error.getDefaultMessage() != null ? error.getDefaultMessage() : "검증 실패")
        );
        log.warn("[ MethodArgumentNotValidException ]: {}", errors);
        BaseErrorCode errorCode = GeneralErrorCode.DTO_VALIDATION_FAILED;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errors));
    }

    // 단일 파라미터 검증 — Spring 6.1+ 내장 method validation (Boot 4 메인)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CustomResponse<Map<String, String>>> handleHandlerMethodValidation(
            HandlerMethodValidationException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults().forEach(result -> {
            String name = result.getMethodParameter().getParameterName();
            String message = result.getResolvableErrors().isEmpty()
                    ? "검증 실패"
                    : result.getResolvableErrors().get(0).getDefaultMessage();
            errors.put(name != null ? name : "param", message);
        });
        log.warn("[ HandlerMethodValidationException ]: {}", errors);
        BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errors));
    }

    // 단일 파라미터 검증 — 클래스 레벨 @Validated AOP 방식 (구식, Boot 4에서는 권장 X)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse<Map<String, String>>> handleConstraintViolation(
            ConstraintViolationException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            // 마지막 필드명만 추출 (예: createOrder.request.quantity -> quantity)
            String fieldName = propertyPath.contains(".")
                    ? propertyPath.substring(propertyPath.lastIndexOf(".") + 1)
                    : propertyPath;
            errors.put(fieldName, violation.getMessage());
        });
        log.warn("[ ConstraintViolationException ]: {}", errors);
        BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errors));
    }

    // 잘못된 경로 (404) — fallback Exception(500)으로 새지 않도록 분리
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomResponse<Void>> handleNoResourceFound(NoResourceFoundException ex) {
        log.warn("[ NoResourceFoundException ]: {}", ex.getMessage());
        BaseErrorCode errorCode = GeneralErrorCode.NOT_FOUND_404;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(CustomResponse.onFailure(errorCode.getCode(), "요청한 경로를 찾을 수 없습니다.", null));
    }

    // 잘못된 HTTP 메서드 (405)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomResponse<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("[ HttpRequestMethodNotSupportedException ]: {}", ex.getMessage());
        BaseErrorCode errorCode = GeneralErrorCode.METHOD_NOT_ALLOWED_405;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), null));
    }

    // 메서드 보안(@PreAuthorize) 거부 — 권한 없음(403)으로 변환. 필터 단계 거부는 JwtAccessDeniedHandler가 처리하고,
    // 컨트롤러 메서드 단계 거부는 여기로 올라오므로 500으로 새지 않도록 분리한다.
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        log.warn("[ AccessDeniedException ]: {}", ex.getMessage());
        BaseErrorCode errorCode = GeneralErrorCode.FORBIDDEN_403;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), null));
    }

    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomResponse<String>> handleAllException(Exception ex) {
        log.error("[WARNING] Internal Server Error : {} ", ex.getMessage(), ex);
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
