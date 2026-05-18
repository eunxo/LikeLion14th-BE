package com.project.likelion14thbe.domain.auth.strategy;

import com.project.likelion14thbe.domain.auth.dto.oauth.KakaoTokenResponse;
import com.project.likelion14thbe.domain.auth.dto.oauth.KakaoUserInfoResponse;
import com.project.likelion14thbe.domain.auth.dto.oauth.OAuthUserInfo;
import com.project.likelion14thbe.domain.auth.enums.Provider;
import com.project.likelion14thbe.domain.auth.exception.AuthErrorCode;
import com.project.likelion14thbe.domain.auth.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class KakaoOAuthStrategy implements OAuthStrategy {

    private static final String AUTHORIZATION_URI = "https://kauth.kakao.com/oauth/authorize";
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public KakaoOAuthStrategy(
            WebClient oauthWebClient,
            @Value("${kakao.client.id}") String clientId,
            @Value("${kakao.client.secret}") String clientSecret,
            @Value("${kakao.redirect-uri}") String redirectUri
    ) {
        this.webClient = oauthWebClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }

    @Override
    public String buildAuthorizationUrl(String state) {
        return UriComponentsBuilder
                .fromUriString(AUTHORIZATION_URI)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    @Override
    public String exchangeCodeForToken(String code, String state) {
        // Kakao 토큰 요청 사양상 state 불필요 — OAuthStrategy 인터페이스 일관성을 위해 파라미터만 유지
        KakaoTokenResponse token;
        try {
            token = webClient.post()
                    .uri(TOKEN_URI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret)
                            .with("redirect_uri", redirectUri)
                            .with("code", code))
                    .retrieve()
                    .bodyToMono(KakaoTokenResponse.class)
                    .block();
        } catch (WebClientRequestException | WebClientResponseException e) {
            throw new AuthException(AuthErrorCode.KAKAO_TOKEN_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }
        if (token == null || token.accessToken() == null) {
            throw new AuthException(AuthErrorCode.KAKAO_TOKEN_REQUEST_FAILED);
        }
        return token.accessToken();
    }

    @Override
    public OAuthUserInfo fetchUserInfo(String accessToken) {
        KakaoUserInfoResponse info;
        try {
            info = webClient.get()
                    .uri(USER_INFO_URI)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(KakaoUserInfoResponse.class)
                    .block();
        } catch (WebClientRequestException | WebClientResponseException e) {
            throw new AuthException(AuthErrorCode.KAKAO_USER_INFO_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }
        if (info == null || info.id() == null || info.kakaoAccount() == null) {
            throw new AuthException(AuthErrorCode.KAKAO_USER_INFO_REQUEST_FAILED);
        }

        KakaoUserInfoResponse.KakaoAccount account = info.kakaoAccount();
        String nickname = account.profile() != null ? account.profile().nickname() : null;
        String profileImage = account.profile() != null ? account.profile().profileImageUrl() : null;

        return OAuthUserInfo.builder()
                .provider(Provider.KAKAO)
                .providerId(String.valueOf(info.id()))
                .email(account.email())
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
