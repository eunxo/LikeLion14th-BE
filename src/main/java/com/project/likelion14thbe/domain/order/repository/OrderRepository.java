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

    List<Order> findAllByMemberId(Long memberId);

    @Query("SELECT m FROM Order m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Order> findByIdAndNotDeleted(@Param("id") Long id);
}
