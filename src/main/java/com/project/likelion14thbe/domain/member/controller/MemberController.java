package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandServiceImpl;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원가입, 로그인, 마이페이지 관련 API")
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/auth/signup")
    @Operation(summary = "회원가입", description = "이메일과 비밀번호로 가입합니다.")
    public ResponseEntity<MemberResDTO.ProfileRes> signup(
            @RequestBody MemberReqDTO.SignupReq signupReq
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberCommandService.signUp(signupReq));
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "JWT 토큰을 발급받습니다.")
    public ResponseEntity<String> login(@RequestBody MemberReqDTO.LoginReq loginReq) {
        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "내 정보 조회", description = "유저 ID로 프로필을 조회합니다.")
    public ResponseEntity<MemberResDTO.ProfileRes> getMember(@PathVariable Long userId) {
        return ResponseEntity.ok(memberQueryService.getMember(userId));
    }

    @PatchMapping("/users/{userId}")
    @Operation(summary = "회원정보 수정", description = "이름이나 프로필 이미지를 수정합니다.")
    public ResponseEntity<String> updateMember(@PathVariable Long userId, @RequestBody MemberReqDTO.UpdateReq updateReq) {
        return ResponseEntity.ok("수정 완료");
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "회원 탈퇴", description = "계정을 삭제합니다.")
    public ResponseEntity<String> deleteMember(@PathVariable Long userId) {
        return ResponseEntity.ok("탈퇴 완료");
    }
}