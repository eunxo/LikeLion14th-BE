package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.converter.ReviewConverter;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
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
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(ReviewConverter::toReviewListRes)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResDTO.ReviewDetailRes getReviewDetail(Long productId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

        return ReviewConverter.toReviewDetailRes(review);
    }

    @Override
    public List<ReviewResDTO.ReviewListRes> getMyReviews(Long memberId) {
        List<Review> reviews = reviewRepository.findByMemberId(memberId);
        return reviews.stream()
                .map(ReviewConverter::toReviewListRes)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResDTO.ReviewListRes> getReviewsByProduct(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(ReviewConverter::toReviewListRes)
                .collect(Collectors.toList());
    }


}