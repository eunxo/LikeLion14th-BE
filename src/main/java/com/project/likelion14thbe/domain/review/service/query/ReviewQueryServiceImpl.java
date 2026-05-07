package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.converter.ReviewConverter;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResDTO.ReviewDetailRes getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("해당 리뷰를 찾을 수 없습니다."));
        return ReviewConverter.toReviewDetailRes(review);
    }

    @Override
    public ReviewResDTO.ReviewListRes getReviewsByProduct(Long productId) {
        return ReviewConverter.toReviewListRes(reviewRepository.findAllByProductId(productId));
    }
}