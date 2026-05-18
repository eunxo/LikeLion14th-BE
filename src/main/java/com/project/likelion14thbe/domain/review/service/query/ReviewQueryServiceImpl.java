package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import com.project.likelion14thbe.domain.review.converter.ReviewConverter;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import com.project.likelion14thbe.domain.review.exception.ReviewErrorCode;
import com.project.likelion14thbe.domain.review.exception.ReviewException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public ReviewResDTO.ReviewDetailResDTO getReview(Long productId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        if (!review.getProduct().getId().equals(productId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_NOT_BELONG_TO_PRODUCT);
        }
        return ReviewConverter.toReviewDetailResDTO(review);
    }

    @Override
    public ReviewResDTO.ReviewListResDTO getReviewList(Long productId, Integer page, Integer size, String sort) {
        if (!productRepository.existsById(productId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_PRODUCT_NOT_FOUND);
        }
        Sort sortOption = "rating".equals(sort)
                ? Sort.by(Sort.Direction.DESC, "rating")
                : Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sortOption);
        Page<Review> reviews = reviewRepository.findAllByProduct_Id(productId, pageable);
        return ReviewConverter.toReviewListResDTO(productId, reviews);
    }

    @Override
    public ReviewResDTO.MyReviewListResDTO getMyReviews(String email, Integer page, Integer size) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_MEMBER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findAllByMember_IdOrderByCreatedAtDesc(member.getId(), pageable);
        return ReviewConverter.toMyReviewListResDTO(reviews);
    }
}
