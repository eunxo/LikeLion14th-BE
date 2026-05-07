package com.project.likelion14thbe.domain.product.service.query;

import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;

public interface ProductQueryService {

    ProductResDTO.ProductGetRes getProducts();

    ProductResDTO.ProductGetDetailRes getProductDetail(Long productId);
}
