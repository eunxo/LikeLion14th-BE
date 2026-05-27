package com.project.likelion14thbe.domain.naver.controller;

import com.project.likelion14thbe.domain.naver.dto.response.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.naver.service.NaverService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NaverLoginController {

    private final NaverService naverService;

    @GetMapping("/auth/naver")
    public CustomResponse<String> redirectToNaver(
            final HttpServletResponse response
    ) throws IOException {
        naverService.redirectToNaver(response);
        return CustomResponse.onSuccess(HttpStatus.FOUND, "naver redirect 완료");
    }

    @GetMapping("/naver/callback")
    public CustomResponse<JwtDTO> callback(
            @RequestParam("code") final String code,
            @RequestParam("state") final String state
    ) {
        String accessToken = naverService.getAccessTokenFromNaver(code, state);
        NaverUserInfoResponseDTO userInfo = naverService.getUserInfo(accessToken);

        JwtDTO jwtDTO = naverService.processNaverLoginOrSignup(userInfo);

        return CustomResponse.onSuccess(jwtDTO);
    }
}