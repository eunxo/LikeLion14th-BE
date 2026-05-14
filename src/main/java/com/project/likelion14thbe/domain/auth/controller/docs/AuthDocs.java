package com.project.likelion14thbe.domain.auth.controller.docs;

import com.project.likelion14thbe.domain.auth.dto.response.JwtDTO;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth", description = "인증 API — 로그인은 Spring Security 필터(CustomLoginFilter)가 직접 처리한다. 이 컨트롤러는 Swagger 문서 노출 전용.")
public interface AuthDocs {

    @Operation(
            summary = "로그인",
            description = "이메일·비밀번호로 인증하여 accessToken·refreshToken을 발급한다. " +
                    "실제 처리는 CustomLoginFilter.attemptAuthentication / successfulAuthentication 에서 수행되며, " +
                    "이 메서드 본문은 호출되지 않는다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = JwtDTO.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패 (이메일 없음·비밀번호 불일치)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "AUTH401_1",
                                      "message": "이메일 또는 비밀번호가 잘못되었습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<JwtDTO> login(MemberReqDTO.LoginReqDTO request);
}
