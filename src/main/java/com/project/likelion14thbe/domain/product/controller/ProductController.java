package com.project.likelion14thbe.domain.product.controller;

import com.project.likelion14thbe.domain.product.controller.docs.ProductDocs;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController implements ProductDocs {

    @Override
    @GetMapping
    public ResponseEntity<ProductResDTO.ProductListResDTO> getProductList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        List<ProductResDTO.ProductItemDTO> productList = List.of(
                new ProductResDTO.ProductItemDTO(
                        1L, "사과", 3000, "과일", "https://example.com/thumb/1.jpg"
                ),
                new ProductResDTO.ProductItemDTO(
                        2L, "바나나", 2000, "과일", "https://example.com/thumb/2.jpg"
                )
        );
        ProductResDTO.ProductListResDTO body = new ProductResDTO.ProductListResDTO(
                50L, 5, page, size, false, productList
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResDTO.ProductDetailResDTO> getProduct(
            @PathVariable Long productId
    ) {
        ProductResDTO.ProductDetailResDTO body = new ProductResDTO.ProductDetailResDTO(
                productId,
                "사과",
                3000,
                "과일",
                "신선한 사과입니다.",
                "https://example.com/thumb/1.jpg",
                LocalDateTime.now()
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @PostMapping
    public ResponseEntity<ProductResDTO.CreateProductResDTO> createProduct(
            @Valid @RequestBody ProductReqDTO.CreateProductReqDTO request
    ) {
        ProductResDTO.CreateProductResDTO body = new ProductResDTO.CreateProductResDTO(
                51L,
                request.name(),
                request.price(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
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
