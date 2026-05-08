package com.project.likelion14thbe.domain.product.controller.docs;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Product", description = "상품 API — 목록/상세 조회, 추가, 부분 수정, 삭제")
public interface ProductDocs {

    @Operation(
            summary = "상품 목록 조회",
            description = "상품 목록을 페이징 조회한다. 키워드/카테고리 필터 지원."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.ProductListResDTO.class)))
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
            @ApiResponse(responseCode = "404", description = "상품 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "PRODUCT404_1",
                                      "message": "상품이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    ResponseEntity<ProductResDTO.ProductDetailResDTO> getProduct(
            @Parameter(description = "상품 아이디", example = "1") Long productId
    );

    @Operation(
            summary = "상품 추가",
            description = "신규 상품을 등록한다. (6주차 시큐리티 도입 후 관리자 권한 검증 추가 예정)"
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "상품 등록 성공",
                    content = @Content(schema = @Schema(implementation = ProductResDTO.CreateProductResDTO.class))),
            @ApiResponse(responseCode = "400", description = "@Valid DTO 필드 검증 실패",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "VALID400_1",
                                      "message": "잘못된 DTO 필드입니다.",
                                      "result": {
                                        "name": "상품명은 필수입니다.",
                                        "price": "가격은 0 이상이어야 합니다."
                                      }
                                    }
                                    """)))
    })
    ResponseEntity<ProductResDTO.CreateProductResDTO> createProduct(
            ProductReqDTO.CreateProductReqDTO request
    );

    @Operation(
            summary = "상품 수정",
            description = "특정 상품의 정보를 부분 수정한다. null 필드는 변경하지 않는다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 수정 성공",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "200",
                                      "message": "OK",
                                      "result": {
                                        "id": 1,
                                        "name": "수정된 상품명",
                                        "price": 29000,
                                        "imageUrl": "https://example.com/new.jpg"
                                      }
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "@Valid DTO 필드 검증 실패 (예: 음수 가격)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "VALID400_1",
                                      "message": "잘못된 DTO 필드입니다.",
                                      "result": {
                                        "price": "가격은 0 이상이어야 합니다."
                                      }
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "상품 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "PRODUCT404_1",
                                      "message": "상품이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<ProductResDTO.UpdateProductResDTO> updateProduct(
            @Parameter(description = "상품 아이디", example = "1") Long productId,
            ProductReqDTO.UpdateProductReqDTO request
    );

    @Operation(
            summary = "상품 삭제",
            description = "특정 상품을 hard delete 한다. (6주차 시큐리티 도입 후 관리자 권한 검증 추가 예정)"
    )
    @SecurityRequirement(name = "JWT TOKEN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "200",
                                      "message": "OK",
                                      "result": "상품 삭제 성공"
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "상품 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "PRODUCT404_1",
                                      "message": "상품이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<String> deleteProduct(
            @Parameter(description = "상품 아이디", example = "1") Long productId
    );
}
