package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;

public interface ProductCommandService {

     String createProduct(ProductReqDTO.CreateProductReq createProductReqDTO, CustomUserDetails customUserDetails);

     void updateProduct(Long productId, ProductReqDTO.CreateProductReq updateProductReqDTO);

     void deleteProduct(Long productId);
}
