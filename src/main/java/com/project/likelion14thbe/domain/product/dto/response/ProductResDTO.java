package com.project.likelion14thbe.domain.product.dto.response;

import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public class ProductResDTO {

    @Builder
    public record ProductGetRes(
            List<ProductInfo> datalist
    ) {

        // 리스트 안에 들어갈 개별 주문 데이터
        @Builder
        public record ProductInfo(
                Long productId,
                String name,
                Double price,
                String photo
        ) {
        }
    }

    @Builder
    public record ProductGetDeatilRes(
            Long productId,
            String name,
            Double price,
            float rating,
            String description
    ){

    }
}