// NAME:MIHRETAB ESAYAS ID: ATE/7353/14
package com.shopwave.shopwave_starter.service;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_shouldSaveProduct_andReturnDto_whenCategoryExists() {
        Category category = Category.builder()
                .id(10L)
                .name("Electronics")
                .build();

        CreateProductRequest request = new CreateProductRequest(
                "Laptop",
                "Gaming laptop",
                new BigDecimal("1299.99"),
                5,
                10L
        );

        Product savedProduct = Product.builder()
                .id(1L)
                .name("Laptop")
                .description("Gaming laptop")
                .price(new BigDecimal("1299.99"))
                .stock(5)
                .category(category)
                .build();

        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDTO result = productService.createProduct(request);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Laptop", result.name());
        assertEquals("Gaming laptop", result.description());
        assertEquals(new BigDecimal("1299.99"), result.price());
        assertEquals(5, result.stock());
        assertEquals(10L, result.categoryId());
        assertEquals("Electronics", result.categoryName());

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals("Laptop", capturedProduct.getName());
        assertEquals("Gaming laptop", capturedProduct.getDescription());
        assertEquals(new BigDecimal("1299.99"), capturedProduct.getPrice());
        assertEquals(5, capturedProduct.getStock());
        assertSame(category, capturedProduct.getCategory());

        verify(categoryRepository).findById(10L);
        verifyNoMoreInteractions(categoryRepository, productRepository);
    }

    @Test
    void createProduct_shouldThrowException_whenCategoryDoesNotExist() {
        CreateProductRequest request = new CreateProductRequest(
                "Laptop",
                "Gaming laptop",
                new BigDecimal("1299.99"),
                5,
                10L
        );

        when(categoryRepository.findById(10L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.createProduct(request)
        );

        assertEquals("Category not found with id: 10", exception.getMessage());

        verify(categoryRepository).findById(10L);
        verify(productRepository, never()).save(any());
    }
    //both passed

}