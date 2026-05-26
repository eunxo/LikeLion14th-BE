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

        // 1. 요청 헤더에서 AccessToken 추출
        String accessToken = jwtUtil.resolveAccessToken(request);

        if (accessToken == null) {
            log.warn("[ CustomLogoutHandler ] 로그아웃 요청에 토큰이 없습니다.");
            return;
        }

        // 2. AccessToken에서 이메일 추출
        String email = jwtUtil.getEmail(accessToken);
        log.info("[ CustomLogoutHandler ] 로그아웃 요청 - email: {}", email);

        // 3. DB에서 해당 이메일의 RefreshToken 삭제
        tokenRepository.findByEmail(email).ifPresentOrElse(
                token -> {
                    tokenRepository.delete(token); // 토큰 삭제
                    log.info("[ CustomLogoutHandler ] RefreshToken 삭제 완료 - email: {}", email);
                },
                () -> log.warn("[ CustomLogoutHandler ] 해당 이메일의 RefreshToken이 존재하지 않습니다. - email: {}", email)
        );
    }
}
