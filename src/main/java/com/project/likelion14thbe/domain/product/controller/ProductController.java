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

@RestController
@RequiredArgsConstructor
@Tag(name = "상품 API", description = "상품 조회 및 등록 관련 API")
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @GetMapping("/home")
    @Operation(summary = "홈 화면 조회", description = "인기 상품 및 카테고리를 조회합니다.")
    public ResponseEntity<ProductResDTO.HomeRes> getHome() {
        return ResponseEntity.ok(ProductResDTO.HomeRes.builder().build());
    }

    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회")
    public ResponseEntity<ProductResDTO.ListRes> getProducts() {
        return ResponseEntity.ok(productQueryService.getProducts());
    }

    @PostMapping("/products")
    @Operation(summary = "상품 등록")
    public ResponseEntity<String> createProduct(@RequestBody ProductReqDTO.CreateReq req) {
        productCommandService.createProduct(req);
        return ResponseEntity.status(HttpStatus.CREATED).body("등록 완료");
    }

    @PostMapping("/users/{userId}/bookmarks/{productId}")
    @Operation(summary = "관심 상품 추가", description = "유저의 관심 목록에 상품을 추가합니다.")
    public ResponseEntity<String> addBookmark(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok("북마크 등록 완료");
    }
}
