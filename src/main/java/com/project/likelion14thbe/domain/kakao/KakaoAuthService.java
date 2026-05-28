package com.project.likelion14thbe.domain.kakao;

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
public class KakaoAuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * case1: DB에 회원이 있으면 로그인
     */
    public JwtDTO loginOrRegister(KakaoUserInfoResponseDTO userInfo) {
        String email = resolveEmail(userInfo);
        String name = resolveName(userInfo);

        Member member = memberRepository.findByEmail(email)
                .filter(m -> m.getDeletedAt() == null)
                .orElseGet(() -> {
                    log.info("[ KakaoAuthService ] 신규 카카오 회원 가입: {}", email);
                    Member newMember = MemberConverter.toSocialMember(
                            email,
                            name,
                            passwordEncoder.encode(UUID.randomUUID().toString())
                    );
                    return memberRepository.save(newMember);
                });

        log.info("[ KakaoAuthService ] 카카오 로그인 성공: {}", member.getEmail());
        return issueToken(member);
    }




    /**   case2: 없으면 회원가입 후 로그인 (기본 로그인과 동일한 JwtDTO 반환)*/

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

    private String resolveEmail(KakaoUserInfoResponseDTO userInfo) {
        if (userInfo.kakaoAccount() != null
                && userInfo.kakaoAccount().email() != null
                && !userInfo.kakaoAccount().email().isBlank()) {
            return userInfo.kakaoAccount().email();
        }
        return "kakao_" + userInfo.id() + "@kakao.user";
    }

    private String resolveName(KakaoUserInfoResponseDTO userInfo) {
        if (userInfo.kakaoAccount() != null && userInfo.kakaoAccount().profile() != null) {
            String nickName = userInfo.kakaoAccount().profile().nickName();
            if (nickName != null && !nickName.isBlank()) {
                return nickName;
            }
        }
        if (userInfo.kakaoAccount() != null
                && userInfo.kakaoAccount().name() != null
                && !userInfo.kakaoAccount().name().isBlank()) {
            return userInfo.kakaoAccount().name();
        }
        return "카카오사용자";
    }
}
