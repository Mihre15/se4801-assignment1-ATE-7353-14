package com.shopwave.shopwave_starter.controller;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductDTO created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(productService.searchProducts(keyword, BigDecimal.valueOf(maxPrice)));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable Long id,
            @RequestBody StockDeltaRequest request
    ) {
        return ResponseEntity.ok(productService.updateStock(id, request.delta()));
    }
}