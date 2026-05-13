package com.project.likelion14thbe.domain.member.scheduler;

import com.project.likelion14thbe.domain.member.service.command.MemberCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberCleanupScheduler {

    private final MemberCommandService memberCommandService;

    // Every day at 03:00, physically deletes members soft-deleted over 30 days ago.
    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupExpiredDeletedMembers() {
        long deletedCount = memberCommandService.purgeExpiredDeletedMembers();
        log.info("Member cleanup scheduler completed. physicallyDeletedCount={}", deletedCount);
    }
}
