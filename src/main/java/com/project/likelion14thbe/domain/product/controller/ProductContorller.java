package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.service.command.ProductCommandService;
import com.project.likelion14thbe.domain.product.service.query.ProductQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "상품 API", description = "상품 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductContorller {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @PostMapping("/products")
    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    public CustomResponse<String> createProduct (
            @RequestBody ProductReqDTO.CreateProductReq createProductReq
    ){
        return CustomResponse.onSuccess(HttpStatus.CREATED, productCommandService.createProduct(createProductReq));
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    public CustomResponse<String> updateProduct (
            @PathVariable Long productId,
            // 어느부분을 수정할지 몰라서 전체 수정 할 수 있도록 PutMapping 하고 CreateProductReq 재사용했는데 이렇게 해도 괜찮은가요?
            @RequestBody ProductReqDTO.CreateProductReq updateProductReq
    ){
        productCommandService.updateProduct(productId, updateProductReq);
        return CustomResponse.onSuccess("상품 수정 성공");
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    public CustomResponse<String> deleteProduct (
            @PathVariable Long productId
    ){
        productCommandService.deleteProduct(productId);
        return CustomResponse.onSuccess("상품 삭제 완료");
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "개별 상품 조회", description = "개별 상품을 조회합니다.")
    public CustomResponse<ProductResDTO.ProductDetailRes> getProduct (
            @PathVariable Long productId
    ){
        return CustomResponse.onSuccess(productQueryService.getProduct(productId));
    }

    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    public CustomResponse<List<ProductResDTO.ProductDetailRes>> getProductList (
    ){
        return CustomResponse.onSuccess(productQueryService.getProductList());
    }
}
