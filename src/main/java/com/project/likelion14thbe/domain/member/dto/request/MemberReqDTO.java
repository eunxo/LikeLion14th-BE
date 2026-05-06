package com.project.likelion14thbe.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MemberReqDTO {

    @Schema(description = "회원가입 요청 DTO")
    public record SignUpReqDTO(
            @Schema(description = "이메일", example = "user@example.com")
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,
            @Schema(description = "비밀번호 (최소 8자)", example = "passw0rd!!")
            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
            String password,
            @Schema(description = "이름 (2~50자)", example = "홍길동")
            @NotBlank(message = "이름은 필수입니다.")
            @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
            String name
    ) {
    }

    @Schema(description = "일반 로그인 요청 DTO")
    public record LoginReqDTO(
            @Schema(description = "이메일", example = "user@example.com")
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,
            @Schema(description = "비밀번호", example = "passw0rd!!")
            @NotBlank(message = "비밀번호는 필수입니다.")
            String password
    ) {
    }

    @Schema(description = "비밀번호 수정 요청 DTO")
    public record UpdatePasswordReqDTO(
            @Schema(description = "현재 비밀번호", example = "passw0rd!!")
            @NotBlank(message = "현재 비밀번호는 필수입니다.")
            String currentPassword,
            @Schema(description = "새 비밀번호 (최소 8자)", example = "newPassw0rd!!")
            @NotBlank(message = "새 비밀번호는 필수입니다.")
            @Size(min = 8, message = "새 비밀번호는 8자 이상이어야 합니다.")
            String newPassword
    ) {
    }
}
