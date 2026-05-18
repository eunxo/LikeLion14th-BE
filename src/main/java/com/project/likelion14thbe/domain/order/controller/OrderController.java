package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문 API")
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping("/orders")
    @Operation(summary = "주문하기")
    public CustomResponse<String> createOrder(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderReqDTO.CreateReq.class),
                            examples = @ExampleObject(
                                    name = "주문 요청 예시",
                                    value = "{\n  \"productId\": 1,\n  \"quantity\": 0,\n  \"color\": \"string\"\n}"
                            )
                    )
            )
            @RequestBody final OrderReqDTO.CreateReq req
    ) {
        orderCommandService.createOrder(customUserDetails.getUsername(), req);
        return CustomResponse.onSuccess("주문 완료");
    }

    @GetMapping("/orders")
    @Operation(summary = "내 주문 목록 조회", description = "로그인한 유저의 주문 내역들을 조회합니다.")
    public CustomResponse<OrderResDTO.OrderListRes> getOrders(
            @AuthenticationPrincipal final CustomUserDetails customUserDetails
    ) {
        return CustomResponse.onSuccess(orderQueryService.getOrders(customUserDetails.getUsername()));
    }

    @DeleteMapping("/orders/{orderId}")
    @Operation(summary = "주문 취소")
    public CustomResponse<String> cancelOrder(
            @PathVariable final Long orderId,
            @AuthenticationPrincipal final CustomUserDetails customUserDetails
    ) {
        orderCommandService.deleteOrder(orderId, customUserDetails.getUsername());
        return CustomResponse.onSuccess("주문 취소 완료");
    }
}