package com.project.likelion14thbe.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.likelion14thbe.domain.auth.exception.AuthErrorCode;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        CustomResponse<Object> errorResponse = CustomResponse.onFailure(
                AuthErrorCode.UNAUTHORIZED.getCode(),
                AuthErrorCode.UNAUTHORIZED.getMessage(),
                null
        );
        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
