// NAME:MIHRETAB ESAYAS ID: ATE/7353/14
package com.shopwave.shopwave_starter.mapper;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static Product toEntity(CreateProductRequest request, Category category) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .category(category)
                .build();
    }

    public static ProductDTO toDTO(Product product) {
        Category category = product.getCategory();

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null,
                product.getCreatedAt()
        );
    }
}