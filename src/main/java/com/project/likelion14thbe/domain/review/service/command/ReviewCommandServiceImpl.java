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
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public String createReview(CustomUserDetails customUserDetails, Long productId, ReviewReqDTO.ReviewCreateReq reviewCreateReq) {

        Member member = memberRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (!reviewRepository.existsByMemberAndProduct(member, product)) {
            Review review = ReviewConverter.toReview(reviewCreateReq, member, product);
            reviewRepository.save(review);
        }
        else throw new ReviewException(ReviewErrorCode.REVIEW_DUPLICATE);

        return "리뷰 생성 완료";
    }

    @Override
    public void updateReview(CustomUserDetails customUserDetails, Long reviewId, ReviewReqDTO.ReviewUpdateReq reviewUpdateReq){
        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        Member member = memberRepository.findByEmail(customUserDetails.getUsername())
                        .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (review.getMember().getId().equals(member.getId())) {
            review.updateReview(reviewUpdateReq);
        }
        else throw new ReviewException(ReviewErrorCode.REVIEW_UNAUTHORIZED);
    }

    @Override
    public void deleteReview(Long reviewId){

        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        review.deleteReview();
    }
}
