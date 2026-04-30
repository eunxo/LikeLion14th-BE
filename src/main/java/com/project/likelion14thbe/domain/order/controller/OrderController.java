package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.controller.docs.OrderDocs;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController implements OrderDocs {

    @Override
    @PostMapping("/orders")
    public ResponseEntity<OrderResDTO.CreateOrderResDTO> createOrder(
            @Valid @RequestBody OrderReqDTO.CreateOrderReqDTO request
    ) {
        int totalPrice = request.quantity() * 3000;
        OrderResDTO.CreateOrderResDTO body = new OrderResDTO.CreateOrderResDTO(
                100L,
                request.productId(),
                request.quantity(),
                totalPrice,
                "PENDING",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Override
    @GetMapping("/members/me/orders")
    public ResponseEntity<OrderResDTO.MyOrderListResDTO> getMyOrders(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        List<OrderResDTO.MyOrderItemDTO> orderList = List.of(
                new OrderResDTO.MyOrderItemDTO(
                        100L, 5L, "사과", 2, 6000, "DELIVERED", LocalDateTime.now()
                )
        );
        OrderResDTO.MyOrderListResDTO body = new OrderResDTO.MyOrderListResDTO(
                12L, 2, page, size, false, orderList
        );
        return ResponseEntity.ok(body);
    }
}
