package com.project.likelion14thbe.domain.order.controller.docs;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Order", description = "주문 API — 주문 생성, 내 주문 목록 조회")
@SecurityRequirement(name = "JWT TOKEN")
public interface OrderDocs {

    @Operation(
            summary = "주문 생성",
            description = "현재 로그인한 회원이 특정 상품을 주문한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "주문 생성 성공",
                    content = @Content(schema = @Schema(implementation = OrderResDTO.CreateOrderResDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 수량 또는 입력 형식 오류", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "404", description = "상품이 존재하지 않음", content = @Content)
    })
    ResponseEntity<OrderResDTO.CreateOrderResDTO> createOrder(
            OrderReqDTO.CreateOrderReqDTO request
    );

    @Operation(
            summary = "내 주문 목록 조회",
            description = "현재 로그인한 회원의 모든 주문을 페이징 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 주문 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = OrderResDTO.MyOrderListResDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 페이지 파라미터", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content)
    })
    ResponseEntity<OrderResDTO.MyOrderListResDTO> getMyOrders(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") Integer page,
            @Parameter(description = "페이지당 개수", example = "10") Integer size
    );
}
