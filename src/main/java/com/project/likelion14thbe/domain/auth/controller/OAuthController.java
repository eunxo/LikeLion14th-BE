package com.project.likelion14thbe.domain.auth.controller;

import com.project.likelion14thbe.domain.auth.controller.docs.OAuthDocs;
import com.project.likelion14thbe.domain.auth.dto.response.JwtDTO;
import com.project.likelion14thbe.domain.auth.enums.Provider;
import com.project.likelion14thbe.domain.auth.service.command.OAuthCommandService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OAuthController implements OAuthDocs {

    private final OAuthCommandService oAuthCommandService;

    @Override
    @GetMapping("/auth/kakao")
    public void kakaoAuthorize(HttpServletResponse response, HttpSession session) throws IOException {
        oAuthCommandService.redirect(Provider.KAKAO, response, session);
    }

    @Override
    @GetMapping("/kakao/callback")
    public CustomResponse<JwtDTO> kakaoCallback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "error", required = false) String error,
            HttpSession session
    ) {
        return CustomResponse.onSuccess(
                oAuthCommandService.handleCallback(Provider.KAKAO, code, state, error, session));
    }

    @Override
    @GetMapping("/auth/naver")
    public void naverAuthorize(HttpServletResponse response, HttpSession session) throws IOException {
        oAuthCommandService.redirect(Provider.NAVER, response, session);
    }

    @Override
    @GetMapping("/naver/callback")
    public CustomResponse<JwtDTO> naverCallback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "error", required = false) String error,
            HttpSession session
    ) {
        return CustomResponse.onSuccess(
                oAuthCommandService.handleCallback(Provider.NAVER, code, state, error, session));
    }
}
