package com.project.likelion14thbe.domain.member.controller.docs;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Member", description = "회원 API — 회원가입, 내 정보 조회, 비밀번호 수정, 회원 탈퇴")
public interface MemberDocs {

    @Operation(
            summary = "회원가입",
            description = "이메일·비밀번호·이름으로 신규 회원을 등록한다. 이메일 중복 시 409."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.SignUpResDTO.class))),
            @ApiResponse(responseCode = "400", description = "@Valid DTO 필드 검증 실패",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "VALID400_1",
                                      "message": "잘못된 DTO 필드입니다.",
                                      "result": {
                                        "email": "올바른 이메일 형식이 아닙니다.",
                                        "password": "비밀번호는 8자 이상이어야 합니다."
                                      }
                                    }
                                    """))),
            @ApiResponse(responseCode = "409", description = "이미 가입된 이메일",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MEMBER409_1",
                                      "message": "이미 사용 중인 이메일입니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<MemberResDTO.SignUpResDTO> signUp(MemberReqDTO.SignUpReqDTO request);

    @Operation(
            summary = "비밀번호 변경",
            description = "로그인한 본인의 비밀번호를 변경한다. JWT의 subject(email)로 본인을 식별한다."
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "200",
                                      "message": "OK",
                                      "result": "비밀번호 변경 성공"
                                    }
                                    """))),
            @ApiResponse(responseCode = "401", description = "인증 토큰 없음·만료·위조",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 미존재 (탈퇴 등)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MEMBER404_1",
                                      "message": "회원이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<String> resetPassword(CustomUserDetails user, MemberReqDTO.PasswordResetDTO request);

    @Operation(
            summary = "회원 탈퇴 (soft delete)",
            description = "로그인한 본인을 soft delete 한다. Member.deletedAt 컬럼이 갱신되며 30일 후 스케줄러가 hard delete 시도."
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "200",
                                      "message": "OK",
                                      "result": "회원 탈퇴 성공"
                                    }
                                    """))),
            @ApiResponse(responseCode = "401", description = "인증 토큰 없음·만료·위조",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MEMBER404_1",
                                      "message": "회원이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<String> deleteMember(CustomUserDetails user);

    @Operation(
            summary = "내 정보 조회",
            description = "로그인한 본인의 정보를 조회한다."
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.MyInfoResDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증 토큰 없음·만료·위조",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 미존재 (탈퇴 등)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MEMBER404_1",
                                      "message": "회원이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<MemberResDTO.MyInfoResDTO> getMyInfo(CustomUserDetails user);
}
