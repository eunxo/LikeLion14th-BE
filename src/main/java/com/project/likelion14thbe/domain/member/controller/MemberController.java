package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
        // 기존 ResponseEntity에서 CustomResponse로 변경
        return CustomResponse.onSuccess(memberCommandService.signUp(signupReq));
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "JWT 토큰을 발급받습니다.")
    public CustomResponse<String> login(@RequestBody MemberReqDTO.LoginReq loginReq) {
        // 추후 로그인 로직 연결 시에도 CustomResponse 사용
        return CustomResponse.onSuccess("로그인 성공");
    }

    @GetMapping("/members/{memberId}")
    @Operation(summary = "내 정보 조회", description = "유저 ID로 프로필을 조회합니다.")
    public CustomResponse<MemberResDTO.ProfileRes> getMember(@PathVariable Long memberId) {
        // 기존 ResponseEntity에서 CustomResponse로 변경
        return CustomResponse.onSuccess(memberQueryService.getMember(memberId));
    }

    @PatchMapping("/members/{memberId}")
    @Operation(summary = "회원정보 수정", description = "이름이나 프로필 이미지를 수정합니다.")
    public CustomResponse<String> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberReqDTO.UpdateReq updateReq
    ) {
        memberCommandService.updateMember(memberId, updateReq);
        return CustomResponse.onSuccess("회원정보 수정 완료");
    }

    @PatchMapping("/members/{memberId}/password")
    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.")
    public CustomResponse<String> resetPassword(
            @PathVariable Long memberId,
            @RequestBody MemberReqDTO.PasswordResetDTO request
    ) {
        memberCommandService.updatePassword(memberId, request);
        return CustomResponse.onSuccess("비밀번호 변경 성공");
    }

    @DeleteMapping("/members/{memberId}")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다.")
    public CustomResponse<String> deleteMember(@PathVariable Long memberId) {
        memberCommandService.deleteMember(memberId);
        return CustomResponse.onSuccess("회원 탈퇴 성공");
    }
}