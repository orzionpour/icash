package com.icash.purchase.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icash.purchase.dto.ProductSalesView;
import com.icash.purchase.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/top-sellers")
    public ResponseEntity<List<ProductSalesView>> getTopSellingProducts(
        @RequestParam(defaultValue = "3") int limit
    ) {
        List<ProductSalesView> topProducts = productRepository.findTopSellingProducts(limit);
        return ResponseEntity.ok(topProducts);
    }
}
