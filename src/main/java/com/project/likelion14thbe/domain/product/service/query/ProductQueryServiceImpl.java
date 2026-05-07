package com.project.likelion14thbe.domain.product.service.query;

import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDTO.ProductGetRes getProducts() {

        List<Product> productList = productRepository.findAll();

        List<ProductResDTO.ProductGetRes.ProductInfo> productInfos = productList.stream()
                .map(ProductConverter::toProductInfo)
                .toList();

        return ProductConverter.toProductGetRes(productInfos);
    }

    @Override
    public ProductResDTO.ProductGetDetailRes getProductDetail(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        return ProductConverter.toProductGetDetailRes(product);
    }
}
