package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.exception.ProductErrorCode;
import com.project.likelion14thbe.domain.product.exception.ProductException;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createProduct(ProductReqDTO.CreateReq req) {
        productRepository.save(ProductConverter.toProduct(req));
    }

    @Override
    public void updateProduct(Long productId, ProductReqDTO.CreateReq req) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));
        product.update(req.getName(), req.getPrice(), req.getCategory(), req.getImageUrl(), req.getDescription());
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

    @Override
    public void addBookmark(Long memberId, Long productId) {
        memberRepository.findByIdAndNotDeleted(memberId).orElseThrow();
        productRepository.findById(productId).orElseThrow();
    }
}
