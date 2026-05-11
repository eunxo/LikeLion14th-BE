package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import com.project.likelion14thbe.domain.category.entity.Category;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import com.project.likelion14thbe.domain.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.likelion14thbe.domain.product.converter.ProductConverter.toProductCreateResDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResDTO.ProductCreateResDto createProduct(ProductReqDTO.ProductCreateReqDto request) {
        Category category = categoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

        Product product = ProductConverter.toProduct(request, category);

        Product savedProduct = productRepository.save(product);

        return toProductCreateResDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

        product.delete();
        productRepository.save(product);
    }

    @Override
    public ProductResDTO.ProductCreateResDto updateProduct(Long id, ProductReqDTO.ProductUpdateReqDto request) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

        Category category = null;
        if (request.getCategory() != null) {
            category = categoryRepository.findByName(request.getCategory())
                    .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));
        }

        ProductConverter.updateProduct(product, request, category);

        Product savedProduct = productRepository.save(product);
        return toProductCreateResDTO(savedProduct);
    }
}