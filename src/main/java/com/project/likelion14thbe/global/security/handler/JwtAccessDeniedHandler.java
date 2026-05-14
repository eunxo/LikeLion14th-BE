package com.project.likelion14thbe.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.likelion14thbe.domain.auth.exception.AuthErrorCode;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        CustomResponse<Object> errorResponse = CustomResponse.onFailure(
                AuthErrorCode.FORBIDDEN.getCode(),
                AuthErrorCode.FORBIDDEN.getMessage(),
                null
        );
        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
