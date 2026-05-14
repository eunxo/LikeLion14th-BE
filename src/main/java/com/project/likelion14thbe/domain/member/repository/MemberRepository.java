package com.project.likelion14thbe.domain.member.repository;

import com.project.likelion14thbe.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    // 삭제되지 않은 활성 회원 조회
    @Query("SELECT m FROM Member m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Member> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.deletedAt IS NULL")
    Optional<Member> findByEmailAndNotDeleted(@Param("email") String email);

    // 삭제된 회원 포함 조회 (스케줄러용)
    @Query("SELECT m FROM Member m WHERE m.deletedAt IS NOT NULL AND m.deletedAt < :threshold")
    List<Member> findAllDeletedBefore(@Param("threshold") LocalDateTime threshold);
}
