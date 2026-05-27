package com.project.likelion14thbe.domain.naver.service;

import com.project.likelion14thbe.domain.auth.exception.AuthException;
import com.project.likelion14thbe.domain.auth.exception.AuthErrorCode;
import com.project.likelion14thbe.domain.naver.dto.response.NaverTokenResponseDTO;
import com.project.likelion14thbe.domain.naver.dto.response.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

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
    private final JwtUtil jwtUtil;

    public NaverService(
            @Value("${spring.naver.client.id}") final String clientId,
            @Value("${spring.naver.client.secret}") final String clientSecret,
            @Value("${spring.naver.uri.redirect}") final String redirectURI,
            final MemberRepository memberRepository,
            final JwtUtil jwtUtil
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectURI = redirectURI;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public void redirectToNaver(final HttpServletResponse response) throws IOException {
        String redirectUrl = UriComponentsBuilder
                .fromUriString(authorizationURI)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectURI)
                .queryParam("state", "naver_login_state_1234") // 네이버는 보안상 state 값이 필수야!
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }

    public String getAccessTokenFromNaver(final String code) {
        String targetUri = UriComponentsBuilder
                .fromUriString(tokenURI)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("state", "naver_login_state_1234")
                .build()
                .toUriString();

        NaverTokenResponseDTO naverTokenResponseDto = null;
        try {
            naverTokenResponseDto = WebClient.create()
                    .post()
                    .uri(targetUri)
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

    public NaverUserInfoResponseDTO getUserInfo(final String accessToken) {
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
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        }

        return userInfo;
    }

    @Transactional
    public JwtDTO processNaverLoginOrSignup(final NaverUserInfoResponseDTO userInfo) {
        String email = userInfo.response().email();
        String nickname = userInfo.response().name();

        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .email(email)
                            .name(nickname)
                            .password("")
                            .role(Role.ROLE_USER)
                            .build();
                    return memberRepository.save(newMember);
                });

        CustomUserDetails userDetails = new CustomUserDetails(
                member.getEmail(),
                member.getPassword(),
                member.getRole()
        );

        String accessToken = jwtUtil.createJwtAccessToken(userDetails);
        String refreshToken = jwtUtil.createJwtRefreshToken(userDetails);

        return JwtDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}