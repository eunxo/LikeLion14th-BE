package com.project.likelion14thbe.domain.auth.exception;

import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import lombok.Getter;

@Getter
public class AuthException extends CustomException {

    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AuthException(String message) {
        super(message);
        this.errorCode = null;
    }
}
