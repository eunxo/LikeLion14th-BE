package com.project.likelion14thbe.domain.auth.controller;

import com.project.likelion14thbe.domain.auth.dto.response.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.auth.service.NaverService;
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

    @GetMapping("api/v1/auth/naver")
    public CustomResponse<String> redirectToNaver(
            HttpServletResponse response
    ) throws IOException {

        naverService.redirectToNaver(response);
        return CustomResponse.onSuccess(HttpStatus.FOUND, "naver redirect 완료");
    }

    @GetMapping("api/v1/naver/callback")
    public CustomResponse<JwtDTO> callback(
            @RequestParam("code") String code
    ) {

        // 1. 네이버 인증서버에서 토큰을 발급받는다.
        // 인가code와 Redirect URL을 파라미터로 전달하여 카카오 인증서버에 요청.
        String accessToken = naverService.getAccessTokenFromNaver(code);


        // 2. 1번에서 받은 토큰으로 네이버 리소스 서버에 사용자 정보 요청.
        NaverUserInfoResponseDTO userInfo = naverService.getUserInfo(accessToken);

        //TODO: 3. 회원가입 & 로그인 처리
        // 여기에 서버 사용자 로그인(인증) 또는 회원가입 로직 추가
        // case1: userInfo가 DB에 있으면 로그인
        JwtDTO jwtDTO = naverService.check(userInfo);
        // case2: userInfo가 DB에 없으면 회원 저장 후 로그인


        return CustomResponse.onSuccess(jwtDTO);
    }
}