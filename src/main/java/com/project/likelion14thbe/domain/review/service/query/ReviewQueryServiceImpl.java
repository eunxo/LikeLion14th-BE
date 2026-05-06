package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.converter.ReviewConverter;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResDTO.ReviewDetailRes getReviewDetail(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        return ReviewConverter.toReviewDetailRes(review);
    }

    @Override
    public ReviewResDTO.ReviewGetRes getReviewsByProduct(Long productId) {

        List<Review> reviewList = reviewRepository.findAllByProductId(productId);

        List<ReviewResDTO.ReviewGetRes.ReviewInfo> reviewInfos = reviewList.stream()
                .map(ReviewConverter::toReviewInfo)
                .toList();

        return ReviewConverter.toReviewGetRes(reviewInfos);
    }
}
