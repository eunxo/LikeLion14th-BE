package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewResDTO.ReviewListRes> getReviews(Long productId) {
        // 리뷰 목록 조회 로직 (엔티티 필드에 맞게 매핑)
        return reviewRepository.findAll().stream() // 특정 상품용 쿼리 메서드가 필요할 수 있습니다.
                .map(review -> ReviewResDTO.ReviewListRes.builder()
                        .reviewId(review.getReviewId())
                        .title(review.getTitle())
                        .rating(review.getRating())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResDTO.ReviewDetailRes getReviewDetail(Long productId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

        return ReviewResDTO.ReviewDetailRes.builder()
                .reviewId(review.getReviewId())
                .title("제목 없음")
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

}