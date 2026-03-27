package com.shopwave.shopwave_starter.dto;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Long categoryId
) {
}