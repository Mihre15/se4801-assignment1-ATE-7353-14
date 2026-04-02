// NAME:MIHRETAB ESAYAS ID: ATE/7353/14
package com.shopwave.shopwave_starter.repository;

import com.shopwave.shopwave_starter.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByNameContainingIgnoreCase_shouldReturnMatchingProducts() {
        productRepository.save(Product.builder()
                .name("iPhone 15")
                .description("Apple phone")
                .price(new BigDecimal("999.99"))
                .stock(10)
                .build());

        productRepository.save(Product.builder()
                .name("Samsung Galaxy")
                .description("Android phone")
                .price(new BigDecimal("899.99"))
                .stock(8)
                .build());

        productRepository.save(Product.builder()
                .name("Laptop")
                .description("Gaming laptop")
                .price(new BigDecimal("1299.99"))
                .stock(5)
                .build());

        List<Product> result = productRepository.findByNameContainingIgnoreCase("phone");

        assertThat(result)
                .hasSize(1)
                .extracting(Product::getName)
                .containsExactly("iPhone 15");
    }
}