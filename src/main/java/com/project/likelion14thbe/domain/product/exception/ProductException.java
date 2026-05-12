package com.project.likelion14thbe.domain.product.exception;

import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;

public class ProductException extends CustomException {
    public ProductException(ProductErrorCode errorCode) {
        super(errorCode);
    }
}
