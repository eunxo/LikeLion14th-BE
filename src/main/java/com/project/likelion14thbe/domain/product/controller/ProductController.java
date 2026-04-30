package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Product", description = "상품 관련 API")
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("")
    @Operation(summary = "상품 목록 조회", description = "카테고리 필터 및 정렬 조건에 맞는 상품 목록을 가져옵니다.")
    public ResponseEntity<List<ProductResDTO.ProductResponseDTO>> getProducts(
            @ParameterObject ProductReqDTO.ProductRequestDTO request
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 조회", description = "상품 ID를 이용해 특정 상품의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public ResponseEntity<ProductResDTO.ProductDetailRes> getProductDetail(@PathVariable Long productId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    @Operation(summary = "상품 추가", description = "관리자 권한으로 새로운 상품을 등록합니다.")
    public ResponseEntity<String> createProduct(@RequestBody ProductReqDTO.ProductCreateReq request) {
        return ResponseEntity.ok("상품 추가 성공");
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "상품 ID를 이용해 등록된 상품을 삭제합니다.")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok("상품 삭제 성공");
    }
}