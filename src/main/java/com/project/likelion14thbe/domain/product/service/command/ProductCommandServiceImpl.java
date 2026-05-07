package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDTO.ProductCreateRes createProduct(ProductReqDTO.ProductCreateReq productCreateReq) {

        Product product = ProductConverter.toProduct(productCreateReq);

        productRepository.save(product);

        return ProductConverter.toProductCreateRes(product);
    }
}
