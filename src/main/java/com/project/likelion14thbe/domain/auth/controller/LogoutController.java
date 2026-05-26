package com.project.likelion14thbe.domain.auth.controller;

import com.project.likelion14thbe.global.apiPayload.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Swagger 문서화 전용 Controller.
 */
@RestController
@RequestMapping("/api/v1/members")
@Tag(name = "Logout", description = "로그아웃 API (실제 처리는 Security Filter)")
public class LogoutController {

    @Operation(
            summary = "로그아웃",
            description = "Authorization 헤더에 Bearer {accessToken} 을 담아 호출합니다. " +
                    "DB의 Refresh Token 을 삭제하고 SecurityContext 를 클리어합니다.",
            security = @SecurityRequirement(name = "JWT TOKEN")
    )
    @PostMapping("/logout")
    public CustomResponse<String> logout() {
        // LogoutFilter 가 이 메서드 호출 전에 가로채므로 실제로 실행되지 않음
        return CustomResponse.onSuccess("로그아웃이 완료되었습니다.");
    }
}
