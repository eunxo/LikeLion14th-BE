package com.project.likelion14thbe.domain.review.exception;

import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_1", "리뷰가 존재하지 않습니다."),
    REVIEW_NOT_BELONG_TO_PRODUCT(HttpStatus.NOT_FOUND, "REVIEW404_2", "해당 상품의 리뷰가 아닙니다."),
    REVIEW_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_3", "리뷰 대상 상품이 존재하지 않습니다."),
    REVIEW_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404_4", "리뷰 작성 회원을 찾을 수 없습니다."),
    REVIEW_FORBIDDEN(HttpStatus.FORBIDDEN, "REVIEW403_1", "본인 리뷰만 수정/삭제할 수 있습니다."),
    REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "REVIEW409_1", "이미 해당 상품에 리뷰를 작성했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
