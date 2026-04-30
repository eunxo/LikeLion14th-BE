package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Member", description = "회원 및 마이페이지 관련 API")
@RequestMapping("/api/v1/members")
public class MemberController {

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 오류")
    })
    public ResponseEntity<String> signUp(@RequestBody MemberReqDTO.SignUpReq request) {
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치")
    })
    public ResponseEntity<MemberResDTO.LoginRes> login(@RequestBody MemberReqDTO.LoginReq request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 액세스 토큰을 검증하고 로그인 또는 회원가입을 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카카오 로그인/가입 성공"),
            @ApiResponse(responseCode = "403", description = "유효하지 않은 소셜 토큰")
    })
    public ResponseEntity<MemberResDTO.KakaoLoginRes> kakaoLogin(@RequestBody MemberReqDTO.KakaoLoginReq request) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 수정", description = "기존 비밀번호 확인 후 새로운 비밀번호로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 수정 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 불일치"),
            @ApiResponse(responseCode = "401", description = "현재 비밀번호 불일치")
    })
    public ResponseEntity<String> updatePassword(@RequestBody MemberReqDTO.PasswordUpdateReq request) {
        return ResponseEntity.ok("비밀번호 수정 성공");
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 프로필 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    public ResponseEntity<MemberResDTO.MemberProfileRes> getMyProfile() {
        return ResponseEntity.ok().build();
    }
}