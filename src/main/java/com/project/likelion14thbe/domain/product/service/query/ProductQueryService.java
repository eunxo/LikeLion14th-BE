package com.project.likelion14thbe.domain.product.service.query;

import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;

public interface ProductQueryService {

    ProductResDTO.ProductDetailResDTO getProduct(Long productId);

    ProductResDTO.ProductListResDTO getProductList(Integer page, Integer size);
}
