package com.project.likelion14thbe.domain.product.service.query;

import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
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
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

        return ProductConverter.toProductDetailResDTO(product);
    }

    @Override
    public List<ProductResDTO.ProductPreviewResDto> getProductList() {
        return productRepository.findAllByDeletedAtIsNull().stream()
                .map(ProductConverter::toProductPreviewResDTO)
                .collect(Collectors.toList());
    }
}