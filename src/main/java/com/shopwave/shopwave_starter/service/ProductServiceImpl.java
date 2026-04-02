// NAME:MIHRETAB ESAYAS ID: ATE/7353/14
package com.shopwave.shopwave_starter.service;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.exception.ProductNotFoundException;
import com.shopwave.shopwave_starter.mapper.ProductMapper;
import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {
        Category category = null;
        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Category not found with id: " + request.categoryId()));
        }

        Product product = ProductMapper.toEntity(request, category);
        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable).map(ProductMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        return ProductMapper.toDTO(findProduct(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {
        return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .filter(product -> maxPrice == null || product.getPrice().compareTo(maxPrice) <= 0)
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDTO updateStock(Long id, int delta) {
        Product product = findProduct(id);

        int currentStock = product.getStock() == null ? 0 : product.getStock();
        int finalStock = currentStock + delta;

        if (finalStock < 0) {
            throw new IllegalArgumentException("Final stock cannot be negative for product id: " + id);
        }

        product.setStock(finalStock);
        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    protected Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}