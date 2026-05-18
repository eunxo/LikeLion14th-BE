package com.project.likelion14thbe.global.config;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@likelion.com";
    private static final String ADMIN_PASSWORD = "admin1234!";
    private static final String ADMIN_NAME = "관리자";

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (memberRepository.existsByEmailAndDeletedAtIsNull(ADMIN_EMAIL)) {
            log.info("ADMIN seed skip: {} already exists", ADMIN_EMAIL);
            return;
        }
        Member admin = Member.builder()
                .name(ADMIN_NAME)
                .email(ADMIN_EMAIL)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(Role.ROLE_ADMIN)
                .build();
        memberRepository.save(admin);
        log.info("ADMIN seed created: {}", ADMIN_EMAIL);
    }
}
