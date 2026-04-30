package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "주문 API", description = "주문 관련 API")
@RequestMapping("/api/v1")
public class OrderController {

    @PostMapping("/orders")
    @Operation(summary = "상품 주문", description = "상품을 주문합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 성공",
                    content = @Content(schema = @Schema(implementation = OrderResDTO.OrderCreateRes.class)))
    })
    public ResponseEntity<OrderResDTO.OrderCreateRes> createOrder(
            @RequestBody OrderReqDTO.OrderCreateReq request
    ) {
        return ResponseEntity.ok(
                OrderResDTO.OrderCreateRes.builder()
                        .isSuccess(true)
                        .code("ORDER201")
                        .message("주문 성공")
                        .result(
                                OrderResDTO.OrderCreateResult.builder()
                                        .orderId(1L)
                                        .productId(request.getProductId())
                                        .quantity(request.getQuantity())
                                        .build()
                        )
                        .build()
        );
    }

    @GetMapping("/orders")
    @Operation(summary = "내 상품 목록 조회", description = "내 주문 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = OrderResDTO.OrderListRes.class)))
    })
    public ResponseEntity<OrderResDTO.OrderListRes> getOrders() {
        return ResponseEntity.ok(
                OrderResDTO.OrderListRes.builder()
                        .isSuccess(true)
                        .code("ORDER200")
                        .message("주문 목록 조회 성공")
                        .result(List.of(
                                OrderResDTO.OrderSummaryRes.builder()
                                        .orderId(1L)
                                        .productName("무드등")
                                        .quantity(2)
                                        .status("ORDERED")
                                        .build()
                        ))
                        .build()
        );
    }

    @PatchMapping("/orders/{orderId}/cancel")
    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 취소 성공",
                    content = @Content(schema = @Schema(implementation = OrderResDTO.OrderCancelRes.class)))
    })
    public ResponseEntity<OrderResDTO.OrderCancelRes> cancelOrder(
            @Parameter(description = "주문 ID", example = "1")
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                OrderResDTO.OrderCancelRes.builder()
                        .isSuccess(true)
                        .code("ORDER200")
                        .message("주문 취소 성공")
                        .build()
        );
    }
}