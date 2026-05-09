package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.project.likelion14thbe.domain.product.converter.ProductConverter.toProductCreateResDTO;

import static com.project.likelion14thbe.domain.product.converter.ProductConverter.toProductCreateResDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDTO.ProductCreateResDto createProduct(ProductReqDTO.ProductCreateReqDto request) {
        Product product = ProductConverter.toProduct(request);

        Product savedProduct = productRepository.save(product);

        return toProductCreateResDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product pro = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));
        productRepository.delete(pro);
    }
}