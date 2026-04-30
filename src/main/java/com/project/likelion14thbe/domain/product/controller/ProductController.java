package com.project.likelion14thbe.domain.product.controller;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity; // 이거 추가
import org.springframework.web.bind.annotation.*;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "상품 API", description = "상품 조회 및 등록 관련 API")
@RequestMapping("/api/v1")
public class ProductController {

    @GetMapping("/home")
    @Operation(summary = "홈 화면 조회", description = "인기 상품 및 카테고리를 조회합니다.")
    public ResponseEntity<ProductResDTO.HomeRes> getHome() {
        return ResponseEntity.ok(ProductResDTO.HomeRes.builder().build());
    }

    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회", description = "카테고리별 상품 목록을 조회합니다.")
    public ResponseEntity<ProductResDTO.ListRes> getProducts(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(ProductResDTO.ListRes.builder().build());
    }

    @PostMapping("/products")
    @Operation(summary = "상품 등록", description = "관리자가 새 상품을 등록합니다.")
    public ResponseEntity<String> createProduct(@RequestBody ProductReqDTO.CreateReq createReq) {
        return ResponseEntity.ok("상품 등록 완료");
    }

    @PostMapping("/users/{userId}/bookmarks/{productId}")
    @Operation(summary = "관심 상품 추가", description = "유저의 관심 목록에 상품을 추가합니다.")
    public ResponseEntity<String> addBookmark(@PathVariable Long userId, @PathVariable Long productId) {
        return ResponseEntity.ok("북마크 등록 완료");
    }
}
