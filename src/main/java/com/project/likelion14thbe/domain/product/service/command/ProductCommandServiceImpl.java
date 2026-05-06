package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
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
    public void createProduct(ProductReqDTO.CreateReq req) {
        productRepository.save(ProductConverter.toProduct(req));
    }
}
