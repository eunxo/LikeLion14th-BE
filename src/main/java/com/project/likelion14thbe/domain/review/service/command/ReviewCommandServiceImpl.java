package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.exception.ProductErrorCode;
import com.project.likelion14thbe.domain.product.exception.ProductException;
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
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createReview(final Long productId, final String email, final ReviewReqDTO.ReviewCreateReq req) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        reviewRepository.save(ReviewConverter.toReview(req, member, product));
    }

    @Override
    public void updateReview(final Long reviewId, final String email, final ReviewReqDTO.ReviewUpdateReq req) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        if (!review.getMember().getEmail().equals(email)) {
            throw new MemberException(MemberErrorCode.MEMBER_FORBIDDEN);
        }

        review.update(req.getContent(), req.getRating());
    }

    @Override
    public void deleteReview(final Long reviewId, final String email) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        if (!review.getMember().getEmail().equals(email)) {
            throw new MemberException(MemberErrorCode.MEMBER_FORBIDDEN);
        }

        reviewRepository.delete(review);
    }
}