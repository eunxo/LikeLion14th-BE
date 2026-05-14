package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;

public interface ProductCommandService {

    ProductResDTO.ProductCreateRes createProduct(ProductReqDTO.ProductCreateReq productCreateReq);

    void updateProduct(Long ProductId, Long memberId, ProductReqDTO.ProductChangeDTO dto);

    void deleteProduct(Long productId, Long memberId);
}
