package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.service.command.ProductCommandService;
import com.project.likelion14thbe.domain.product.service.query.ProductQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @ApiResponses(@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "홈 화면 조회 성공"))
    public CustomResponse<ProductResDTO.HomeResult> getHome() {
        return CustomResponse.onSuccess(
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
        );
    }

    @GetMapping("/products")
    @Operation(summary = "상품 목록 조회", description = "전체 상품 목록을 조회합니다.")
    @ApiResponses(@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 목록 조회 성공"))
    public CustomResponse<List<ProductResDTO.ProductSummaryRes>> getProducts() {
        return CustomResponse.onSuccess(productQueryService.getProducts());
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "상품 개별 조회", description = "상품 ID를 이용하여 특정 상품의 상세 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상품 개별 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public CustomResponse<ProductResDTO.ProductDetailResult> getProductDetail(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId
    ) {
        return CustomResponse.onSuccess(productQueryService.getProduct(productId));
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "상품 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    public CustomResponse<ProductResDTO.ProductCreateResult> createProduct(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @org.springframework.web.bind.annotation.RequestBody ProductReqDTO.ProductCreateReq request
    ) {
        return CustomResponse.onSuccess(
                HttpStatus.CREATED,
                productCommandService.createProduct(userDetails.getUsername(), request)
        );
    }

    @PostMapping("/products/{productId}/likes")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "관심 상품 추가", description = "상품 ID를 이용하여 관심 상품으로 등록합니다.")
    @ApiResponses(@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관심 상품 추가 성공"))
    public CustomResponse<ProductResDTO.ProductLikeResult> addLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId
    ) {
        return CustomResponse.onSuccess(
                ProductResDTO.ProductLikeResult.builder()
                        .productId(productId)
                        .liked(true)
                        .build()
        );
    }

    @DeleteMapping("/products/{productId}/likes")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "관심 상품 취소", description = "상품 ID를 이용하여 관심 상품 등록을 취소합니다.")
    @ApiResponses(@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관심 상품 취소 성공"))
    public CustomResponse<ProductResDTO.ProductLikeResult> cancelLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId
    ) {
        return CustomResponse.onSuccess(
                ProductResDTO.ProductLikeResult.builder()
                        .productId(productId)
                        .liked(false)
                        .build()
        );
    }
}
