package com.project.likelion14thbe.domain.naver;

import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class NaverLoginController {

    private final NaverService naverService;
    private final NaverAuthService naverAuthService;

    @GetMapping({"/api/v1/auth/naver", "/api/v1/naver/login"})
    public CustomResponse<String> redirectToNaver(HttpServletResponse response) throws IOException {
        naverService.redirectToNaver(response);
        return CustomResponse.onSuccess(HttpStatus.FOUND, "naver redirect 완료");
    }

    @GetMapping("/api/v1/naver/callback")
    public CustomResponse<JwtDTO> callback(@RequestParam("code") String code) {
        // 1. 네이버 인증서버에서 access token 발급
        String naverAccessToken = naverService.getAccessTokenFromNaver(code);

        // 2. access token으로 네이버 사용자 정보 조회
        NaverUserInfoResponseDTO userInfo = naverService.getUserInfo(naverAccessToken);

        // 3. 회원가입 & 로그인
        JwtDTO jwtDto = naverAuthService.loginOrRegister(userInfo);

        return CustomResponse.onSuccess(jwtDto);
    }
}