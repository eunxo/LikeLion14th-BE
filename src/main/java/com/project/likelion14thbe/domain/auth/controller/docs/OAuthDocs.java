package com.project.likelion14thbe.domain.auth.controller.docs;

import com.project.likelion14thbe.domain.auth.dto.response.JwtDTO;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@Tag(name = "OAuth", description = "소셜 로그인 API (카카오·네이버). 브라우저로 진입 URL 접속 시 Provider 인가 페이지로 리다이렉트된다.")
public interface OAuthDocs {

    @Operation(summary = "카카오 로그인 진입", description = "카카오 인가 페이지로 리다이렉트한다.")
    void kakaoAuthorize(HttpServletResponse response, HttpSession session) throws IOException;

    @Operation(summary = "카카오 콜백", description = "인가 코드로 로그인 또는 회원가입 후 일반 로그인과 동일한 JwtDTO를 반환한다. 인가 거부 시 OAUTH_ACCESS_DENIED(AUTH401_5)를 반환한다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = JwtDTO.class))))
    CustomResponse<JwtDTO> kakaoCallback(String code, String state, String error, HttpSession session);

    @Operation(summary = "네이버 로그인 진입", description = "네이버 인가 페이지로 리다이렉트한다.")
    void naverAuthorize(HttpServletResponse response, HttpSession session) throws IOException;

    @Operation(summary = "네이버 콜백", description = "인가 코드로 로그인 또는 회원가입 후 일반 로그인과 동일한 JwtDTO를 반환한다. 인가 거부 시 OAUTH_ACCESS_DENIED(AUTH401_5)를 반환한다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = JwtDTO.class))))
    CustomResponse<JwtDTO> naverCallback(String code, String state, String error, HttpSession session);
}
