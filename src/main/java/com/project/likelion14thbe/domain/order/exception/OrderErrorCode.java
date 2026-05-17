package com.project.likelion14thbe.domain.order.exception;

import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404_1", "주문 내역을 찾을 수 없습니다."),
    ORDER_CANNOT_CANCEL(HttpStatus.BAD_REQUEST, "ORDER400_1", "이미 배송이 시작되어 취소할 수 없습니다."),
    ORDER_FORBIDDEN(HttpStatus.FORBIDDEN, "ORDER403_1", "본인의 주문만 취소할 수 있습니다."),
    ORDER_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "ORDER400_2", "상품 재고가 부족하여 주문할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}