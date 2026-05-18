package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
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
    private final MemberRepository memberRepository;

    @Override
    public ProductResDTO.ProductCreateRes createProduct(ProductReqDTO.ProductCreateReq productCreateReq, String email) {

        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = ProductConverter.toProduct(productCreateReq);

        productRepository.save(product);

        return ProductConverter.toProductCreateRes(product);
    }

    @Override
    public void updateProduct(Long productId, String email, ProductReqDTO.ProductChangeDTO dto){
        // 상품 정보 조회
        Product product = productRepository.findByAndNotDeleted(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 상품 정보 접근 권한 확인
        if (!product.getMember().getEmail().equals(email)) {
            throw new ProductException(ProductErrorCode.PRODUCT_FORBIDDEN);
        }

        product.updateProduct(dto.productPrice(), dto.productQuantity(), dto.productName(), dto.description());
    }

    @Override
    public void deleteProduct(Long productId, String email){
        //상품 정보 조회
        Product product = productRepository.findByAndNotDeleted(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 리뷰 접근 권한 확인
        if (!product.getMember().getEmail().equals(email)) {
            throw new ProductException(ProductErrorCode.PRODUCT_FORBIDDEN);
        }

        product.delete();
    }
}
