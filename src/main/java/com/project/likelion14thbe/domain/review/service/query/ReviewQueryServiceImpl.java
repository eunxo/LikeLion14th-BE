package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.converter.ReviewConverter;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.exception.ReviewErrorCode;
import com.project.likelion14thbe.domain.review.exception.ReviewException;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewResDTO.ReviewSummaryRes> getReviews(Long productId) {
        return reviewRepository.findAllByProductProductIdOrderByCreatedAtDesc(productId).stream()
                .map(ReviewConverter::toSummary)
                .toList();
    }

    @Override
    public ReviewResDTO.ReviewDetailResult getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        return ReviewConverter.toDetail(review);
    }
}
