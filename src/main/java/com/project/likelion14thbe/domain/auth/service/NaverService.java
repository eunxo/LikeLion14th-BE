package com.project.likelion14thbe.domain.auth.service;

import com.project.likelion14thbe.domain.auth.dto.response.NaverTokenResponseDTO;
import com.project.likelion14thbe.domain.auth.dto.response.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.auth.exception.AuthErrorCode;
import com.project.likelion14thbe.domain.auth.exception.AuthException;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
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
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class NaverService {

    private static final String authorizationURI = "https://nid.naver.com/oauth2.0/authorize";
    private static final String tokenURI = "https://nid.naver.com/oauth2.0/token";
    private static final String userInfoURI = "https://openapi.naver.com/v1/nid/me";

    private final String clientId;
    private final String clientSecret;
    private final String redirectURI;
    private final MemberRepository memberRepository;

    public NaverService(
            @Value("${spring.naver.client.id}") String clientId,
            @Value("${spring.naver.client.secret}") String clientSecret,
            @Value("${spring.naver.uri.redirect}") String redirectURI,
            MemberRepository memberRepository
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectURI = redirectURI;
        this.memberRepository = memberRepository;
    }

    public void redirectToNaver(HttpServletResponse response) throws IOException {

        // CSRF 방지를 위한 임의의 상태 토큰 생성 (실제 구현 시 세션 등에 보관하여 검증할 수 있음)
        String state = UUID.randomUUID().toString();

        String redirectUrl = UriComponentsBuilder
                .fromUriString(authorizationURI)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectURI)
                .queryParam("state", state)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }

    public String getAccessTokenFromNaver(String code, String state) {

        NaverTokenResponseDTO naverTokenResponseDto = null;
        try {
            naverTokenResponseDto = WebClient.create()
                    .post()
                    .uri(tokenURI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(
                            BodyInserters.fromFormData("grant_type", "authorization_code")
                                    .with("client_id", clientId)
                                    .with("client_secret", clientSecret)
                                    .with("code", code)
                                    .with("state", state)
                    )
                    .retrieve()
                    .bodyToMono(NaverTokenResponseDTO.class)
                    .block();
        } catch (WebClientRequestException e) {
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }

        if (naverTokenResponseDto == null || naverTokenResponseDto.accessToken() == null) {
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        }

        return naverTokenResponseDto.accessToken();
    }

    public NaverUserInfoResponseDTO getUserInfo(String accessToken) {
        NaverUserInfoResponseDTO userInfo = null;
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

        if (userInfo == null || userInfo.response() == null) {
            throw new AuthException(AuthErrorCode.NAVER_USER_INFO_REQUEST_FAILED);
        }

        return userInfo;
    }

    public Optional<Member> findMember(NaverUserInfoResponseDTO userInfo) {
        return memberRepository.findByEmailAndNotDeleted(userInfo.response().email());
    }
}
