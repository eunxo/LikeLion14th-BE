package com.project.likelion14thbe.domain.product.service.query;

import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDTO.ProductDetailRes getProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return ProductConverter.toProductDetailRes(product);
    }

    @Override
    public List<ProductResDTO.ProductDetailRes> getProductList() {

        List<Product> productList = productRepository.findAll();

        // 여기 반환 부분만 주문 목록 조회와 같이 AI 참고해서 만들었습니다
        return productList.stream().map(ProductConverter::toProductDetailRes).toList();
    }
}
