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
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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
    public ResponseEntity<ProductResDTO.ProductListResDTO> getProductList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(productQueryService.getProductList(page, size));
    }

    @Override
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResDTO.ProductDetailResDTO> getProduct(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(productQueryService.getProduct(productId));
    }

    @Override
    @PostMapping
    public ResponseEntity<ProductResDTO.CreateProductResDTO> createProduct(
            @Valid @RequestBody ProductReqDTO.CreateProductReqDTO request
    ) {
        ProductResDTO.CreateProductResDTO body = productCommandService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
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
    public ResponseEntity<ProductResDTO.DeleteProductResDTO> deleteProduct(
            @PathVariable Long productId
    ) {
        ProductResDTO.DeleteProductResDTO body = new ProductResDTO.DeleteProductResDTO(
                productId,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(body);
    }
}
