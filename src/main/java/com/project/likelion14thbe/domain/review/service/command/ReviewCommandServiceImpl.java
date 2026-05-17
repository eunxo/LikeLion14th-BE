package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.exception.ProductErrorCode;
import com.project.likelion14thbe.domain.product.exception.ProductException;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import com.project.likelion14thbe.domain.review.converter.ReviewConverter;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.review.exception.ReviewErrorCode;
import com.project.likelion14thbe.domain.review.exception.ReviewException;
import com.project.likelion14thbe.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public ReviewResDTO.ReviewCreateRes createReview(ReviewReqDTO.ReviewCreateReq reviewCreateReq, Long productId, String email) {

        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findByAndNotDeleted(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Review review = ReviewConverter.toReview(reviewCreateReq, member, product);

        reviewRepository.save(review);

        return ReviewConverter.toReviewCreateRes(review);
    }

    @Override
    public void updateReview(Long reviewId, String email, ReviewReqDTO.ReviewChangeReq dto){
        // 리뷰 정보 조회
        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        //  리뷰 접근 권한 확인
        if (!review.getMember().getEmail().equals(email)) {
            throw new OrderException(OrderErrorCode.ORDER_FORBIDDEN);
        }

        review.updatedReview(dto.reviewContent(), dto.reviewRating());
    }

    @Override
    public void deleteReview(Long reviewId, String email){
        // 리뷰 정보 조회
        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        // 리뷰 접근 권한 확인
        if (!review.getMember().getEmail().equals(email)) {
            throw new OrderException(OrderErrorCode.ORDER_FORBIDDEN);
        }

        //soft delete 처리
        review.delete();
    }
}
