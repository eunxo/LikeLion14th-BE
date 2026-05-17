package com.project.likelion14thbe.domain.order.repository;

import com.project.likelion14thbe.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberEmail(String email);

    @Query("SELECT m FROM Order m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Order> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.product po " +
            "WHERE o.member.id = :memberId " +
            "AND po.product.id = :productId " +
            "AND o.status = :status")
    boolean existsByMemberIdAndProductIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("productId") Long productId,
            @Param("status") String status
    );
}
