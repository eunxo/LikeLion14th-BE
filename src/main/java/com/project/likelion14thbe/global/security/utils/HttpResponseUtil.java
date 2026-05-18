package com.project.likelion14thbe.global.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.apiPayload.code.BaseErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * 필터·시큐리티 핸들러 등 컨트롤러 밖에서 표준 CustomResponse 에러 응답을 직접 내려준다.
 *
 * <p>그동안 JwtAuthorizationFilter 는 토큰 에러를 plain text 로 써서 다른 401
 * (무토큰=AUTH401_4 JSON)과 응답 형식이 달랐다. 이 유틸로 모든 인증/인가 에러를
 * {isSuccess, code, message, result} envelope 로 통일한다. (팀장 Capstone-BackEnd
 * global.security.utils.HttpResponseUtil 패턴 참고)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setErrorResponse(
            HttpServletResponse response,
            BaseErrorCode errorCode
    ) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        CustomResponse<Object> body = CustomResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage(),
                null
        );
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
