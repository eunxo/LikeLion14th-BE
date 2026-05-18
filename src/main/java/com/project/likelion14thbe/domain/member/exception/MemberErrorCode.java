package com.project.likelion14thbe.domain.member.exception;

import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "회원을 찾을 수 없습니다."),
    MEMBER_FORBIDDEN(HttpStatus.FORBIDDEN, "MEMBER403_1", "회원 정보를 수정/삭제할 권한이 없습니다."),
    MEMBER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "MEMBER401_1", "회원 인증이 필요합니다."),
    MEMBER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "MEMBER409_1", "이미 사용 중인 이메일입니다."),
    MEMBER_WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER401_1", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
