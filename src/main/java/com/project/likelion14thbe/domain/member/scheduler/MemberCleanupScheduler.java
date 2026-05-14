package com.project.likelion14thbe.domain.member.scheduler;

import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MemberCleanupScheduler {

    private final MemberRepository memberRepository;

    //새벽 3시 실행
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void cleanupOldDeletedMembers(){

        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        memberRepository.deleteByDeletedAtBefore(thirtyDaysAgo);

        System.out.println("탈퇴한지 30일이 경과한 회원의 데이터가 영구 삭제되었습니다.");
    }
}
