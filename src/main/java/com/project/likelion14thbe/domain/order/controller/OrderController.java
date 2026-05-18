package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "주문 API", description = "주문 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "상품 주문", description = "로그인한 사용자 기준으로 상품을 주문합니다.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "주문 성공"))
    public CustomResponse<OrderResDTO.OrderCreateResult> createOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody OrderReqDTO.OrderCreateReq request
    ) {
        return CustomResponse.onSuccess(
                HttpStatus.CREATED,
                orderCommandService.createOrder(userDetails.getUsername(), request)
        );
    }

    @GetMapping("/orders")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "내 주문 목록 조회", description = "로그인한 사용자의 주문 목록을 조회합니다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공"))
    public CustomResponse<List<OrderResDTO.OrderSummaryRes>> getOrders(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return CustomResponse.onSuccess(orderQueryService.getOrders(userDetails.getUsername()));
    }

    @PatchMapping("/orders/{orderId}/cancel")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "주문 취소", description = "본인 주문만 취소할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 취소 성공"),
            @ApiResponse(responseCode = "403", description = "본인 주문이 아님"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음")
    })
    public CustomResponse<String> cancelOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "주문 ID", example = "1") @PathVariable Long orderId
    ) {
        orderCommandService.cancelOrder(userDetails.getUsername(), orderId);
        return CustomResponse.onSuccess("주문 취소 성공");
    }
}
