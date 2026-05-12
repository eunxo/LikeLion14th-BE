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

@RestController
@Tag(name = "상품API", description = "상품 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @GetMapping("/products/get")
    @Operation(summary = "상품 목록 조회", description = "상품 전체 목록을 조회합니다")
    public ResponseEntity<ProductResDTO.ProductGetRes> getProducts(){
        return ResponseEntity.ok(productQueryService.getProducts());
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "상품 상세 조회", description = "상품 하나의 정보를 자세하게 보여줍니다.")
    public ResponseEntity<ProductResDTO.ProductGetDetailRes> getProduct(
            @PathVariable Long productId
    ){
        return ResponseEntity.ok(productQueryService.getProductDetail(productId));
    }

    @PostMapping("/products")
    @Operation(summary = "상품 추가", description = "새로운 상품을 등록한다")
    public ResponseEntity<ProductResDTO.ProductCreateRes> createProduct(
            @RequestBody ProductReqDTO.ProductCreateReq productCreateReq
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productCommandService.createProduct(productCreateReq));
    }

    @PatchMapping("/products/{productId}")
    @Operation(summary = "상품 수정", description = "상품 정보를 변경합니다")
    public CustomResponse<String> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductReqDTO.ProductChangeDTO update
    ){
        productCommandService.updateProduct(productId, update);
        return CustomResponse.onSuccess("상품 정보 변경 성공");
    }

    @DeleteMapping("/product/{productId}/delete")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    public CustomResponse<String> deleteProduct(
            @PathVariable Long productId
    ){
        productCommandService.deleteProduct(productId);
        return CustomResponse.onSuccess("회원 탈퇴 성공");
    }
}
