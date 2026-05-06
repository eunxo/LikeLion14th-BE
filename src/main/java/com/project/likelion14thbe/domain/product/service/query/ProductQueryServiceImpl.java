package com.project.likelion14thbe.domain.product.service.query;

import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 조회 전용 트랜잭션
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDTO.ProductDetailResDto getProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        return ProductResDTO.ProductDetailResDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getPhotoImg())
                .description(product.getDescription())
                .build();
    }

    @Override
    public List<ProductResDTO.ProductPreviewResDto> getProductList() {
        return productRepository.findAll().stream()
                .map(item -> ProductResDTO.ProductPreviewResDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .price(Long.valueOf(item.getPrice()))
                        .photoImg(item.getPhotoImg())
                        .stock(item.getStock())
                        .build())
                .collect(Collectors.toList());
    }
}