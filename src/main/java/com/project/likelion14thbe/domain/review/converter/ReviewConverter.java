package com.project.likelion14thbe.domain.review.converter;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewConverter {

    public static Review toReview(
            Member member,
            Product product,
            ReviewReqDTO.CreateReviewReqDTO request
    ) {
        return Review.builder()
                .member(member)
                .product(product)
                .rating(request.rating())
                .content(request.content())
                .build();
    }

    public static ReviewResDTO.CreateReviewResDTO toCreateReviewResDTO(Review review) {
        return new ReviewResDTO.CreateReviewResDTO(
                review.getId(),
                review.getProduct().getId(),
                review.getMember().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }

    public static ReviewResDTO.UpdateReviewResDTO toUpdateReviewResDTO(Review review) {
        return new ReviewResDTO.UpdateReviewResDTO(
                review.getId(),
                review.getMember().getName(),
                review.getRating(),
                review.getContent(),
                review.getUpdatedAt()
        );
    }

    public static ReviewResDTO.ReviewDetailResDTO toReviewDetailResDTO(Review review) {
        return new ReviewResDTO.ReviewDetailResDTO(
                review.getId(),
                review.getProduct().getId(),
                review.getMember().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }

    public static ReviewResDTO.ReviewItemDTO toReviewItemDTO(Review review) {
        return new ReviewResDTO.ReviewItemDTO(
                review.getId(),
                review.getMember().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }

    public static ReviewResDTO.ReviewListResDTO toReviewListResDTO(Long productId, Page<Review> page) {
        List<ReviewResDTO.ReviewItemDTO> reviewList = page.getContent().stream()
                .map(ReviewConverter::toReviewItemDTO)
                .toList();
        return new ReviewResDTO.ReviewListResDTO(
                productId,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isLast(),
                reviewList
        );
    }

    public static ReviewResDTO.MyReviewItemDTO toMyReviewItemDTO(Review review) {
        return new ReviewResDTO.MyReviewItemDTO(
                review.getId(),
                review.getProduct().getId(),
                review.getProduct().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }

    public static ReviewResDTO.MyReviewListResDTO toMyReviewListResDTO(Page<Review> page) {
        List<ReviewResDTO.MyReviewItemDTO> reviewList = page.getContent().stream()
                .map(ReviewConverter::toMyReviewItemDTO)
                .toList();
        return new ReviewResDTO.MyReviewListResDTO(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isLast(),
                reviewList
        );
    }
}
