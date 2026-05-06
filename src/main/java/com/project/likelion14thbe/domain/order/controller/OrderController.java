package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Order", description = "주문 관련 API")
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping("")
    @Operation(summary = "주문 생성", description = "상품 리스트와 배송 정보를 받아 새로운 주문을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 생성 성공"),
            @ApiResponse(responseCode = "400", description = "재고 부족 또는 잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    public ResponseEntity<String> createOrder(@RequestBody OrderReqDTO.CreateOrderReq request) {
        return ResponseEntity.ok("주문이 성공적으로 완료되었습니다.");
    }

    @GetMapping("/me")
    @Operation(summary = "내 주문 목록 조회", description = "현재 로그인한 사용자의 과거 주문 내역을 모두 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 내역 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    public ResponseEntity<List<OrderResDTO.OrderHistoryRes>> getMyOrders() {
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{orderId}")
    @Operation(summary = "내 주문 상세 조회", description = "로그인한 사용자의 주문 ID를 이용해 특정 주문의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 주문을 찾을 수 없음")
    })
    public ResponseEntity<OrderResDTO.OrderDetailResDto> getMyOrderDetail(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderQueryService.getOrder(orderId));
    }
}