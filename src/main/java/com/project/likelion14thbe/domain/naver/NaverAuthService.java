package com.project.likelion14thbe.domain.naver;

import com.project.likelion14thbe.domain.member.converter.MemberConverter;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.global.security.jwt.JwtUtil;
import com.project.likelion14thbe.global.security.jwt.dto.JwtDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NaverAuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public JwtDTO loginOrRegister(NaverUserInfoResponseDTO userInfo) {
        String email = resolveEmail(userInfo);
        String name = resolveName(userInfo);

        Member member = memberRepository.findByEmail(email)
                .filter(m -> m.getDeletedAt() == null)
                .orElseGet(() -> {
                    log.info("[ NaverAuthService ] 신규 네이버 회원 가입: {}", email);
                    Member newMember = MemberConverter.toSocialMember(
                            email,
                            name,
                            passwordEncoder.encode(UUID.randomUUID().toString())
                    );
                    return memberRepository.save(newMember);
                });

        log.info("[ NaverAuthService ] 네이버 로그인 성공: {}", member.getEmail());
        return issueToken(member);
    }

    private JwtDTO issueToken(Member member) {
        Role role = member.getRole() != null ? member.getRole() : Role.ROLE_USER;

        CustomUserDetails userDetails = new CustomUserDetails(
                member.getEmail(),
                member.getPassword(),
                role
        );

        return JwtDTO.builder()
                .accessToken(jwtUtil.createJwtAccessToken(userDetails))
                .refreshToken(jwtUtil.createJwtRefreshToken(userDetails))
                .build();
    }

    private String resolveEmail(NaverUserInfoResponseDTO userInfo) {
        if (userInfo.response() != null
                && userInfo.response().email() != null
                && !userInfo.response().email().isBlank()) {
            return userInfo.response().email();
        }
        return "naver_" + userInfo.response().id() + "@naver.user";
    }

    private String resolveName(NaverUserInfoResponseDTO userInfo) {
        if (userInfo.response() != null
                && userInfo.response().name() != null
                && !userInfo.response().name().isBlank()) {
            return userInfo.response().name();
        }
        return "네이버사용자";
    }
}