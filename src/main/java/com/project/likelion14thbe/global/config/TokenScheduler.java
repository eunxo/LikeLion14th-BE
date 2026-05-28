package com.project.likelion14thbe.global.config;

import com.project.likelion14thbe.global.security.jwt.repository.BlacklistTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class TokenScheduler {
    private final BlacklistTokenRepository blacklistTokenRepository;
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredBlacklistTokens() {
        blacklistTokenRepository.deleteByExpiredAt(LocalDateTime.now());
        log.info("[ TokenScheduler ] 만료된 블랙리스트 토큰 삭제 완료");
    }
}