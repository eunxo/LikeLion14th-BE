package com.project.likelion14thbe.domain.auth.service.command;

import com.project.likelion14thbe.domain.auth.dto.response.JwtDTO;
import com.project.likelion14thbe.domain.auth.enums.Provider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public interface OAuthCommandService {

    // 인가 페이지로 리다이렉트. state를 세션에 저장
    void redirect(Provider provider, HttpServletResponse response, HttpSession session) throws IOException;

    // 콜백 처리: error(거부/실패) 처리, state 검증, 토큰·사용자정보 조회, 로그인 또는 회원가입 후 JwtDTO 반환
    JwtDTO handleCallback(Provider provider, String code, String state, String error, HttpSession session);
}
