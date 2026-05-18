package com.project.likelion14thbe.global.config;

import com.project.likelion14thbe.global.security.exception.JwtAccessDeniedHandler;
import com.project.likelion14thbe.global.security.exception.JwtAuthenticationEntryPoint;
import com.project.likelion14thbe.global.security.filter.CustomLoginFilter;
import com.project.likelion14thbe.global.security.filter.JwtAuthorizationFilter;
import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // 무조건 전면 공개 URL
    private final String[] allowUrl = {
            "/api/v1/auth/login",
            "/api/v1/auth/signup",
            "/api/v1/auth/kakao",
            "/api/v1/kakao/callback",
            "/api/v1/auth/naver",
            "/api/v1/naver/callback",
            "/auth/reissue",
            "/auth/**",
            "/api/usage",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    // "GET(조회)" 메서드만 로그인 없이 허용할 URL
    private final String[] allowGetUrl = {
            "/api/v1/home",
            "/api/v1/products",
            "/api/v1/products/*/reviews",
            "/api/v1/reviews/*"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CustomLoginFilter loginFilter = new CustomLoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/api/v1/auth/login");

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(allowUrl).permitAll() // 여기에 속한 주소는 전면 프리패스
                        .requestMatchers(HttpMethod.GET, allowGetUrl).permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
        ;

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}