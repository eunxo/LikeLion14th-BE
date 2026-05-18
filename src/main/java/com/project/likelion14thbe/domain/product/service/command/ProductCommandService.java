package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;

public interface ProductCommandService {

    ProductResDTO.ProductCreateRes createProduct(ProductReqDTO.ProductCreateReq productCreateReq, String email);

    void updateProduct(Long ProductId, String email, ProductReqDTO.ProductChangeDTO dto);

    void deleteProduct(Long productId, String email);
}
