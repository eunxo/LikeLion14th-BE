package com.project.likelion14thbe.domain.auth.service;

import com.project.likelion14thbe.domain.auth.dto.response.NaverTokenResponseDTO;
import com.project.likelion14thbe.domain.auth.dto.response.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.auth.exception.AuthErrorCode;
import com.project.likelion14thbe.domain.auth.exception.AuthException;
import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import com.project.likelion14thbe.domain.member.service.command.MemberCommandServiceImpl;
import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
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

@Slf4j
@Service
public class NaverService {

    private final MemberRepository memberRepository;
    private final MemberCommandService memberCommandService ;
    private final JwtUtil jwtUtil;

    // 이 값은 항상 고정이므로 환경변수화 하지 않고, yml 파일에 저장하지 않음
    private static final String authorizationURI = "https://nid.naver.com/oauth2.0/authorize";
    private static final String tokenURI = "https://nid.naver.com/oauth2.0/token";
    private static final String userInfoURI = "https://openapi.naver.com/v1/nid/me";

    // 실행 환경 마다 변경되는 값 또는 민감한 값이므로 yml에 환경 변수로 저장
    private final String clientId;
    private final String clientSecret;
    private final String redirectURI;

    // 생성자 주입 방식을 사용하는 이유는 clientId, clientSecret, redirectURI를 불변(final)하게 하기 위해서는 생성자 주입을 해야 함
    public NaverService(
            @Value("${spring.naver.client.id}") String clientId,
            @Value("${spring.naver.client.secret}") String clientSecret,
            @Value("${spring.naver.uri.redirect}") String redirectURI,
            MemberRepository memberRepository,
            MemberCommandService memberCommandService,
            JwtUtil jwtUtil
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectURI = redirectURI;
        this.memberRepository = memberRepository;
        this.memberCommandService = memberCommandService;
        this.jwtUtil = jwtUtil;
    }

    public void redirectToNaver(HttpServletResponse response) throws IOException {

        // https://kauth.naver.com/oauth/authorize
        // ?response_type=code
        // &client_id=${REST_API_KEY}
        // &redirect_uri=${REDIRECT_URI}
        // 를 만드는 코드
        String redirectUrl = UriComponentsBuilder //UriComponentsBuilder로 URL 조립
                .fromUriString(authorizationURI)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectURI)
                .build()
                .toUriString();

        // code 발급을 위한 redirect
        response.sendRedirect(redirectUrl); // 사용자를 네이버로 보냄
    }

    public String getAccessTokenFromNaver(String code) {

        // 실제 서비스에서는 절대 사용해서는 안됨(값 노출) -> 로컬 개발 시 디버깅 용도에만 사용
        log.info("[NaverService] tokenURI={}", tokenURI);
        log.info("[NaverService] redirectURI={}", redirectURI);
        log.info("[NaverService] clientId={}", clientId);
        log.info("[NaverService] code={}", code);

        NaverTokenResponseDTO naverTokenResponseDTO = null;
        try {
            // WebClient 요청으로 카카오에서 액세스 토큰을 받아오기
            naverTokenResponseDTO = WebClient.create()
                    // post 요청
                    .post()
                    // tokenURI로
                    .uri(tokenURI)
                    // 요청 헤더의 Content-Type을 application/x-www-form-urlencoded로 설정
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    // requestBody에 담을 값들
                    .body(
                            BodyInserters.fromFormData("grant_type", "authorization_code")
                                    .with("client_id", clientId)
                                    .with("client_secret", clientSecret)
                                    .with("redirect_uri", redirectURI)
                                    .with("code", code)
                    )
                    .retrieve()
                    // 네이버 서버가 보내준 정보를 NaverTokenResponseDTO 형태로 역직렬화 해서 받겠다
                    // 그래서 반환 값을 아는 것이 중요하다
                    .bodyToMono(NaverTokenResponseDTO.class)
                    // 동기 방식
                    .block();
            // 예외 처리
        } catch (WebClientRequestException e) {
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }
        // naverTokenResponseDto가 null이거나, naverTokenResponseDto의 accessToken이 null일 때
        if (naverTokenResponseDTO == null || naverTokenResponseDTO.accessToken() == null) {
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        }

        // 실제 서비스에서는 절대 사용해서는 안됨(값 노출) -> 로컬 개발 시 디버깅 용도에만 사용
        log.info("[Naver Service] Access Token ------> {}", naverTokenResponseDTO.accessToken());
        log.info("[Naver Service] Refresh Token ------> {}", naverTokenResponseDTO.refreshToken());
        log.info("[Naver Service] Id Token ------> {}", naverTokenResponseDTO.idToken());
        log.info("[Naver Service] Scope ------> {}", naverTokenResponseDTO.scope());

        // access token을 반환
        return naverTokenResponseDTO.accessToken();
    }

    public NaverUserInfoResponseDTO getUserInfo(String accessToken) {

        NaverUserInfoResponseDTO userInfo = null;
        try {
            // 네이버에서 받은 access token으로 네이버 유저 프로필 조회
            userInfo = WebClient.create()
                    // 조회이므로 get 요청
                    .get()
                    // userInfoURI로
                    .uri(userInfoURI)
                    // request header에 네이버에서 받은 access token을 포함
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    // 네이버 서버가 보내준 정보를 NaverUserInfoResponseDTO 형태로 역직렬화 해서 받겠다
                    // 그래서 반환 값을 아는 것이 중요하다
                    .bodyToMono(NaverUserInfoResponseDTO.class)
                    // 동기 방식
                    .block();
        } catch (WebClientRequestException e) {
            throw new AuthException(AuthErrorCode.NAVER_USER_INFO_REQUEST_FAILED);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_OAUTH_REQUEST);
        }

        // NaverUserInfoResponseDTO가 null인 경우
        if (userInfo == null) {
            throw new AuthException(AuthErrorCode.NAVER_TOKEN_REQUEST_FAILED);
        }

        log.info("[Naver Service] userInfo ------> {}", userInfo);

        // 실제 서비스에서는 절대 사용해서는 안됨(값 노출) -> 로컬 개발 시 디버깅 용도에만 사용
        if (userInfo != null && userInfo.response() != null) {
            log.info("[Naver Service] Auth ID ---> {}", userInfo.response().id());
            log.info("[Naver Service] NickName ---> {}", userInfo.response().nickname());
            log.info("[Naver Service] ProfileImageUrl ---> {}", userInfo.response().profileImage());
        }

        return userInfo;
    }
    public JwtDTO check(NaverUserInfoResponseDTO userInfo){
        Optional<Member> member = memberRepository.findByEmailAndNotDeleted(userInfo.response().email());

        // signup
        if (member.isEmpty()){
            Member naverMember = MemberConverter.toNaverMember(userInfo);
            member = Optional.of(memberRepository.save(naverMember));
        }

        Member naverMember = member.get();

        // 커스텀 유저 디테일
        CustomUserDetails customUserDetails = new CustomUserDetails(naverMember.getEmail(), null, Role.ROLE_USER);

        // 커스텀 유저 디테일로 토큰 제작
        String accessToken = jwtUtil.createJwtAccessToken(customUserDetails);
        String refreshToken = jwtUtil.createJwtRefreshToken(customUserDetails);

        // 토큰 resDTO 반환
        return JwtDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }
}