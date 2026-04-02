// NAME:MIHRETAB ESAYAS ID: ATE/7353/14
package com.shopwave.shopwave_starter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt
) {
}