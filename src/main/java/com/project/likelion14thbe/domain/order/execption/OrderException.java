package com.project.likelion14thbe.domain.order.execption;

import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import lombok.Getter;

@Getter
public class OrderException extends CustomException {
    public OrderException(OrderErrorCode errorCode) {
        super(errorCode);
    }
}
