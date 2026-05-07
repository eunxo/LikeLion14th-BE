package com.project.likelion14thbe.domain.review.repository;

import com.project.likelion14thbe.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByProduct_Id(Long productId, Pageable pageable);

    Page<Review> findAllByMember_IdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

    boolean existsByMember_IdAndProduct_Id(Long memberId, Long productId);

    boolean existsByMemberId(Long memberId);
}
