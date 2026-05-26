package com.project.likelion14thbe.global.security.logout;

import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.jwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException; // ⭐️ 임포트 확인!
import org.springframework.security.core.Authentication;
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
    public void logout(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication
    ) {
        log.info("[ CustomLogoutHandler ] 로그아웃 처리를 시작합니다.");

        String accessToken = jwtUtil.resolveAccessToken(request);

        if (accessToken == null || accessToken.isBlank()) {
            log.warn("[ CustomLogoutHandler ] 헤더에 토큰이 존재하지 않아 로그아웃을 차단합니다.");
            throw new InsufficientAuthenticationException("로그인하지 않은 사용자입니다.");
        }

        try {
            String email = jwtUtil.getEmail(accessToken);
            log.info("[ CustomLogoutHandler ] 로그아웃 대상 유저: {}", email);

            tokenRepository.deleteByEmail(email);
            log.info("[ CustomLogoutHandler ] DB에서 Refresh Token 삭제 완료");

        } catch (Exception e) {
            log.error("[ CustomLogoutHandler ] 토큰 검증 혹은 DB 삭제 중 오류 발생: {}", e.getMessage());
            throw new InsufficientAuthenticationException("유효하지 않은 토큰입니다.", e);
        }
    }
}