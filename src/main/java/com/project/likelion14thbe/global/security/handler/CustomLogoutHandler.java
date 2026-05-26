package com.project.likelion14thbe.global.security.handler;

import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.jwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("[CustomLogoutHandler] 로그아웃 핸들러 동작 시작");

        // 1. 요청 헤더에서 Access Token 추출
        String accessToken = jwtUtil.resolveAccessToken(request);

        if (accessToken != null) {
            try {
                // 2. 토큰 유효성 검증 및 이메일 추출
                jwtUtil.validateToken(accessToken);
                String email = jwtUtil.getEmail(accessToken);

                // 3. DB에 저장된 Refresh Token 삭제
                tokenRepository.deleteByEmail(email);
                log.info("[CustomLogoutHandler] 이메일 {}의 Refresh Token을 삭제하였습니다.", email);
            } catch (Exception e) {
                log.error("[CustomLogoutHandler] 로그아웃 처리 중 예외 발생: {}", e.getMessage());
                // 유효하지 않거나 이미 만료된 토큰이어도 클라이언트 상에서의 로그아웃 요청이므로 로그 처리만 하고 넘어갑니다.
            }
        }
    }
}
