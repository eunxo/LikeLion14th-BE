package com.project.likelion14thbe.domain.member.exception;

import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import lombok.Getter;

@Getter
public class MemberException extends CustomException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
