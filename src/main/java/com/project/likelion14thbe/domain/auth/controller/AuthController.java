package com.project.likelion14thbe.domain.auth.controller;

import com.project.likelion14thbe.domain.auth.service.AuthService;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "토큰 발급 API", description = "토큰 발급 API입니다.")
public class AuthController {

    private final AuthService authService;
    private final MemberCommandService memberCommandService;

    @Operation(method = "POST", summary = "토큰 재발급", description = "토큰 재발급. accessToken과 refreshToken을 body에 담아서 전송합니다.")
    @PostMapping("/reissue")
    public CustomResponse<?> reissue(@RequestBody JwtDTO jwtDto) {

        log.info("[ Auth Controller ] 토큰을 재발급합니다. ");

        return CustomResponse.onSuccess(authService.reissueToken(jwtDto));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "유저가 회원 가입을 합니다.")
    public CustomResponse<MemberResDTO.MemberSignupResDTO> signup (
            @RequestBody MemberReqDTO.MemberSignupReqDTO memberSignupReqDTO
    ){
        return CustomResponse.onSuccess(HttpStatus.CREATED, memberCommandService.signup(memberSignupReqDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "로컬 로그인", description = "유저가 로컬 로그인을 합니다.")
    public CustomResponse<String> login (
            @RequestBody MemberReqDTO.LoginReq loginReq
    ){
        return CustomResponse.onSuccess("로그인 성공");
    }
}