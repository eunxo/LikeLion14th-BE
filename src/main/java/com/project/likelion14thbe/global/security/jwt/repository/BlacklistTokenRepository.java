package com.project.likelion14thbe.global.security.jwt.repository;

import com.project.likelion14thbe.global.security.jwt.entity.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long> {

    boolean existsByToken(String token);  //토큰이 있는지 확인

    void deleteByExpiredAt(LocalDateTime now);  // 만료시간보다 전이면 삭제
}
