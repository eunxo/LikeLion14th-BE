package com.project.likelion14thbe.global.apiPayload.exception;

public class AuthException extends CustomException {

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}
