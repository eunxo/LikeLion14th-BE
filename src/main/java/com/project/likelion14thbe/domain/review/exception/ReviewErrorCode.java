package com.project.likelion14thbe.domain.review.exception;

import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_1", "리뷰를 찾을 수 없습니다."),
    REVIEW_UNAUTHORIZED(HttpStatus.FORBIDDEN, "REVIEW403_1", "리뷰 정보를 수정/삭제할 권한이 없습니다."),
    REVIEW_DUPLICATE(HttpStatus.CONFLICT, "REVIEW409_1", "이미 리뷰가 작성된 상품입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
