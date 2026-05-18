package com.project.likelion14thbe.global.security.handler;

import com.project.likelion14thbe.global.apiPayload.exception.AuthErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.AuthException;
import com.project.likelion14thbe.global.security.jwt.repository.TokenRepository;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
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

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new AuthException(AuthErrorCode._UNAUTHORIZED);
        }

        String email = userDetails.getUsername();
        if (!tokenRepository.existsById(email)) {
            throw new AuthException(AuthErrorCode.ALREADY_LOGGED_OUT);
        }

        log.info("[ CustomLogoutHandler ] Refresh Token 삭제: {}", email);
        tokenRepository.deleteById(email);
    }
}
