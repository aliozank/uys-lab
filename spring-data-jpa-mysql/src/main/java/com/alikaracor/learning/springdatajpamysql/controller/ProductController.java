package com.alikaracor.learning.springdatajpamysql.controller;

import com.alikaracor.learning.springdatajpamysql.dto.ProductRequest;
import com.alikaracor.learning.springdatajpamysql.dto.ProductResponse;
import com.alikaracor.learning.springdatajpamysql.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest
    ) {
        ProductResponse productResponse =
                productService.createProduct(productRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productResponse);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(
            @PathVariable("productId") Long productId
    ) {
        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody ProductRequest productRequest
    ) {
        return productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("productId") Long productId
    ) {
        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProductsByName(
            @RequestParam String name
    ) {
        return productService.searchProductsByName(name);
    }

    @GetMapping("/price-range")
    public List<ProductResponse> getProductsByPriceRange(
            @RequestParam BigDecimal minimumPrice,
            @RequestParam BigDecimal maximumPrice
    ) {
        return productService.getProductsByPriceRange(
                minimumPrice,
                maximumPrice
        );
    }
}
