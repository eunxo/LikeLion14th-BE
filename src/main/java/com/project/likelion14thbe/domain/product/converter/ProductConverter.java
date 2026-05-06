package com.project.likelion14thbe.domain.product.converter;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    public static Product toProduct(ProductReqDTO.CreateProductReq productReqDTO) {
        return Product.builder()
                .name(productReqDTO.name())
                .description(productReqDTO.description())
                .price(productReqDTO.price())
                .category(productReqDTO.category())
                .imageUrl(productReqDTO.productImage())
                .quantity(productReqDTO.quantity())
                .build();
    }
}