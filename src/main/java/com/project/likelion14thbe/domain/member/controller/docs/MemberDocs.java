package com.project.likelion14thbe.domain.member.controller.docs;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Member", description = "회원 API — 회원가입, 로그인, 내 정보 조회, 비밀번호 수정")
public interface MemberDocs {

    @Operation(
            summary = "회원가입",
            description = "이메일·비밀번호·이름으로 신규 회원을 등록한다. 이메일 중복 시 409."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.SignUpResDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 형식 오류 (필수 필드 누락 / 이메일 형식 / 비밀번호 길이)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "400 Bad Request",
                                      "message": "잘못된 요청입니다.",
                                      "result": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "409", description = "이미 가입된 이메일",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "409 Conflict",
                                      "message": "이미 가입된 이메일입니다.",
                                      "result": null
                                    }
                                    """)))
    })
    ResponseEntity<MemberResDTO.SignUpResDTO> signUp(MemberReqDTO.SignUpReqDTO request);

    @Operation(
            summary = "일반 로그인",
            description = "이메일·비밀번호로 로그인 후 JWT Access/Refresh 토큰을 발급한다. (현재 토큰 3개 필드는 dummy 상수 — 실제 발급은 jjwt 도입 시 service 내부에서 교체 예정)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.LoginResDTO.class))),
            @ApiResponse(responseCode = "401", description = "비밀번호 불일치",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "401 Unauthorized",
                                      "message": "이메일 또는 비밀번호가 일치하지 않습니다.",
                                      "result": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "404 Not Found",
                                      "message": "존재하지 않는 회원입니다.",
                                      "result": null
                                    }
                                    """)))
    })
    ResponseEntity<MemberResDTO.LoginResDTO> login(MemberReqDTO.LoginReqDTO request);

    @Operation(
            summary = "비밀번호 변경 (5주차 강의 PDF 단순 버전)",
            description = "회원의 비밀번호를 변경한다. 시큐리티 미적용으로 memberId를 PathVariable로 임시 전달."
    )
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
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MEMBER404_1",
                                      "message": "회원을 찾을 수 없습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<String> resetPassword(Long memberId, MemberReqDTO.PasswordResetDTO request);

    @Operation(
            summary = "회원 탈퇴 (soft delete)",
            description = "회원을 soft delete 한다. 시큐리티 미적용으로 memberId를 PathVariable로 임시 전달."
    )
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
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "MEMBER404_1",
                                      "message": "회원을 찾을 수 없습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<String> deleteMember(Long memberId);

    @Operation(
            summary = "내 정보 조회",
            description = "현재 로그인한 회원의 정보를 조회한다. (JWT 적용 전까지 memberId는 임시 query 파라미터)"
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberResDTO.MyInfoResDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증 토큰 없음 또는 만료",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "401 Unauthorized",
                                      "message": "로그인이 필요합니다.",
                                      "result": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "회원 미존재 (탈퇴 등)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "404 Not Found",
                                      "message": "회원을 찾을 수 없습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    ResponseEntity<MemberResDTO.MyInfoResDTO> getMyInfo(
            @Parameter(description = "조회 회원 ID (JWT 적용 전 임시)", example = "1") Long memberId
    );
}
