package com.project.likelion14thbe.domain.kakao;

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
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/api/v1/auth/kakao")
    public CustomResponse<String> redirectToKakao(HttpServletResponse response) throws IOException {
        kakaoService.redirectToKakao(response);
        return CustomResponse.onSuccess(HttpStatus.FOUND, "kakao redirect 완료");
    }

    @GetMapping("/api/v1/kakao/callback")
    public CustomResponse<JwtDTO> callback(@RequestParam("code") String code) {
        // 1. 카카오 인증서버에서 access token 발급
        String kakaoAccessToken = kakaoService.getAccessTokenFromKakao(code);

        // 2. access token으로 카카오 사용자 정보 조회
        KakaoUserInfoResponseDTO userInfo = kakaoService.getUserInfo(kakaoAccessToken);

        // 3. 회원가입 & 로그인 (DB에 있으면 로그인, 없으면 가입 후 JWT 발급)
        JwtDTO jwtDto = kakaoAuthService.loginOrRegister(userInfo);

        return CustomResponse.onSuccess(jwtDto);
    }
}
