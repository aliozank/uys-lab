package com.alikaracor.learning.springdatajpamysql.service;

import com.alikaracor.learning.springdatajpamysql.dto.ProductRequest;
import com.alikaracor.learning.springdatajpamysql.dto.ProductResponse;
import com.alikaracor.learning.springdatajpamysql.model.Product;
import com.alikaracor.learning.springdatajpamysql.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        if (productRepository.existsByNameIgnoreCase(productRequest.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bu isimde bir ürün zaten kayıtlı"
            );
        }

        Product product = new Product();
        updateProductFields(product, productRequest);

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse getProductById(Long productId) {
        return toResponse(findProductById(productId));
    }

    @Transactional
    public ProductResponse updateProduct(
            Long productId,
            ProductRequest productRequest
    ) {
        Product product = findProductById(productId);

        if (productRepository.existsByNameIgnoreCaseAndIdNot(
                productRequest.getName(),
                productId
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bu isimde başka bir ürün zaten kayıtlı"
            );
        }

        updateProductFields(product, productRequest);

        return toResponse(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = findProductById(productId);

        productRepository.delete(product);
    }

    public List<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductResponse> getProductsByPriceRange(
            BigDecimal minimumPrice,
            BigDecimal maximumPrice
    ) {
        if (minimumPrice.compareTo(maximumPrice) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Minimum fiyat maksimum fiyattan büyük olamaz"
            );
        }

        return productRepository.findByPriceBetween(
                        minimumPrice,
                        maximumPrice
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bu id ile eşleşen ürün bulunamadı"
                ));
    }

    private void updateProductFields(
            Product product,
            ProductRequest productRequest
    ) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
    }

    private ProductResponse toResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock(product.getStock());

        return productResponse;
    }
}
