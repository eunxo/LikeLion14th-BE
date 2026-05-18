package com.project.likelion14thbe.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.likelion14thbe.domain.auth.dto.response.JwtDTO;
import com.project.likelion14thbe.domain.auth.exception.AuthErrorCode;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws AuthenticationException {

        log.info("[ Login Filter ]  로그인 시도 : Custom Login Filter 작동 ");
        ObjectMapper objectMapper = new ObjectMapper();
        MemberReqDTO.LoginReqDTO requestBody;
        try {
            requestBody = objectMapper.readValue(request.getInputStream(), MemberReqDTO.LoginReqDTO.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Request Body 파싱 중 오류");
        }

        String email = requestBody.email();
        String password = requestBody.password();
        log.info("[ Login Filter ]  Email ---> {} ", email);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain,
            @NonNull Authentication authentication
    ) throws IOException {

        log.info("[ Login Filter ] 로그인에 성공 하였습니다.");

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        JwtDTO jwtDto = JwtDTO.builder()
                .accessToken(jwtUtil.createJwtAccessToken(customUserDetails))
                .refreshToken(jwtUtil.createJwtRefreshToken(customUserDetails))
                .build();

        CustomResponse<JwtDTO> responseBody = CustomResponse.onSuccess(jwtDto);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    @Override
    protected void unsuccessfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException failed
    ) throws IOException {

        log.info("[ Login Filter ] 로그인에 실패하였습니다. ({}) ", failed.getClass().getSimpleName());

        AuthErrorCode errorCode;

        if (failed instanceof BadCredentialsException) {
            errorCode = AuthErrorCode.WRONG_CREDENTIALS;
        } else if (failed instanceof LockedException || failed instanceof DisabledException) {
            errorCode = AuthErrorCode.FORBIDDEN;
        } else if (failed instanceof UsernameNotFoundException) {
            errorCode = AuthErrorCode.MEMBER_NOT_FOUND_AUTH;
        } else if (failed instanceof AuthenticationServiceException) {
            errorCode = AuthErrorCode.BODY_PARSE_ERROR;
        } else {
            errorCode = AuthErrorCode.UNAUTHORIZED;
        }

        CustomResponse<Object> responseBody = CustomResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage(),
                null
        );

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
