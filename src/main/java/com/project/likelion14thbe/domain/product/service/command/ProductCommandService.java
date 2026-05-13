package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;

public interface ProductCommandService {

    ProductResDTO.CreateProductResDTO createProduct(ProductReqDTO.CreateProductReqDTO request);

    ProductResDTO.UpdateProductResDTO updateProduct(Long productId, ProductReqDTO.UpdateProductReqDTO request);

    void deleteProduct(Long productId);
}
