package com.project.likelion14thbe.domain.order.controller.docs;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
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

@Tag(name = "Order", description = "주문 API — 주문 생성, 내 주문 목록 조회, 주문 취소")
@SecurityRequirement(name = "JWT TOKEN")
public interface OrderDocs {

    @Operation(
            summary = "주문 생성",
            description = "현재 로그인한 회원이 특정 상품을 주문한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "주문 생성 성공",
                    content = @Content(schema = @Schema(implementation = OrderResDTO.CreateOrderResDTO.class))),
            @ApiResponse(responseCode = "400", description = "@Valid DTO 필드 검증 실패 (예: 수량 1 미만)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "VALID400_1",
                                      "message": "잘못된 DTO 필드입니다.",
                                      "result": {
                                        "quantity": "수량은 1 이상이어야 합니다."
                                      }
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "회원 또는 상품 미존재 (RFC 9457 — most relevant problem 응답: 회원 검증 우선)",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = {
                                    @ExampleObject(name = "회원 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "ORDER404_2",
                                              "message": "회원이 존재하지 않습니다.",
                                              "result": null
                                            }
                                            """),
                                    @ExampleObject(name = "상품 미존재", value = """
                                            {
                                              "isSuccess": false,
                                              "code": "ORDER404_3",
                                              "message": "주문 상품을 찾을 수 없습니다.",
                                              "result": null
                                            }
                                            """)
                            }))
    })
    CustomResponse<OrderResDTO.CreateOrderResDTO> createOrder(
            CustomUserDetails user,
            OrderReqDTO.CreateOrderReqDTO request
    );

    @Operation(
            summary = "내 주문 목록 조회",
            description = "현재 로그인한 회원의 모든 주문을 페이징 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 주문 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = OrderResDTO.MyOrderListResDTO.class))),
            @ApiResponse(responseCode = "404", description = "회원 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "ORDER404_2",
                                      "message": "회원이 존재하지 않습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<OrderResDTO.MyOrderListResDTO> getMyOrders(
            CustomUserDetails user,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") Integer page,
            @Parameter(description = "페이지당 개수", example = "10") Integer size
    );

    @Operation(
            summary = "주문 취소",
            description = "본인의 주문을 취소(hard delete)한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 취소 성공",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": true,
                                      "code": "200",
                                      "message": "OK",
                                      "result": "주문 취소 성공"
                                    }
                                    """))),
            @ApiResponse(responseCode = "403", description = "본인 주문이 아님",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "ORDER403_1",
                                      "message": "본인 주문만 취소할 수 있습니다.",
                                      "result": null
                                    }
                                    """))),
            @ApiResponse(responseCode = "404", description = "주문 미존재",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "ORDER404_1",
                                      "message": "주문을 찾을 수 없습니다.",
                                      "result": null
                                    }
                                    """)))
    })
    CustomResponse<String> cancelOrder(
            @Parameter(description = "취소할 주문 ID", example = "1") Long orderId,
            CustomUserDetails user
    );
}
