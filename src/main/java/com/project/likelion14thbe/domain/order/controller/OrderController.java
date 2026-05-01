package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.controller.docs.OrderDocs;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController implements OrderDocs {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @Override
    @PostMapping("/orders")
    public ResponseEntity<OrderResDTO.CreateOrderResDTO> createOrder(
            @RequestParam Long memberId,
            @Valid @RequestBody OrderReqDTO.CreateOrderReqDTO request
    ) {
        OrderResDTO.CreateOrderResDTO body = orderCommandService.createOrder(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Override
    @GetMapping("/members/me/orders")
    public ResponseEntity<OrderResDTO.MyOrderListResDTO> getMyOrders(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(orderQueryService.getMyOrders(memberId, page, size));
    }
}
