package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "회원 API", description = "회원 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @PostMapping("/auth/signup")
    @Operation(summary = "회원가입", description = "이름, 이메일, 비밀번호를 입력하여 회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.SignUpRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    })
    public ResponseEntity<MemberResDTO.SignUpRes> signup(
            @RequestBody MemberReqDTO.SignUpReq request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberCommandService.signUp(request));
    }

    @GetMapping("/users/{memberId}")
    @Operation(summary = "회원 조회", description = "회원 ID로 회원의 기본 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.MemberPreviewResDTO.class))),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<MemberResDTO.MemberPreviewResDTO> getMember(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(memberQueryService.getMember(memberId));
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력하여 로그인을 진행합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.LoginRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치")
    })
    public ResponseEntity<MemberResDTO.LoginRes> login(
            @RequestBody(required = true) MemberReqDTO.LoginReq request
    ) {
        return ResponseEntity.ok(
                MemberResDTO.LoginRes.builder()
                        .isSuccess(true)
                        .code("USER200")
                        .message("로그인 성공")
                        .build()
        );
    }

    @PostMapping("/auth/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 액세스 토큰을 이용하여 로그인을 진행합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "카카오 로그인 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.KakaoLoginRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "필수 요청 값 누락"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    public ResponseEntity<MemberResDTO.KakaoLoginRes> kakaoLogin(
            @RequestBody(required = true) MemberReqDTO.KakaoLoginReq request
    ) {
        return ResponseEntity.ok(
                MemberResDTO.KakaoLoginRes.builder()
                        .isSuccess(true)
                        .code("USER200")
                        .message("카카오 로그인 성공")
                        .result(
                                MemberResDTO.KakaoLoginResult.builder()
                                        .userId(1L)
                                        .name("홍길동")
                                        .email("kakao@test.com")
                                        .build()
                        )
                        .build()
        );
    }

    @PostMapping("/auth/logout")
    @Operation(summary = "로그아웃", description = "현재 로그인한 사용자의 로그아웃을 진행합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.LogoutRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<MemberResDTO.LogoutRes> logout() {
        return ResponseEntity.ok(
                MemberResDTO.LogoutRes.builder()
                        .isSuccess(true)
                        .code("USER200")
                        .message("로그아웃 성공")
                        .build()
        );
    }

    @GetMapping("/users/me")
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "내 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.MyInfoRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<MemberResDTO.MyInfoRes> getMyInfo() {
        return ResponseEntity.ok(
                MemberResDTO.MyInfoRes.builder()
                        .memberId(1L)
                        .name("홍길동")
                        .email("test@test.com")
                        .build()
        );
    }

    @PatchMapping("/users/me")
    @Operation(summary = "회원 정보 수정", description = "현재 로그인한 사용자의 정보를 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.UpdateRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패")
    })
    public ResponseEntity<MemberResDTO.UpdateRes> updateMember(
            @RequestBody(required = true) MemberReqDTO.UpdateReq request
    ) {
        return ResponseEntity.ok(
                MemberResDTO.UpdateRes.builder()
                        .memberId(1L)
                        .name(request.getName())
                        .build()
        );
    }

    @DeleteMapping("/users/me")
    @Operation(summary = "회원 탈퇴", description = "현재 로그인한 사용자의 계정을 탈퇴 처리합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 탈퇴 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.DeleteRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public ResponseEntity<MemberResDTO.DeleteRes> deleteMember() {
        return ResponseEntity.ok(
                MemberResDTO.DeleteRes.builder()
                        .isSuccess(true)
                        .code("USER200")
                        .message("회원 탈퇴 성공")
                        .build()
        );
    }
}