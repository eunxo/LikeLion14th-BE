package com.project.likelion14thbe.domain.review.service.command;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import com.project.likelion14thbe.domain.review.converter.ReviewConverter;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.exception.ReviewErrorCode;
import com.project.likelion14thbe.domain.review.exception.ReviewException;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
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
    public void createReview(Long productId, Long memberId, ReviewReqDTO.ReviewCreateReq req) {
        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new RuntimeException("MEMBER_NOT_FOUND"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("PRODUCT_NOT_FOUND"));
        reviewRepository.save(ReviewConverter.toReview(req, member, product));
    }

    @Override
    public void updateReview(Long reviewId, Long memberId, ReviewReqDTO.ReviewUpdateReq req) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        if (!review.getMember().getId().equals(memberId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_FORBIDDEN);
        }
        review.update(req.getContent(), req.getRating());
    }

    @Override
    public void deleteReview(Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        if (!review.getMember().getId().equals(memberId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_FORBIDDEN);
        }
        reviewRepository.delete(review);
    }
}