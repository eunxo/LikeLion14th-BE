package com.project.likelion14thbe.domain.auth.controller;

import com.project.likelion14thbe.domain.auth.dto.response.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.auth.service.NaverService;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import com.project.likelion14thbe.global.security.userdetails.service.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "네이버 로그인 API", description = "네이버 로그인 API입니다.")
public class NaverLoginController {

    private final NaverService naverService;
    private final MemberCommandService memberCommandService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @GetMapping("api/v1/auth/naver")
    @Operation(summary = "네이버 로그인/회원 가입", description = "유저가 네이버 소셜 로그인/회원 가입을 합니다.")
    public CustomResponse<String> redirectToNaver(
            HttpServletResponse response
    ) throws IOException {
        naverService.redirectToNaver(response);
        return CustomResponse.onSuccess(HttpStatus.FOUND, "naver redirect 완료");
    }

    @GetMapping("api/v1/naver/callback")
    @Operation(summary = "네이버 콜백", description = "네이버 콜백")
    public CustomResponse<JwtDTO> callback(
            @RequestParam("code") String code,
            @RequestParam("state") String state
    ) {
        // 1. 네이버 토큰 발급
        String accessToken = naverService.getAccessTokenFromNaver(code, state);

        // 2. 토큰으로 유저 정보 획득
        NaverUserInfoResponseDTO userInfo = naverService.getUserInfo(accessToken);

        // 3. 기존 유저 여부 판별 후 신규 가입 또는 로그인 처리
        Optional<Member> member = naverService.findMember(userInfo);
        Member findMember = member.orElseGet(() -> memberCommandService.naverSignup(userInfo));

        CustomUserDetails customUserDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(findMember.getEmail());

        String accessT = jwtUtil.createJwtAccessToken(customUserDetails);
        String refreshT = jwtUtil.createJwtRefreshToken(customUserDetails);

        JwtDTO jwtDTO = new JwtDTO(accessT, refreshT);

        return CustomResponse.onSuccess(jwtDTO);
    }
}
