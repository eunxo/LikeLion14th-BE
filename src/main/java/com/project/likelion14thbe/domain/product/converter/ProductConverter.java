package com.project.likelion14thbe.domain.product.converter;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    public static Product toProduct(ProductReqDTO.CreateProductReqDTO request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .category(request.category())
                .description(request.description())
                .build();
    }

    public static ProductResDTO.CreateProductResDTO toCreateProductResDTO(Product product) {
        return new ProductResDTO.CreateProductResDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCreatedAt()
        );
    }

    public static ProductResDTO.ProductDetailResDTO toProductDetailResDTO(Product product) {
        return new ProductResDTO.ProductDetailResDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getDescription(),
                product.getThumbnailUrl(),
                product.getCreatedAt()
        );
    }

    public static ProductResDTO.ProductItemDTO toProductItemDTO(Product product) {
        return new ProductResDTO.ProductItemDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getThumbnailUrl()
        );
    }

    public static ProductResDTO.ProductListResDTO toProductListResDTO(Page<Product> page) {
        List<ProductResDTO.ProductItemDTO> productList = page.getContent().stream()
                .map(ProductConverter::toProductItemDTO)
                .toList();
        return new ProductResDTO.ProductListResDTO(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isLast(),
                productList
        );
    }
}
