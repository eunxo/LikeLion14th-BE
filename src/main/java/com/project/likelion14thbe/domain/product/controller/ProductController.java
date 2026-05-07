package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.service.command.ProductCommandService;
import com.project.likelion14thbe.domain.product.service.query.ProductQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "상품 API", description = "상품 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;

    @GetMapping("/home")
    @Operation(summary = "홈 화면 조회", description = "홈 화면에 필요한 배너, 추천 상품, 인기 상품 데이터를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "홈 화면 조회 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.HomeRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "홈 화면 데이터 조회 실패")
    })
    public ResponseEntity<ProductResDTO.HomeRes> getHome() {
        return ResponseEntity.ok(
                ProductResDTO.HomeRes.builder()
                        .isSuccess(true)
                        .code("HOME200")
                        .message("홈 화면 조회 성공")
                        .result(
                                ProductResDTO.HomeResult.builder()
                                        .banners(List.of(
                                                ProductResDTO.BannerRes.builder()
                                                        .bannerId(1L)
                                                        .title("봄맞이 추천 상품")
                                                        .imageUrl("https://example.com/banner1.png")
                                                        .linkUrl("/products?category=spring")
                                                        .build()
                                        ))
                                        .recommendProducts(List.of(
                                                ProductResDTO.ProductSummaryRes.builder()
                                                        .productId(1L)
                                                        .name("무드등")
                                                        .price(12000)
                                                        .imageUrl("https://example.com/product1.png")
                                                        .build()
                                        ))
                                        .popularProducts(List.of(
                                                ProductResDTO.ProductSummaryRes.builder()
                                                        .productId(2L)
                                                        .name("텀블러")
                                                        .price(9000)
                                                        .imageUrl("https://example.com/product2.png")
                                                        .build()
                                        ))
                                        .build()
                        )
                        .build()
        );
    }

    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회", description = "전체 상품 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.ProductListRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "상품 목록 조회 실패")
    })
    public ResponseEntity<ProductResDTO.ProductListRes> getProducts() {
        return ResponseEntity.ok(
                ProductResDTO.ProductListRes.builder()
                        .isSuccess(true)
                        .code("PRODUCT200")
                        .message("상품 목록 조회 성공")
                        .result(productQueryService.getProducts())
                        .build()
        );
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "상품 개별 조회", description = "상품 ID를 이용하여 특정 상품의 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 개별 조회 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.ProductDetailRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public ResponseEntity<ProductResDTO.ProductDetailRes> getProductDetail(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                ProductResDTO.ProductDetailRes.builder()
                        .isSuccess(true)
                        .code("PRODUCT200")
                        .message("상품 개별 조회 성공")
                        .result(productQueryService.getProduct(productId))
                        .build()
        );
    }

    @PostMapping("/products")
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 등록 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.ProductCreateRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    public ResponseEntity<ProductResDTO.ProductCreateRes> createProduct(
            @RequestBody(description = "상품 등록 요청 데이터", required = true)
            @org.springframework.web.bind.annotation.RequestBody ProductReqDTO.ProductCreateReq request
    ) {
        return ResponseEntity.ok(
                ProductResDTO.ProductCreateRes.builder()
                        .isSuccess(true)
                        .code("PRODUCT201")
                        .message("상품 등록 성공")
                        .result(productCommandService.createProduct(request))
                        .build()
        );
    }

    @PostMapping("/products/{productId}/likes")
    @Operation(summary = "관심 상품 추가", description = "상품 ID를 이용하여 관심 상품으로 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관심 상품 추가 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.ProductLikeRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이미 관심 상품으로 등록된 상품"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public ResponseEntity<ProductResDTO.ProductLikeRes> addLike(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                ProductResDTO.ProductLikeRes.builder()
                        .isSuccess(true)
                        .code("LIKE200")
                        .message("관심 상품 추가 성공")
                        .result(
                                ProductResDTO.ProductLikeResult.builder()
                                        .productId(productId)
                                        .liked(true)
                                        .build()
                        )
                        .build()
        );
    }

    @DeleteMapping("/products/{productId}/likes")
    @Operation(summary = "관심 상품 취소", description = "상품 ID를 이용하여 관심 상품 등록을 취소합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관심 상품 취소 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.ProductLikeRes.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "관심 상품으로 등록되지 않은 상품"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public ResponseEntity<ProductResDTO.ProductLikeRes> cancelLike(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                ProductResDTO.ProductLikeRes.builder()
                        .isSuccess(true)
                        .code("LIKE200")
                        .message("관심 상품 취소 성공")
                        .result(
                                ProductResDTO.ProductLikeResult.builder()
                                        .productId(productId)
                                        .liked(false)
                                        .build()
                        )
                        .build()
        );
    }
}