package com.project.likelion14thbe.domain.auth.controller;

import com.project.likelion14thbe.domain.auth.dto.response.KakaoUserInfoResponseDTO;
import com.project.likelion14thbe.domain.auth.service.KakaoService;
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
@Tag(name = "카카오 로그인 API", description = "카카오 로그인 API입니다.")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final MemberCommandService memberCommandService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @GetMapping("api/v1/auth/kakao")
    @Operation(summary = "카카오 로그인/회원 가입", description = "유저가 카카오 소셜 로그인/회원 가입을 합니다.")
    public CustomResponse<String> redirectToKakao(
            HttpServletResponse response
    ) throws IOException {

        kakaoService.redirectToKakao(response);
        return CustomResponse.onSuccess(HttpStatus.FOUND, "kakao redirect 완료");
    }

    @GetMapping("api/v1/kakao/callback")
    @Operation(summary = "카카오 콜백", description = "카카오 콜백")
    public CustomResponse<JwtDTO> callback(
            @RequestParam("code") String code
    ) {

        // 1. 카카오 인증서버에서 토큰을 발급받는다.
        // 인가code와 Redirect URL을 파라미터로 전달하여 카카오 인증서버에 요청.
        String accessToken = kakaoService.getAccessTokenFromKakao(code);


        // 2. 1번에서 받은 토큰으로 카카오 리소스 서버에 사용자 정보 요청.
        KakaoUserInfoResponseDTO userInfo = kakaoService.getUserInfo(accessToken);

        //TODO: 3. 회원가입 & 로그인 처리
        // 여기에 서버 사용자 로그인(인증) 또는 회원가입 로직 추가
        // case1: userInfo가 DB에 있으면 로그인
        // case2: userInfo가 DB에 없으면 회원 저장 후 로그인

        Optional<Member> member = kakaoService.findMember(userInfo);

        Member findMember = member.orElseGet(() -> memberCommandService.kakaoSignup(userInfo));

        CustomUserDetails customUserDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(findMember.getEmail());

        String accessT = jwtUtil.createJwtAccessToken(customUserDetails);
        String refreshT = jwtUtil.createJwtRefreshToken(customUserDetails);

        JwtDTO jwtDTO = new JwtDTO(accessT, refreshT);

        return CustomResponse.onSuccess(jwtDTO);
    }
}