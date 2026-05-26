package com.project.likelion14thbe.global.security.handler;

import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.jwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        log.info("[ CustomLogoutHandler ] 로그아웃 처리를 시작합니다.");

        // 1. 요청 헤더에서 Access Token 추출
        String accessToken = jwtUtil.resolveAccessToken(request);

        if (accessToken == null) {
            log.warn("[ CustomLogoutHandler ] Access Token 이 존재하지 않습니다.");
            return;
        }

        try {
            // 2. Access Token 유효성 검증
            jwtUtil.validateToken(accessToken);

            // 3. Access Token 에서 사용자 email 추출
            String email = jwtUtil.getEmail(accessToken);
            log.info("[ CustomLogoutHandler ] 로그아웃 대상 email = {}", email);

            // 4. DB 에 저장된 Refresh Token 삭제
            tokenRepository.findByEmail(email).ifPresent(token -> {
                tokenRepository.deleteByEmail(email);
                log.info("[ CustomLogoutHandler ] DB 에서 Refresh Token 삭제 완료");
            });

        } catch (Exception e) {
            log.warn("[ CustomLogoutHandler ] 로그아웃 중 토큰 처리 오류: {}", e.getMessage());
        } finally {
            // 5. SecurityContext 비우기
            SecurityContextHolder.clearContext();
            log.info("[ CustomLogoutHandler ] SecurityContext 클리어 완료");
        }
    }
}
