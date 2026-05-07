package com.project.likelion14thbe.domain.product.service.query;

import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;

import java.util.List;

public interface ProductQueryService {

    List<ProductResDTO.ProductSummaryRes> getProducts();

    ProductResDTO.ProductDetailResult getProduct(Long productId);
}
