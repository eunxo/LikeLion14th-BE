package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;

public interface ProductCommandService {
    void createProduct(ProductReqDTO.CreateReq req);
    void updateProduct(Long productId, ProductReqDTO.CreateReq req);
    void deleteProduct(Long productId);
    void addBookmark(Long memberId, Long productId);
}
