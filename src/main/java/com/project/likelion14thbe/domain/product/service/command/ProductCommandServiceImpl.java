package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.order.execption.OrderErrorCode;
import com.project.likelion14thbe.domain.order.execption.OrderException;
import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
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

    @Override
    public ProductResDTO.ProductCreateRes createProduct(ProductReqDTO.ProductCreateReq productCreateReq) {

        Product product = ProductConverter.toProduct(productCreateReq);

        productRepository.save(product);

        return ProductConverter.toProductCreateRes(product);
    }

    @Override
    public void updateProduct(Long productId, Long memberId, ProductReqDTO.ProductChangeDTO dto){
        // 상품 정보 조회
        Product product = productRepository.findByAndNotDeleted(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 상품 정보 접근 권한 확인
        if (!product.getMember().getId().equals(memberId)) {
            throw new OrderException(OrderErrorCode.ORDER_FORBIDDEN);
        }

        product.updateProduct(dto.productPrice(), dto.productQuantity(), dto.productName(), dto.description());
    }

    @Override
    public void deleteProduct(Long productId, Long memberId){
        //상품 정보 조회
        Product product = productRepository.findByAndNotDeleted(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 리뷰 접근 권한 확인
        if (!product.getMember().getId().equals(memberId)) {
            throw new OrderException(OrderErrorCode.ORDER_FORBIDDEN);
        }

        product.delete();
    }
}
