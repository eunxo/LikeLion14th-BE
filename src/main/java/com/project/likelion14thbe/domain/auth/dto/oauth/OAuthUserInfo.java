package com.project.likelion14thbe.domain.auth.dto.oauth;

import com.project.likelion14thbe.domain.auth.enums.Provider;
import lombok.Builder;

@Builder
public record OAuthUserInfo(
        Provider provider,
        String providerId,
        String email,
        String nickname,
        String profileImage
) {
}
