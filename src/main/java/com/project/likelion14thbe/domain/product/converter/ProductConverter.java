package com.project.likelion14thbe.domain.product.converter;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    public static Product toProduct(ProductReqDTO.ProductCreateReq productCreateReq, Member member) {
        return Product.builder()
                .productName(productCreateReq.name())
                .productPrice(productCreateReq.price())
                .productQuantity(productCreateReq.quantity())
                .productDescription(productCreateReq.description())
                .member(member)
                .build();
    }

    public static ProductResDTO.ProductCreateRes toProductCreateRes(Product product) {
        return ProductResDTO.ProductCreateRes.builder()
                .id(product.getId())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public static ProductResDTO.ProductGetRes.ProductInfo toProductInfo(Product product) {
        return ProductResDTO.ProductGetRes.ProductInfo.builder()
                .productId(product.getId())
                .name(product.getProductName())
                .price(product.getProductPrice())
                .build();
    }

    public static ProductResDTO.ProductGetRes toProductGetRes(List<ProductResDTO.ProductGetRes.ProductInfo> productInfos) {
        return ProductResDTO.ProductGetRes.builder()
                .datalist(productInfos)
                .build();
    }

    public static ProductResDTO.ProductGetDetailRes toProductGetDetailRes(Product product) {
        return ProductResDTO.ProductGetDetailRes.builder()
                .productId(product.getId())
                .name(product.getProductName())
                .price(product.getProductPrice())
                .rating(product.getProductRatingAverage() != null ? product.getProductRatingAverage().floatValue() : 0f)
                .description(product.getProductDescription())
                .build();
    }
}
