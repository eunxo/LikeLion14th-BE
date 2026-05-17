package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원가입, 로그인, 마이페이지 관련 API")
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/auth/signup")
    @Operation(summary = "회원가입", description = "이메일과 비밀번호로 가입합니다.")
    public CustomResponse<MemberResDTO.ProfileRes> signup(
            @RequestBody MemberReqDTO.SignupReq signupReq
    ) {
        return CustomResponse.onSuccess(memberCommandService.signUp(signupReq));
    }

    @GetMapping("/members/me")
    @Operation(summary = "내 정보 조회", description = "토큰을 기반으로 본인의 프로필을 조회합니다.")
    public CustomResponse<MemberResDTO.ProfileRes> getMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return CustomResponse.onSuccess(memberQueryService.getMemberByEmail(customUserDetails.getUsername()));
    }

    @PatchMapping("/members/me")
    @Operation(summary = "회원정보 수정", description = "이름이나 프로필 이미지를 수정합니다.")
    public CustomResponse<String> updateMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MemberReqDTO.UpdateReq updateReq
    ) {
        memberCommandService.updateMember(customUserDetails.getUsername(), updateReq);
        return CustomResponse.onSuccess("회원정보 수정 완료");
    }

    @PatchMapping("/members/me/password")
    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.")
    public CustomResponse<String> resetPassword(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MemberReqDTO.PasswordResetDTO request
    ) {
        memberCommandService.updatePassword(customUserDetails.getUsername(), request);
        return CustomResponse.onSuccess("비밀번호 변경 성공");
    }

    @DeleteMapping("/members/me")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다.")
    public CustomResponse<String> deleteMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        memberCommandService.deleteMember(customUserDetails.getUsername());
        return CustomResponse.onSuccess("회원 정보가 삭제 되었습니다.");
    }
}