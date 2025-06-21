package com.icash.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.icash.purchase.dto.ProductSalesView;
import com.icash.purchase.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByNameIn(List<String> names);

    @Query(value = """
    SELECT p.id AS id, p.name AS name, p.price AS price, COUNT(*) AS salesCount
    FROM product p
    JOIN purchase_products pp ON p.id = pp.product_id
    GROUP BY p.id, p.name, p.price
    ORDER BY salesCount DESC
    LIMIT :limit
    """, nativeQuery = true)
    List<ProductSalesView> findTopSellingProducts(@Param("limit") int limit);
}