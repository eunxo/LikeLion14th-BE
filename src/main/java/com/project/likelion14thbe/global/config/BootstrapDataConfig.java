package com.project.likelion14thbe.global.config;

import com.project.likelion14thbe.domain.category.entity.Category;
import com.project.likelion14thbe.domain.category.repository.CategoryRepository;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BootstrapDataConfig {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Bean
    public CommandLineRunner bootstrapData() {
        return args -> {
            if (memberRepository.findFirstByDeletedAtIsNullOrderByUserIdAsc().isEmpty()) {
                memberRepository.save(Member.builder()
                        .email("default@test.com")
                        .password("1234")
                        .name("기본회원")
                        .build());
            }
            if (categoryRepository.count() == 0) {
                categoryRepository.save(Category.builder()
                        .name("기본 카테고리")
                        .build());
            }
        };
    }
}
