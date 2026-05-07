package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
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
    public String createProduct(ProductReqDTO.CreateProductReq createProductReqDTO){
        Product product = ProductConverter.toProduct(createProductReqDTO);

        productRepository.save(product);

        return "상품이 등록되었습니다.";
    }
}
