package com.project.likelion14thbe.domain.order.repository;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.entity.Order; // Order로 변경
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMember(Member memeber);

    @Query("SELECT o FROM Order o WHERE o.member.id = :memberId AND o.deletedAt IS NULL")
    List<Order> findAllByMemberIdAndNotDeleted(@Param("memberId") Long memberId);

    // 삭제되지 않은 단건 주문 조회
    @Query("SELECT o FROM Order o WHERE o.id = :id AND o.deletedAt IS NULL")
    Optional<Order> findByIdAndNotDeleted(@Param("id") Long id);
}