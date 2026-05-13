package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.controller.docs.ProductDocs;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.service.command.ProductCommandService;
import com.project.likelion14thbe.domain.product.service.query.ProductQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController implements ProductDocs {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @Override
    @GetMapping
    public CustomResponse<ProductResDTO.ProductListResDTO> getProductList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK, "상품 목록 조회 성공", productQueryService.getProductList(page, size));
    }

    @Override
    @GetMapping("/{productId}")
    public CustomResponse<ProductResDTO.ProductDetailResDTO> getProduct(
            @PathVariable Long productId
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK, "상품 상세 조회 성공", productQueryService.getProduct(productId));
    }

    @Override
    @PostMapping
    public CustomResponse<ProductResDTO.CreateProductResDTO> createProduct(
            @Valid @RequestBody ProductReqDTO.CreateProductReqDTO request
    ) {
        ProductResDTO.CreateProductResDTO body = productCommandService.createProduct(request);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "상품 등록 성공", body);
    }

    @Override
    @PatchMapping("/{productId}")
    public CustomResponse<ProductResDTO.UpdateProductResDTO> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductReqDTO.UpdateProductReqDTO request
    ) {
        return CustomResponse.onSuccess(productCommandService.updateProduct(productId, request));
    }

    @Override
    @DeleteMapping("/{productId}")
    public CustomResponse<String> deleteProduct(
            @PathVariable Long productId
    ) {
        productCommandService.deleteProduct(productId);
        return CustomResponse.onSuccess("상품 삭제 성공");
    }
}
