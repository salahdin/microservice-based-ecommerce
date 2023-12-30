package com.ecommerce.productservice.service;

import com.ecommerce.productservice.adapter.ProductAdapter;
import com.ecommerce.productservice.dto.ProductResponse;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ecommerce.productservice.dto.ProductRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductAdapter productAdapter;
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = productAdapter.toProduct(productRequest);
        productRepository.save(product);
        log.info("{} Product created with id {}", product.getName(), product.getId());
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ProductResponse getProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        ProductResponse productResponse = productAdapter.toProductResponse(product);
        log.info("{} Product found with id {}", product.getName(), product.getId());
        return productResponse;
    }
}
