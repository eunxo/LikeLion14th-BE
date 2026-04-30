package com.project.likelion14thbe.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // http://localhost:8081/swagger-ui/index.html#/

    @Bean
    public OpenAPI LikeLionAPI() {
        Info info = new Info()
                .title("LikeLion API") // API 제목
                .description("LikeLion API 명세서 입니다.") // 설명
                .version("1.0.0"); //버전

        String jwtSchemeName = "JWT TOKEN";

        // JWT Bearer 인증 스키마 등록 (전역 요구는 걸지 않음 — 인증 필요 API에 @SecurityRequirement 명시)
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info)
                .components(components);
    }
}
