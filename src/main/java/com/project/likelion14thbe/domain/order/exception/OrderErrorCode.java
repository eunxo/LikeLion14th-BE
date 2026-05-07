package com.project.likelion14thbe.domain.order.exception;

import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_1", "주문을 찾을 수 없습니다."),
    ORDER_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_2", "주문 회원을 찾을 수 없습니다."),
    ORDER_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_3", "주문 상품을 찾을 수 없습니다."),
    ORDER_FORBIDDEN(HttpStatus.FORBIDDEN, "ORDER403_1", "본인 주문만 취소할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
