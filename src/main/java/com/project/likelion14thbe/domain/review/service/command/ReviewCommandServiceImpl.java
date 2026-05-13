package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import com.project.likelion14thbe.domain.review.converter.ReviewConverter;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import com.project.likelion14thbe.domain.review.exception.ReviewErrorCode;
import com.project.likelion14thbe.domain.review.exception.ReviewException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public ReviewResDTO.CreateReviewResDTO createReview(
            Long memberId,
            Long productId,
            ReviewReqDTO.CreateReviewReqDTO request
    ) {
        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_MEMBER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_PRODUCT_NOT_FOUND));

        if (reviewRepository.existsByMember_IdAndProduct_Id(memberId, productId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_ALREADY_EXISTS);
        }

        Review review = ReviewConverter.toReview(member, product, request);
        Review saved = reviewRepository.save(review);
        return ReviewConverter.toCreateReviewResDTO(saved);
    }

    @Override
    public ReviewResDTO.UpdateReviewResDTO updateReview(
            Long memberId,
            Long productId,
            Long reviewId,
            ReviewReqDTO.UpdateReviewReqDTO request
    ) {
        // 작성자 회원 검증 (탈퇴 회원은 수정 불가)
        if (memberRepository.findByIdAndNotDeleted(memberId).isEmpty()) {
            throw new ReviewException(ReviewErrorCode.REVIEW_MEMBER_NOT_FOUND);
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        // URL의 productId와 리뷰의 productId 일치 검증
        if (!review.getProduct().getId().equals(productId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_NOT_BELONG_TO_PRODUCT);
        }

        // 본인 리뷰 검증 (시큐리티 미적용으로 임시)
        if (!review.getMember().getId().equals(memberId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_FORBIDDEN);
        }

        review.updateReview(request.rating(), request.content());
        return ReviewConverter.toUpdateReviewResDTO(review);
    }

    @Override
    public void deleteReview(Long memberId, Long productId, Long reviewId) {
        if (memberRepository.findByIdAndNotDeleted(memberId).isEmpty()) {
            throw new ReviewException(ReviewErrorCode.REVIEW_MEMBER_NOT_FOUND);
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        if (!review.getProduct().getId().equals(productId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_NOT_BELONG_TO_PRODUCT);
        }

        if (!review.getMember().getId().equals(memberId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_FORBIDDEN);
        }

        reviewRepository.delete(review);  // hard delete
    }
}
