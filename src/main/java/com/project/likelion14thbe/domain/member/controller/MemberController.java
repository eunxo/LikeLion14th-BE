package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MemberResDTO.MemberLoginRes> login(
            @RequestBody MemberReqDTO.MemberLoginReq MemberLoginReq
    ){
        return ResponseEntity.ok(MemberResDTO.MemberLoginRes.builder().build());
    }

    @DeleteMapping("/members/{memberId}/deletemember")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다")
    public CustomResponse<String> deleteMember(
            @PathVariable Long memberId
    ){
        memberCommandService.deleteMember(memberId);
        return CustomResponse.onSuccess("회원 탈퇴 성공");
    }

    @GetMapping("/users/{userId}/getprofile")
    @Operation(summary = "프로필 조회", description = "프로필 조회를 합니다")
    public CustomResponse<MemberResDTO.MemberGetRes> getProfile(
            @PathVariable long userId
    ){
        return CustomResponse
                .onSuccess(memberQueryService.getProfile(userId));
    }

    @PatchMapping("/users/{userId}/fixprofile")
    @Operation(summary = "프로필 수정", description = "프로필 수정을 합니다")
    public ResponseEntity<String> fixProfile(
            @PathVariable int userId,
            @RequestBody MemberReqDTO.MemberFixReq MemberFixReq
    ){
        return ResponseEntity.ok("프로필 수정 성공");
    }

    @PatchMapping("/members/{memberId}/password")
    @Operation(summary = "비밀번호 변경", description = "id를 받아 비밀번호를 바꿉니다.")
    public CustomResponse<String> resetPassword(
            @PathVariable Long memberId,
            @RequestBody MemberReqDTO.PasswordResetDTO request
    ) {
        memberCommandService.updatePassword(memberId, request);
        return CustomResponse.onSuccess("비밀번호 변경 성공");
    }
}
