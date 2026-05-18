package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;

public interface ProductCommandService {

    void createProduct(final ProductReqDTO.CreateReq req, final String email);

    void updateProduct(final Long productId, final ProductReqDTO.CreateReq req, final String email);

    void deleteProduct(final Long productId, final String email);

    void addBookmark(final Long productId, final String email);
}