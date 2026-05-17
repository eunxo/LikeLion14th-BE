package com.project.likelion14thbe.domain.product.converter;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    public static Product toProduct(ProductReqDTO.CreateProductReq productReqDTO, Member member) {
        return Product.builder()
                .name(productReqDTO.name())
                .description(productReqDTO.description())
                .price(productReqDTO.price())
                .category(productReqDTO.category())
                .imageUrl(productReqDTO.productImage())
                .quantity(productReqDTO.quantity())
                .member(member)
                .build();
    }

    public static ProductResDTO.ProductDetailRes toProductDetailRes(Product product) {
        return ProductResDTO.ProductDetailRes.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .rating(0.0) // 우선 0으로 하드코딩
                .reviewCount(0L) // 우선 0으로 하드코딩
                .description(product.getDescription())
                .productImage(product.getImageUrl())
                .build();
    }
}