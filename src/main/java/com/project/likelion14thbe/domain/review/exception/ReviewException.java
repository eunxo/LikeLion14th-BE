package com.project.likelion14thbe.domain.review.exception;

import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import lombok.Getter;

@Getter
public class ReviewException extends CustomException {

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }
}
