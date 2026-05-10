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
@Tag(name = "유저 API", description = "유저 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/auth/signup")
    @Operation(summary = "회원 가입", description = "유저가 회원 가입을 합니다.")
    public CustomResponse<MemberResDTO.MemberSignupResDTO> signup (
            @RequestBody MemberReqDTO.MemberSignupReqDTO memberSignupReqDTO
    ){
        return CustomResponse.onSuccess(HttpStatus.CREATED, memberCommandService.signup(memberSignupReqDTO));
    }

    @GetMapping("/users")
    @Operation(summary = "유저 정보 조회", description = "유저 정보를 조회합니다.")
    public CustomResponse<MemberResDTO.MemberPreviewResDTO> getUsers (
    ){
        return CustomResponse.onSuccess(memberQueryService.getMember());
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
