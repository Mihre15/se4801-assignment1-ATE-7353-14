package com.shopwave.shopwave_starter.repository;

import com.shopwave.shopwave_starter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByPriceLessThanEqual(BigDecimal maxPrice);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    Optional<Product> findTopByOrderByPriceDesc();
}