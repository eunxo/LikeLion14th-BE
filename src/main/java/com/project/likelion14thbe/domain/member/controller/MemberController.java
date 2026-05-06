package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Member", description = "회원 및 마이페이지 관련 API")
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 오류")
    })
    public ResponseEntity<MemberResDTO.MemberCreateResDTO> signUp(@RequestBody MemberReqDTO.MemberCreateReqDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberCommandService.createMember(request));
    }

    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치")
    })
    public ResponseEntity<MemberResDTO.LoginRes> login(@RequestBody MemberReqDTO.LoginReq request) {
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "회원 정보 조회", description = "특정 회원의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 회원을 찾을 수 없음")
    })
    public ResponseEntity<MemberResDTO.MemberPreviewResDTO> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberQueryService.getMember(id));
    }






}