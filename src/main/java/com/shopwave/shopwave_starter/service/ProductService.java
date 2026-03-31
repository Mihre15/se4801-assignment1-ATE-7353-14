package com.shopwave.shopwave_starter.service;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDTO createProduct(CreateProductRequest request);

    Page<ProductDTO> getAllProducts(int page, int size);

    ProductDTO getProductById(Long id);

    List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice);

    ProductDTO updateStock(Long id, int delta);
}