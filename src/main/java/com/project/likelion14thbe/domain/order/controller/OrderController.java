package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "주문API", description = "주문 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @GetMapping("/orders")
    @Operation(summary = "내 주문 목록 조회", description = "memberId 받아서 내 주문 목록 전체 조회")
    public ResponseEntity<OrderResDTO.OrderGetListRes> getMyOrderList(){

        Long memberId = 1L;

        return ResponseEntity.ok(orderQueryService.getMyOrderList(memberId));
    }

    @PostMapping("/orders/create")
    @Operation(summary = "주문 추가", description = "주문 상품을 추가한다")
    public ResponseEntity<OrderResDTO.OrderCreateRes> createOrder(
            @RequestBody OrderReqDTO.OrderCreateReq orderCreateReq) {

        Long memberId = 1L;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderCommandService.createOrder(orderCreateReq, memberId));
    }

    @PatchMapping("/orders/{orderId}/status")
    @Operation(summary = "배송 상태 변경", description = "각 주문의 주문상태를 변경합니다.")
    public CustomResponse<String> changeStatus(
            @PathVariable Long orderId,
            @RequestBody OrderReqDTO.ChangeStatusDTO change
    ){
        orderCommandService.changeStatus(orderId, change);
        return CustomResponse.onSuccess("주문상태 변경 성공");
    }

    @DeleteMapping("/orders/{orderId}/delete")
    public CustomResponse<String> deleteOrder(
            @PathVariable Long orderId
    ){
        orderCommandService.deleteOrder(orderId);
        return CustomResponse.onSuccess("주문 내역 삭제 성공");
    }
}
