package com.project.likelion14thbe.domain.auth.controller;

import com.project.likelion14thbe.domain.auth.controller.docs.AuthDocs;
import com.project.likelion14thbe.domain.auth.dto.response.JwtDTO;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController implements AuthDocs {

    // CustomLoginFilter가 /api/v1/login 요청을 가로채 직접 인증을 처리하므로 이 메서드는 호출되지 않는다.
    @Override
    @PostMapping("/login")
    public CustomResponse<JwtDTO> login(@RequestBody MemberReqDTO.LoginReqDTO request) {
        throw new UnsupportedOperationException("로그인은 CustomLoginFilter가 처리한다.");
    }

    // Spring Security LogoutFilter가 /api/v1/auth/logout 요청을 가로채 CustomLogoutHandler를 호출하므로 이 메서드는 호출되지 않는다.
    @Override
    @PostMapping("/auth/logout")
    public CustomResponse<String> logout() {
        throw new UnsupportedOperationException("로그아웃은 Spring Security LogoutFilter가 처리한다.");
    }
}
