package com.project.likelion14thbe.domain.review.repository;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByMemberId(Long memberId);

    boolean existsByMemberAndProduct (Member member, Product product);

    // 삭제되지 않은 활성 리뷰 조회
    @Query("SELECT r FROM Review r WHERE r.id = :id AND r.deletedAt IS NULL")
    Optional<Review> findByIdAndNotDeleted(@Param("id") Long id);
}
