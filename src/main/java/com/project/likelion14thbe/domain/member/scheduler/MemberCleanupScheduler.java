package com.project.likelion14thbe.domain.member.scheduler;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberCleanupScheduler {

    private static final int RETENTION_DAYS = 30;

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 탈퇴 후 30일이 지난 회원 Hard Delete
     * - 매일 새벽 3시에 실행
     * - 자식 데이터(Order/Review)가 있는 회원은 FK 보호로 skip
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupDeletedMembers() {
        log.info("탈퇴 회원 정리 스케줄러 시작");

        LocalDateTime threshold = LocalDateTime.now().minusDays(RETENTION_DAYS);
        List<Member> expiredMembers = memberRepository.findAllDeletedBefore(threshold);

        if (expiredMembers.isEmpty()) {
            log.info("정리 대상 탈퇴 회원이 없습니다.");
            return;
        }

        log.info("정리 대상 탈퇴 회원 수: {}", expiredMembers.size());

        int deletedCount = 0;
        int skippedCount = 0;
        for (Member member : expiredMembers) {
            // FK 보호 — 자식 Order/Review 있으면 skip (예외 기반보다 명시적 체크)
            if (orderRepository.existsByMemberId(member.getId())
                    || reviewRepository.existsByMemberId(member.getId())) {
                log.warn("FK 보호로 skip: memberId={} (자식 Order/Review 존재)", member.getId());
                skippedCount++;
                continue;
            }
            try {
                deleteMemberHard(member.getId());
                deletedCount++;
                log.info("회원 Hard Delete 완료: memberId={}, deletedAt={}", member.getId(), member.getDeletedAt());
            } catch (DataIntegrityViolationException e) {
                log.warn("FK 위반으로 skip: memberId={}, error={}", member.getId(), e.getMessage());
                skippedCount++;
            }
        }

        log.info("탈퇴 회원 정리 스케줄러 완료: 삭제={}, skip={}", deletedCount, skippedCount);
    }

    @Transactional
    public void deleteMemberHard(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
