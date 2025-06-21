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
                SELECT name, sales_count
                FROM product_sales_rank
                WHERE rnk <= 3
                ORDER BY sales_count DESC;
            """, nativeQuery = true)
    List<ProductSalesView> findTopSellingProducts(@Param("limit") int limit);
}