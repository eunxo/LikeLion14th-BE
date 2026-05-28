package com.project.likelion14thbe.global.security.handler;

import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.jwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final RedisTemplate<String, Object> redisTemplate;

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

                // 4. Access Token의 남은 수명(TTL) 구하기
                Long expiration = jwtUtil.getExpirationTimeMillis(accessToken);

                // 5. Redis에 블랙리스트 등록 (Key: Access Token, Value: "logout", TTL 설정)
                redisTemplate.opsForValue().set(
                        accessToken,
                        "logout",
                        expiration,
                        TimeUnit.MILLISECONDS
                );
                log.info("[CustomLogoutHandler] Access Token 블랙리스트 등록 완료 (남은 시간: {} ms)", expiration);

            } catch (Exception e) {
                log.error("[CustomLogoutHandler] 로그아웃 처리 중 예외 발생: {}", e.getMessage());
                // 유효하지 않거나 이미 만료된 토큰이어도 클라이언트 상에서의 로그아웃 요청이므로 로그 처리만 하고 넘어갑니다.
            }
        }
    }
}
