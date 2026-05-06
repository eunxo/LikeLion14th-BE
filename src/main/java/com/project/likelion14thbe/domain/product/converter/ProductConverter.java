package com.project.likelion14thbe.domain.product.converter;


import com.project.likelion14thbe.domain.category.entity.Category;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    public static Product toProduct(ProductReqDTO.ProductCreateReq request, Category category, Member seller) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock() == null ? 0 : request.getStock())
                .category(category)
                .seller(seller)
                .build();
    }

    public static ProductResDTO.ProductSummaryRes toSummary(Product product) {
        return ProductResDTO.ProductSummaryRes.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(null)
                .build();
    }

    public static ProductResDTO.ProductCreateResult toCreateResult(Product product) {
        return ProductResDTO.ProductCreateResult.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .build();
    }

    public static ProductResDTO.ProductDetailResult toDetailResult(Product product) {
        return ProductResDTO.ProductDetailResult.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(null)
                .isLiked(false)
                .build();
    }
}
