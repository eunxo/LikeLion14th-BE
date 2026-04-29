package com.project.likelion14thbe.domain.product.controller.docs;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Product", description = "상품 API — 목록/상세 조회, 추가, 삭제")
public interface ProductDocs {

    @Operation(
            summary = "상품 목록 조회",
            description = "상품 목록을 페이징 조회한다. 키워드/카테고리 필터 지원."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.ProductListResDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 페이지 파라미터", content = @Content)
    })
    ResponseEntity<ProductResDTO.ProductListResDTO> getProductList(
            @Parameter(description = "상품명 검색어 (선택)", example = "사과") String keyword,
            @Parameter(description = "카테고리 필터 (선택)", example = "과일") String category,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") Integer page,
            @Parameter(description = "페이지당 개수", example = "10") Integer size
    );

    @Operation(
            summary = "상품 상세 조회",
            description = "특정 상품의 상세 정보를 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.ProductDetailResDTO.class))),
            @ApiResponse(responseCode = "404", description = "상품이 존재하지 않음", content = @Content)
    })
    ResponseEntity<ProductResDTO.ProductDetailResDTO> getProduct(
            @Parameter(description = "상품 아이디", example = "1") Long productId
    );

    @Operation(
            summary = "상품 추가",
            description = "신규 상품을 등록한다. 관리자 권한 필요."
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "상품 등록 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.CreateProductResDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 형식 오류", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "403", description = "관리자 권한 없음", content = @Content)
    })
    ResponseEntity<ProductResDTO.CreateProductResDTO> createProduct(
            ProductReqDTO.CreateProductReqDTO request
    );

    @Operation(
            summary = "상품 삭제",
            description = "특정 상품을 삭제한다. 관리자 권한 필요."
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.DeleteProductResDTO.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "403", description = "관리자 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "상품이 존재하지 않음", content = @Content)
    })
    ResponseEntity<ProductResDTO.DeleteProductResDTO> deleteProduct(
            @Parameter(description = "상품 아이디", example = "51") Long productId
    );
}
