package com.project.likelion14thbe.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

public class MemberReqDTO {

    @Getter
    public static class SignUpReq {

        @Schema(description = "이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다.")
        private String name;

        @Schema(description = "이메일", example = "test@test.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @Schema(description = "비밀번호", example = "1234")
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
        private String password;
    }

    @Getter
    public static class LoginReq {

        @Schema(description = "이메일", example = "test@test.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @Schema(description = "비밀번호", example = "1234")
        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
    }

    @Getter
    public static class KakaoLoginReq {

        @Schema(description = "카카오 액세스 토큰", example = "kakao_access_token")
        private String kakaoAccessToken;
    }

    @Getter
    public static class UpdateReq {

        @Schema(description = "이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다.")
        private String name;

        @Schema(description = "비밀번호", example = "1234")
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
        private String password;
    }

    @Builder
    public record PasswordResetDTO(
            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
            String password
    ) {
    }
}