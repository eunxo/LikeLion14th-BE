package com.project.likelion14thbe.domain.member.controller.docs;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Member", description = "회원 API — 회원가입, 로그인(일반/카카오), 내 정보 조회, 비밀번호 수정")
public interface MemberDocs {

    @Operation(
            summary = "회원가입",
            description = "이메일·비밀번호·닉네임으로 신규 회원을 등록한다. 이메일·닉네임 중복 시 409."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.SignUpResDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 형식 오류 (이메일/비밀번호/닉네임)", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 가입된 이메일 또는 닉네임", content = @Content)
    })
    ResponseEntity<MemberResDTO.SignUpResDTO> signUp(MemberReqDTO.SignUpReqDTO request);

    @Operation(
            summary = "일반 로그인",
            description = "이메일·비밀번호로 로그인 후 JWT Access/Refresh 토큰을 발급한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.LoginResDTO.class))),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치", content = @Content),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원", content = @Content)
    })
    ResponseEntity<MemberResDTO.LoginResDTO> login(MemberReqDTO.LoginReqDTO request);

    @Operation(
            summary = "카카오 로그인",
            description = "프론트에서 받은 카카오 OAuth Access Token을 검증하고 자체 JWT 토큰을 발급한다. 첫 로그인 시 자동 회원가입."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카카오 로그인 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.KakaoLoginResDTO.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 카카오 토큰", content = @Content),
            @ApiResponse(responseCode = "502", description = "카카오 인증 서버 통신 실패", content = @Content)
    })
    ResponseEntity<MemberResDTO.KakaoLoginResDTO> kakaoLogin(MemberReqDTO.KakaoLoginReqDTO request);

    @Operation(
            summary = "비밀번호 수정",
            description = "현재 로그인한 회원의 비밀번호를 변경한다. 현재 비밀번호 검증 필요."
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.UpdatePasswordResDTO.class))),
            @ApiResponse(responseCode = "400", description = "현재 비밀번호 불일치 또는 새 비밀번호 형식 오류", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content)
    })
    ResponseEntity<MemberResDTO.UpdatePasswordResDTO> updatePassword(MemberReqDTO.UpdatePasswordReqDTO request);

    @Operation(
            summary = "내 정보 조회",
            description = "현재 로그인한 회원의 정보를 조회한다."
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.MyInfoResDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증 토큰 없음 또는 만료", content = @Content),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음 (탈퇴 등)", content = @Content)
    })
    ResponseEntity<MemberResDTO.MyInfoResDTO> getMyInfo();
}
