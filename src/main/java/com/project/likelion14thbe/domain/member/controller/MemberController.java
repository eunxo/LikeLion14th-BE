package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "유저 API", description = "유저 관련 API")
@RequestMapping("/api/v1")
public class MemberController {

    @PostMapping("/auth/signup")
    @Operation(summary = "회원 가입", description = "유저가 회원 가입을 합니다.")
    public ResponseEntity<String> signup (
            @RequestBody MemberReqDTO.UserSignupReq userSignupReq
    ){
        // 리뷰 생성 로직~~~
        return ResponseEntity.ok("회원가입 성공");
    }

    @PatchMapping("/users/{userId}/password")
    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.")
    public ResponseEntity<String> changePassword (
            @PathVariable Long userId
    ){
        // 비밀번호 변경 로직~~~
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로컬 로그인", description = "유저가 로컬 로그인을 합니다.")
    public ResponseEntity<MemberResDTO.UserTokenRes> login (
            @RequestBody MemberReqDTO.UserLoginReq userLoginReq
    ){
        // 로컬 로그인 로직~~~
        return ResponseEntity.ok(
                MemberResDTO.UserTokenRes.builder().build());
    }

    @PostMapping("/auth/login/kakao")
    @Operation(summary = "카카오 로그인", description = "유저가 카카오 소셜 로그인을 합니다.")
    public ResponseEntity<MemberResDTO.UserTokenRes> loginKakao (
            @RequestBody MemberReqDTO.UserLoginKakaoReq userLoginKakaoReq
    ){
        // 소셜 로그인 로직~~~
        return ResponseEntity.ok(
                MemberResDTO.UserTokenRes.builder().build());
    }
}
