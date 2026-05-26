package com.project.likelion14thbe.domain.naverlogin.controller;

import com.project.likelion14thbe.domain.naverlogin.dto.NaverTokenResponseDTO;
import com.project.likelion14thbe.domain.naverlogin.dto.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.naverlogin.service.NaverService;
import com.project.likelion14thbe.global.apiPayload.exception.CustomResponse;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Tag(name = "Naver Login", description = "네이버 소셜 로그인 API")
@RestController
@RequestMapping("/api/v1/auth/naver")
@RequiredArgsConstructor
public class NaverLoginController {

    private final NaverService naverService;

    @Operation(summary = "네이버 로그인 인증 URL 발급")
    @GetMapping("/login-url")
    public CustomResponse<Map<String, String>> getLoginUrl() {
        return CustomResponse.onSuccess(Map.of("url", naverService.getAuthorizationUrl()));
    }

    @Operation(summary = "네이버 로그인 콜백 (회원가입/로그인 처리)")
    @GetMapping("/callback")
    public CustomResponse<JwtDTO> callback(@RequestParam("code") String code,
                                           @RequestParam("state") String state) {
        log.info("[NaverLoginController] 콜백 진입: code={}, state={}", code, state);

        NaverTokenResponseDTO token = naverService.getAccessToken(code, state);
        NaverUserInfoResponseDTO userInfo = naverService.getUserInfo(token.getAccessToken());
        JwtDTO jwt = naverService.loginOrSignUp(userInfo);

        return CustomResponse.onSuccess(jwt);
    }
}
