package com.project.likelion14thbe.domain.member.controller;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.query.MemberQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.handler.CustomLogoutHandler;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "회원 API", description = "회원 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final CustomLogoutHandler customLogoutHandler;

    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입", description = "이름, 이메일, 비밀번호를 입력하여 회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    })
    public CustomResponse<MemberResDTO.SignUpRes> signup(
            @Valid @RequestBody MemberReqDTO.SignUpReq request
    ) {
        return CustomResponse.onSuccess(HttpStatus.CREATED, memberCommandService.signUp(request));
    }

    @GetMapping("/users/{memberId}")
    @Operation(summary = "회원 조회", description = "회원 ID로 회원의 기본 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public CustomResponse<MemberResDTO.MemberPreviewResDTO> getMember(
            @PathVariable @Min(value = 1, message = "memberId는 1 이상이어야 합니다.") Long memberId
    ) {
        return CustomResponse.onSuccess(memberQueryService.getMember(memberId));
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호를 JSON body로 전송합니다. 실제 인증·토큰 발급은 CustomLoginFilter가 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공 (accessToken, refreshToken 반환)"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치")
    })
    public CustomResponse<Void> login(
            @Valid @RequestBody(required = true) MemberReqDTO.LoginReq request
    ) {
        // 실제 인증·토큰 발급은 CustomLoginFilter가 처리
        return CustomResponse.onSuccess(null);
    }

    @PostMapping("/auth/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 액세스 토큰을 이용하여 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카카오 로그인 성공"),
            @ApiResponse(responseCode = "400", description = "필수 요청 값 누락"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    public CustomResponse<MemberResDTO.KakaoLoginResult> kakaoLogin(
            @RequestBody(required = true) MemberReqDTO.KakaoLoginReq request
    ) {
        return CustomResponse.onSuccess(
                MemberResDTO.KakaoLoginResult.builder()
                        .userId(1L)
                        .name("홍길동")
                        .email("kakao@test.com")
                        .build()
        );
    }

    @PostMapping("/auth/logout")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "로그아웃", description = "Authorization 헤더에 Bearer accessToken을 넣고 호출합니다. refreshToken이 삭제됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 또는 이미 로그아웃된 상태")
    })
    public CustomResponse<String> logout(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        customLogoutHandler.logout(request, response, authentication);
        SecurityContextHolder.clearContext();
        return CustomResponse.onSuccess("로그아웃 성공");
    }

    @GetMapping("/users/me")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.MyInfoRes.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    public CustomResponse<MemberResDTO.MyInfoRes> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return CustomResponse.onSuccess(memberQueryService.getMyInfo(userDetails.getUsername()));
    }

    @PatchMapping("/users/me")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "회원 정보 수정", description = "현재 로그인한 사용자의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    public CustomResponse<MemberResDTO.UpdateRes> updateMember(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody(required = true) MemberReqDTO.UpdateReq request
    ) {
        return CustomResponse.onSuccess(memberCommandService.updateMyInfo(userDetails.getUsername(), request));
    }

    @PatchMapping("/members/{memberId}/password")
    @Operation(summary = "회원 비밀번호 변경", description = "회원 ID 기준으로 비밀번호를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    public CustomResponse<String> resetPassword(
            @PathVariable @Min(value = 1, message = "memberId는 1 이상이어야 합니다.") Long memberId,
            @Valid @RequestBody MemberReqDTO.PasswordResetDTO request
    ) {
        memberCommandService.updatePassword(memberId, request);
        return CustomResponse.onSuccess("비밀번호 변경 성공");
    }

    @DeleteMapping("/members/{memberId}")
    @Operation(summary = "회원 탈퇴(관리자/식별자 기준)", description = "회원 ID 기준으로 논리 삭제 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    public CustomResponse<String> deleteMember(
            @PathVariable @Min(value = 1, message = "memberId는 1 이상이어야 합니다.") Long memberId
    ) {
        memberCommandService.deleteMember(memberId);
        return CustomResponse.onSuccess("회원 탈퇴 성공");
    }
}
