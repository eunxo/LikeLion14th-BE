package com.project.likelion14thbe.domain.product.converter;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//파라미터를 필요로 하지 않는 생성자를 추가해야함
//멤버컨버터 안은 다 static이기때문에 접근을 허용하지 않음
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    // 1. DTO -> Entity 변환: 상품 생성
    public static Product toProduct(ProductReqDTO.ProductCreateReqDto request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .description(request.getDescription())
                .build();
    }

    // 2. Entity -> Response DTO 변환: 생성 응답
    public static ProductResDTO.ProductCreateResDto toProductCreateResDTO(Product product) {
        return ProductResDTO.ProductCreateResDto.builder()
                .id(product.getId())
                .createdAt(product.getCreatedAt())
                .build();
    }

    // 3. Entity -> Response DTO 변환: 상품 목록 미리보기
    public static ProductResDTO.ProductPreviewResDto toProductPreviewResDTO(Product product) {
        return ProductResDTO.ProductPreviewResDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(Long.valueOf(product.getPrice()))
                .photoImg(product.getImageUrl())
                .stock(product.getStock())
                .build();
    }

    // 4. Entity -> Response DTO 변환: 상품 상세 정보
    public static ProductResDTO.ProductDetailResDto toProductDetailResDTO(Product product) {
        return ProductResDTO.ProductDetailResDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(String.valueOf(product.getCategory())) // 엔티티의 category 값 연결
                .imageUrl(product.getImageUrl())
                .photoImg(product.getImageUrl())
                .stock(product.getStock())
                .build();
    }
}
