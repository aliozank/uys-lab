package com.alikaracor.learning.springdatajpamysql.repository;

import com.alikaracor.learning.springdatajpamysql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(
            BigDecimal minimumPrice,
            BigDecimal maximumPrice
    );
}
