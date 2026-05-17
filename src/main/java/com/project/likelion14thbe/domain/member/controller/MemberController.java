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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "유저 API", description = "유저 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @GetMapping("/users")
    @Operation(summary = "유저 정보 조회", description = "유저 정보를 조회합니다.")
    public CustomResponse<MemberResDTO.MemberPreviewResDTO> getUsers (
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        return CustomResponse.onSuccess(memberQueryService.getMember(customUserDetails.getUsername()));
    }

    @PatchMapping("/users/{userId}/password")
    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.")
    public CustomResponse<String> changePassword (
            @PathVariable Long userId,
            @RequestBody MemberReqDTO.PasswordResetDTO request
    ){
        memberCommandService.updatePassword(userId, request);
        return CustomResponse.onSuccess("비밀번호 변경 성공");
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
    public CustomResponse<String> deleteMember (Long memberId) {
        memberCommandService.deleteMember(memberId);
        return CustomResponse.onSuccess("회원 탈퇴 성공");
    }
}
