package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "유저API", description = "유저 관련 API")
@RequestMapping("/api/v1")
public class MemberController {
    @PostMapping("/auth/signup")
    @Operation(summary = "회원가입", description = "회원가입을 합니다")
    public ResponseEntity<String> createUser(
            @RequestBody MemberReqDTO.MemberCreateReq MemberCreateReq
    ){
        //회원 가입 로직
        return ResponseEntity.ok("회원 가입 완료");
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "로그인을 합니다")
    public ResponseEntity<MemberResDTO.MemberLoginRes> login(
            @RequestBody MemberReqDTO.MemberLoginReq MemberLoginReq
    ){
        // 로그인 로직
        return ResponseEntity.ok(MemberResDTO.MemberLoginRes.builder().build());
    }

    @DeleteMapping("/users/{userId}/deleteuser")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다")
    public ResponseEntity<String> deleteUser(
            @PathVariable int userId
    ){
        // 회원 탈퇴 로직
        return ResponseEntity.ok("회원 탈퇴 성공");
    }

    @GetMapping("/users/{userId}/getprofile")
    @Operation(summary = "프로필 조회", description = "프로필 조회를 합니다")
    public ResponseEntity<MemberResDTO.MemberGetRes> getProfile(
            @PathVariable int userId
    ){
        //프로필 조회 로직
        return ResponseEntity.ok(MemberResDTO.MemberGetRes.builder().build());
    }

    @PatchMapping("/users/{userId}/fixprofile")
    @Operation(summary = "프로필 수정", description = "프로필 수정을 합니다")
    public ResponseEntity<String> fixProfile(
            @PathVariable int userId,
            @RequestBody MemberReqDTO.MemberFixReq MemberFixReq
    ){
        //프로필 수정 로직
        return ResponseEntity.ok("프로필 수정 성공");
    }

}
