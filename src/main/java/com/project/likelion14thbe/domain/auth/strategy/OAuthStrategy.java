package com.project.likelion14thbe.domain.auth.strategy;

import com.project.likelion14thbe.domain.auth.dto.oauth.OAuthUserInfo;
import com.project.likelion14thbe.domain.auth.enums.Provider;

public interface OAuthStrategy {

    // 이 전략이 처리하는 Provider
    Provider getProvider();

    // 인가 코드 요청 URL 생성 (state는 CSRF 방지용)
    String buildAuthorizationUrl(String state);

    // 인가 코드로 액세스 토큰 교환. 네이버는 state를 토큰 요청에 동봉, 카카오는 무시
    String exchangeCodeForToken(String code, String state);

    // 액세스 토큰으로 사용자 정보 조회 후 중립 모델로 변환
    OAuthUserInfo fetchUserInfo(String accessToken);
}
