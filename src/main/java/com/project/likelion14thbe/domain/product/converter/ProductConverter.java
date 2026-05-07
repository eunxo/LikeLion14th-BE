package com.project.likelion14thbe.domain.product.converter;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    public static Product toProduct(ProductReqDTO.CreateReq req) {
        return Product.builder()
                .name(req.getName())
                .price(req.getPrice())
                .category(req.getCategory())
                .imageUrl(req.getImageUrl())
                .description(req.getDescription())
                .build();
    }

    public static ProductResDTO.ProductItem toProductItem(Product product) {
        return ProductResDTO.ProductItem.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public static ProductResDTO.ListRes toListRes(List<Product> products) {
        return ProductResDTO.ListRes.builder()
                .products(products.stream()
                        .map(ProductConverter::toProductItem)
                        .collect(Collectors.toList()))
                .build();
    }
}