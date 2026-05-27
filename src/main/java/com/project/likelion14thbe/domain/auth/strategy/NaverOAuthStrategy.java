package com.project.likelion14thbe.domain.auth.strategy;

import com.project.likelion14thbe.domain.auth.dto.oauth.NaverTokenResponse;
import com.project.likelion14thbe.domain.auth.dto.oauth.NaverUserInfoResponse;
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
public class NaverOAuthStrategy implements OAuthStrategy {

    private static final String AUTHORIZATION_URI = "https://nid.naver.com/oauth2.0/authorize";
    private static final String TOKEN_URI = "https://nid.naver.com/oauth2.0/token";
    private static final String USER_INFO_URI = "https://openapi.naver.com/v1/nid/me";

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public NaverOAuthStrategy(
            WebClient oauthWebClient,
            @Value("${naver.client.id}") String clientId,
            @Value("${naver.client.secret}") String clientSecret,
            @Value("${naver.redirect-uri}") String redirectUri
    ) {
        this.webClient = oauthWebClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    @Override
    public Provider getProvider() {
        return Provider.NAVER;
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
        NaverTokenResponse token;
        try {
            token = webClient.post()
                    .uri(TOKEN_URI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret)
                            .with("code", code)
                            .with("state", state))
                    .retrieve()
                    .bodyToMono(NaverTokenResponse.class)
                    .block();
        } catch (WebClientRequestException | WebClientResponseException e) {
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }
        if (token == null || token.accessToken() == null) {
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        }
        return token.accessToken();
    }

    @Override
    public OAuthUserInfo fetchUserInfo(String accessToken) {
        NaverUserInfoResponse info;
        try {
            info = webClient.get()
                    .uri(USER_INFO_URI)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(NaverUserInfoResponse.class)
                    .block();
        } catch (WebClientRequestException | WebClientResponseException e) {
            throw new AuthException(AuthErrorCode.NAVER_USER_INFO_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }
        if (info == null || info.response() == null || info.response().id() == null) {
            throw new AuthException(AuthErrorCode.NAVER_USER_INFO_REQUEST_FAILED);
        }

        NaverUserInfoResponse.NaverAccount account = info.response();
        return OAuthUserInfo.builder()
                .provider(Provider.NAVER)
                .providerId(account.id())
                .email(account.email())
                .nickname(account.nickname())
                .profileImage(account.profileImage())
                .build();
    }
}
