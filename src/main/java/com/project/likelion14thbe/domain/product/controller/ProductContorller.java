package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.service.command.ProductCommandService;
import com.project.likelion14thbe.domain.product.service.query.ProductQueryService;
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
    public ResponseEntity<String> createProduct (
            @RequestBody ProductReqDTO.CreateProductReq createProductReq
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productCommandService.createProduct(createProductReq));
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    public ResponseEntity<String> deleteProduct (
            @PathVariable Long productId
    ){
        // 상품 삭제 로직~~~
        return ResponseEntity.ok("상품 삭제 완료");
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "개별 상품 조회", description = "개별 상품을 조회합니다.")
    public ResponseEntity<ProductResDTO.ProductDetailRes> getProduct (
            @PathVariable Long productId
    ){
        return ResponseEntity.ok(productQueryService.getProduct(productId));
    }

    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    public ResponseEntity<List<ProductResDTO.ProductDetailRes>> getProductList (
    ){
        // 개별 상품 조회때 사용한 DTO 재사용

        // 상품 목록 조회 로직 Mock data 활용
        List<ProductResDTO.ProductDetailRes> productList = List.of(
                ProductResDTO.ProductDetailRes.builder().build(), // 첫 번째 상품
                ProductResDTO.ProductDetailRes.builder().build()  // 두 번째 상품
        );

        return ResponseEntity.ok(productList);
    }
}
