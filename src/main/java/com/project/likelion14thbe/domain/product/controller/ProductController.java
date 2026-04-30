package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "상품API", description = "상품 관련 API")
@RequestMapping("/api/v1")
public class ProductController {
    @GetMapping("/categories/{categoryId}/products/")
    @Operation(summary = "상품 목록 조회", description = "카테고리 안에 상품들을 전부 보여줍니다")
    public ResponseEntity<ProductResDTO.ProductGetRes> getProducts(
            @PathVariable int categoryId
    ){
        //상품 목록 조회 로직
        return ResponseEntity.ok(ProductResDTO.ProductGetRes.builder().build());
    }

    @GetMapping("/categories/{categoryId}/products/{productId}")
    @Operation(summary = "상품 상세 조회", description = "상품 하나의 정보를 자세하게 보여줍니다.")
    public ResponseEntity<ProductResDTO.ProductGetDeatilRes> getProduct(
            @PathVariable int productId,
            @PathVariable int categoryId
    ){
        //상품 상세 조회 로직
        return ResponseEntity.ok(ProductResDTO.ProductGetDeatilRes.builder().build());
    }

    @PostMapping("/products")
    @Operation(summary = "상품 추가", description = "새로운 상품을 등록한다")
    public ResponseEntity<String> createProduct(
            @RequestBody ProductReqDTO.ProductCreateReq PRoductCreateReq
    ){
        //상품 등록 로직
        return ResponseEntity.ok("상품 추가 성공");
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "상품 삭제", description = "기존 상품을 삭제한다")
    public ResponseEntity<String> deleteProduct(
    ){
        //상품 삭제 로직
        return ResponseEntity.ok("상품 삭제 성공");
    }
}
