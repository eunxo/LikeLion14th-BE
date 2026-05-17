package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.service.command.ProductCommandService;
import com.project.likelion14thbe.domain.product.service.query.ProductQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "상품 API")
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @GetMapping("/home")
    @Operation(summary = "홈 화면 조회")
    public CustomResponse<ProductResDTO.HomeRes> getHome() {
        return CustomResponse.onSuccess(productQueryService.getHome());
    }

    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회")
    public CustomResponse<ProductResDTO.ListRes> getProducts() {
        return CustomResponse.onSuccess(productQueryService.getProducts());
    }

    @PostMapping("/products")
    @Operation(summary = "상품 등록")
    public CustomResponse<String> createProduct(@RequestBody ProductReqDTO.CreateReq req) {
        productCommandService.createProduct(req);
        return CustomResponse.onSuccess("상품 등록 완료");
    }

    @PatchMapping("/products/{productId}")
    @Operation(summary = "상품 수정")
    public CustomResponse<String> updateProduct(@PathVariable Long productId, @RequestBody ProductReqDTO.CreateReq req) {
        productCommandService.updateProduct(productId, req);
        return CustomResponse.onSuccess("상품 수정 완료");
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "상품 삭제")
    public CustomResponse<String> deleteProduct(@PathVariable Long productId) {
        productCommandService.deleteProduct(productId);
        return CustomResponse.onSuccess("상품 삭제 완료");
    }

    @PostMapping("/members/{memberId}/bookmarks/{productId}")
    @Operation(summary = "관심 상품 추가")
    public CustomResponse<String> addBookmark(
            @PathVariable Long memberId,
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        productCommandService.addBookmark(memberId, productId, customUserDetails.getUsername());
        return CustomResponse.onSuccess("북마크 등록 완료");
    }
}