package com.project.likelion14thbe.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 2. 인증 정보가 있다면 로그아웃 관련 추가 로직 처리 (예: 로그 남기기)
        if (authentication != null && authentication.getPrincipal() != null) {
            String username = authentication.getName();
            System.out.println("사용자 로그아웃 처리 중: " + username);
        }
    }
}
