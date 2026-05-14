package com.project.likelion14thbe.domain.review.repository;

import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(Long productId);

    @Query("SELECT m FROM Review m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Review> findByIdAndNotDeleted(@Param("id") Long id);
}
