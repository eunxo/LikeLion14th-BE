package com.project.likelion14thbe.domain.review.converter;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewConverter {

    public static Review toReview(ReviewReqDTO.ReviewCreateReq reviewCreateReq, Member member, Product product) {
        return Review.builder()
                .score(reviewCreateReq.rating())
                .content(reviewCreateReq.content())
                .member(member)
                .product(product)
                .build();
    }
}
