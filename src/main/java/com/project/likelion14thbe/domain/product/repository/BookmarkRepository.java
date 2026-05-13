package com.project.likelion14thbe.domain.product.repository;

import com.project.likelion14thbe.domain.product.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}