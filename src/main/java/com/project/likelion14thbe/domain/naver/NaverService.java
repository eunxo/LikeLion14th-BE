package com.project.likelion14thbe.domain.naver;

import com.project.likelion14thbe.global.apiPayload.exception.AuthErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Service
public class NaverService {

    private static final String authorizationURI =
            "https://nid.naver.com/oauth2.0/authorize";

    private static final String tokenURI =
            "https://nid.naver.com/oauth2.0/token";

    private static final String userInfoURI =
            "https://openapi.naver.com/v1/nid/me";

    private final String clientId;
    private final String clientSecret;
    private final String redirectURI;

    public NaverService(
            @Value("${spring.naver.client.id}") String clientId,
            @Value("${spring.naver.client.secret}") String clientSecret,
            @Value("${spring.naver.uri.redirect}") String redirectURI
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectURI = redirectURI;
    }

    public void redirectToNaver(HttpServletResponse response) throws IOException {

        String redirectUrl = UriComponentsBuilder
                .fromUriString(authorizationURI)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectURI)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }

    public String getAccessTokenFromNaver(String code) {

        NaverTokenResponseDTO naverTokenResponseDto;

        try {

            naverTokenResponseDto = WebClient.create()
                    .post()
                    .uri(tokenURI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(
                            BodyInserters.fromFormData("grant_type", "authorization_code")
                                    .with("client_id", clientId)
                                    .with("client_secret", clientSecret)
                                    .with("redirect_uri", redirectURI)
                                    .with("code", code)
                    )
                    .retrieve()
                    .bodyToMono(NaverTokenResponseDTO.class)
                    .block();

        } catch (WebClientRequestException e) {
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }

        if (naverTokenResponseDto == null
                || naverTokenResponseDto.accessToken() == null) {

            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        }

        return naverTokenResponseDto.accessToken();
    }

    public NaverUserInfoResponseDTO getUserInfo(String accessToken) {

        NaverUserInfoResponseDTO userInfo;

        try {

            userInfo = WebClient.create()
                    .get()
                    .uri(userInfoURI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(NaverUserInfoResponseDTO.class)
                    .block();

        } catch (WebClientRequestException e) {
            throw new AuthException(AuthErrorCode.NAVER_USER_INFO_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }

        if (userInfo == null) {
            throw new AuthException(AuthErrorCode.NAVER_USER_INFO_REQUEST_FAILED);
        }

        log.info("[ Naver Service ] userInfo ---> {}", userInfo);

        return userInfo;
    }
}