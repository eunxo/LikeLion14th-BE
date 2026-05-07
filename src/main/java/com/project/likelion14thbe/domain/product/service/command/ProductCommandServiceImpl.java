package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDTO.ProductCreateResDto createProduct(ProductReqDTO.ProductCreateReqDto request) {
        // 클라이언트가 보낸 DTO를 Product 엔티티로 변환
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .photoImg(request.getImageUrl())
                .description(request.getDescription())
                .build();

        Product savedProduct = productRepository.save(product);

        // 생성 응답 DTO 반환
        return ProductResDTO.ProductCreateResDto.builder()
                .id(savedProduct.getId())
                .createdAt(savedProduct.getCreatedAt())
                .build();
    }

    @Override
    public void deleteProduct(Long id) {
        Product pro = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));
        productRepository.delete(pro);
    }
}