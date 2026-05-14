package com.project.likelion14thbe.domain.member.scheduler;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MemberCleanupScheduler {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void cleanupDeletedMembers() {
        log.info("Starting scheduled cleanup of deleted members...");

        // 한 달 전 날짜 계산
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        // 한 달 전 이전에 소프트 딜리트된 회원 조회
        List<Member> membersToDelete = memberRepository.findDeletedMembersBefore(oneMonthAgo);

        if (membersToDelete.isEmpty()) {
            log.info("No members to delete.");
        }

        for (Member member : membersToDelete) {
            memberRepository.delete(member);
        }
    }
}
