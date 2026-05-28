package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "유저API", description = "유저 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;


    @PostMapping("/auth/signup")
    @Operation(summary = "회원가입", description = "회원가입을 합니다")
    public CustomResponse<MemberResDTO.MemberCreateRes> createMember(
            @RequestBody MemberReqDTO.MemberCreateReq MemberCreateReq
    ){
        return CustomResponse
                .onSuccess(memberCommandService.createMember(MemberCreateReq));
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "로그인을 합니다")
    public CustomResponse<MemberResDTO.MemberLoginRes> login(
            @RequestBody MemberReqDTO.MemberLoginReq MemberLoginReq
    ){
        return CustomResponse
                .onSuccess(MemberResDTO.MemberLoginRes.builder().build());
    }


    @DeleteMapping("/members/me/out")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다")
    public CustomResponse<String> deleteMember(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        memberCommandService.deleteMember(userDetails.getUsername());
        return CustomResponse.onSuccess("회원 탈퇴 성공");
    }

    @GetMapping("/users/me")
    @Operation(summary = "프로필 조회", description = "프로필 조회를 합니다")
    public CustomResponse<MemberResDTO.MemberGetRes> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails
            ){
        return CustomResponse
                .onSuccess(memberQueryService.getProfile(userDetails.getUsername()));
    }

    @PatchMapping("/users/me/profile")
    @Operation(summary = "프로필 수정", description = "프로필 수정을 합니다")
    public CustomResponse<String> fixProfile(
            @RequestBody MemberReqDTO.MemberFixReq memberFixReq,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        memberCommandService.updateProfile(userDetails.getUsername(), memberFixReq);
        return CustomResponse
                .onSuccess("프로필 수정 성공");
    }

    @PatchMapping("/members/me/password")
    @Operation(summary = "비밀번호 변경", description = "id를 받아 비밀번호를 바꿉니다.")
    public CustomResponse<String> resetPassword(
            @RequestBody MemberReqDTO.PasswordResetDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        memberCommandService.updatePassword(userDetails.getUsername(), request);
        return CustomResponse.onSuccess("비밀번호 변경 성공");
    }

    @Operation(summary = "로그아웃", description = "발급받은 토큰으로 로그아웃을 진행합니다.")
    @PostMapping("/logout")
    public CustomResponse<String> logout() {
        return CustomResponse.onSuccess("로그아웃 성공");
    }
}
