package com.project.likelion14thbe.domain.review.controller.docs;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Review", description = "리뷰 API — 생성, 단건/목록 조회, 수정, 삭제")
@SecurityRequirement(name = "JWT TOKEN")
public interface ReviewDocs {

    @Operation(
            summary = "리뷰 생성",
            description = "특정 상품에 대해 새로운 리뷰를 생성한다. 동일 상품에 1인 1회 작성 제한."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "리뷰 생성 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.CreateReviewResDTO.class))),
            @ApiResponse(responseCode = "400", description = "@Valid DTO 필드 검증 실패",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "VALID400_1",
                                      "message": "잘못된 DTO 필드입니다.",
                                      "result": {
                                        "rating": "별점은 0.5 이상이어야 합니다.",
                                        "content": "리뷰 내용은 필수입니다."
                                      }
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "회원 또는 상품 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = {
                                    @ExampleObject(name = "회원 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_4",
                                              "message": "회원이 존재하지 않습니다.",
                                              "result": null
                                            }
                                            """),
                                    @ExampleObject(name = "상품 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_3",
                                              "message": "리뷰 대상 상품이 존재하지 않습니다.",
                                              "result": null
                                            }
                                            """)
                            })),
            @ApiResponse(responseCode = "409", description = "이미 해당 상품에 리뷰 작성함",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "REVIEW409_1",
                                      "message": "이미 해당 상품에 리뷰를 작성했습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<ReviewResDTO.CreateReviewResDTO> createReview(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            CustomUserDetails user,
            ReviewReqDTO.CreateReviewReqDTO request
    );

    @Operation(
            summary = "리뷰 단건 조회",
            description = "특정 상품의 특정 리뷰 1건을 상세 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 단건 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailResDTO.class))),
            @ApiResponse(responseCode = "404", description = "리뷰 미존재 또는 상품 불일치",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = {
                                    @ExampleObject(name = "리뷰 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_1",
                                              "message": "리뷰가 존재하지 않습니다.",
                                              "result": null
                                            }
                                            """),
                                    @ExampleObject(name = "URL 상품과 리뷰 상품 불일치", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_2",
                                              "message": "해당 상품의 리뷰가 아닙니다.",
                                              "result": null
                                            }
                                            """)
                            }))
    })
    CustomResponse<ReviewResDTO.ReviewDetailResDTO> getReview(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "리뷰 아이디", example = "1") Long reviewId
    );

    @Operation(
            summary = "리뷰 목록 조회",
            description = "특정 상품의 리뷰 목록을 페이징 조회한다. 정렬 기준은 latest / rating 지원."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewListResDTO.class))),
            @ApiResponse(responseCode = "404", description = "상품 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "REVIEW404_3",
                                      "message": "리뷰 대상 상품이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<ReviewResDTO.ReviewListResDTO> getReviewList(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") Integer page,
            @Parameter(description = "페이지당 개수", example = "10") Integer size,
            @Parameter(description = "정렬 기준 (latest | rating)", example = "latest",
                    schema = @Schema(allowableValues = {"latest", "rating"})) String sort
    );

    @Operation(
            summary = "리뷰 수정",
            description = "본인이 작성한 리뷰의 별점/내용을 부분 수정한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "200",
                                      "message": "OK",
                                      "result": {
                                        "reviewId": 4,
                                        "writerNickname": "홍길동",
                                        "rating": 5.0,
                                        "content": "다시 먹어봤는데 더 맛있네요!",
                                        "updatedAt": "2026-05-08T19:33:04"
                                      }
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "@Valid DTO 필드 검증 실패 (별점 범위 초과)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "VALID400_1",
                                      "message": "잘못된 DTO 필드입니다.",
                                      "result": {
                                        "rating": "별점은 5.0 이하여야 합니다."
                                      }
                                    }
                                    """))),
            @ApiResponse(responseCode = "403", description = "본인이 작성한 리뷰가 아님",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "REVIEW403_1",
                                      "message": "본인 리뷰만 수정/삭제할 수 있습니다.",
                                      "result": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "회원/리뷰 미존재 또는 상품 불일치",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = {
                                    @ExampleObject(name = "회원 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_4",
                                              "message": "회원이 존재하지 않습니다.",
                                              "result": null
                                            }
                                            """),
                                    @ExampleObject(name = "리뷰 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_1",
                                              "message": "리뷰가 존재하지 않습니다.",
                                              "result": null
                                            }
                                            """),
                                    @ExampleObject(name = "URL 상품과 리뷰 상품 불일치", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_2",
                                              "message": "해당 상품의 리뷰가 아닙니다.",
                                              "result": null
                                            }
                                            """)
                            }))
    })
    CustomResponse<ReviewResDTO.UpdateReviewResDTO> updateReview(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "리뷰 아이디", example = "123") Long reviewId,
            CustomUserDetails user,
            ReviewReqDTO.UpdateReviewReqDTO request
    );

    @Operation(
            summary = "리뷰 삭제",
            description = "본인이 작성한 리뷰를 hard delete 한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "200",
                                      "message": "OK",
                                      "result": "리뷰 삭제 성공"
                                    }
                                    """))),
            @ApiResponse(responseCode = "403", description = "본인이 작성한 리뷰가 아님",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "REVIEW403_1",
                                      "message": "본인 리뷰만 수정/삭제할 수 있습니다.",
                                      "result": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "회원/리뷰 미존재 또는 상품 불일치",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = {
                                    @ExampleObject(name = "회원 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_4",
                                              "message": "회원이 존재하지 않습니다.",
                                              "result": null
                                            }
                                            """),
                                    @ExampleObject(name = "리뷰 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_1",
                                              "message": "리뷰가 존재하지 않습니다.",
                                              "result": null
                                            }
                                            """),
                                    @ExampleObject(name = "URL 상품과 리뷰 상품 불일치", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "REVIEW404_2",
                                              "message": "해당 상품의 리뷰가 아닙니다.",
                                              "result": null
                                            }
                                            """)
                            }))
    })
    CustomResponse<String> deleteReview(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "리뷰 아이디", example = "123") Long reviewId,
            CustomUserDetails user
    );

    @Operation(
            summary = "내 리뷰 조회",
            description = "현재 로그인한 회원이 작성한 모든 리뷰를 페이징 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 리뷰 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.MyReviewListResDTO.class))),
            @ApiResponse(responseCode = "404", description = "회원 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "REVIEW404_4",
                                      "message": "회원이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<ReviewResDTO.MyReviewListResDTO> getMyReviews(
            CustomUserDetails user,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") Integer page,
            @Parameter(description = "페이지당 개수", example = "10") Integer size
    );
}
