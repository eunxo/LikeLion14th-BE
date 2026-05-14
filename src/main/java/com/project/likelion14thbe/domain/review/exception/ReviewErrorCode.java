package com.project.likelion14thbe.domain.review.exception;

import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    REVIEW_ORDER_ITEM_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "REVIEW404_1",
            "주문 상세를 찾을 수 없습니다."
    ),

    REVIEW_ORDER_ITEM_PRODUCT_MISMATCH(
            HttpStatus.BAD_REQUEST,
            "REVIEW400_1",
            "주문 상세가 해당 상품과 일치하지 않습니다."
    ),

    REVIEW_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "REVIEW404_2",
            "리뷰를 찾을 수 없습니다."
    );

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
