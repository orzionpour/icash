package com.icash.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icash.admin.service.PurchaseApiService;

import lombok.RequiredArgsConstructor;

/**
 * Admin endpoints for analytics and user statistics.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PurchaseApiService purchaseApiService;

    /**
     * Get top selling products.
     * @param limit number of products to return (default 3)
     */
    @GetMapping("/top-products")
    public ResponseEntity<?> getTopProducts() {
        return purchaseApiService.getTopSellingProducts(3);
    }

    /**
     * Get loyal buyers (users with at least minPurchases).
     * @param minPurchases minimum purchases (default 3)
     */
    @GetMapping("/loyal-buyers")
    public ResponseEntity<?> getLoyalBuyers() {
        return purchaseApiService.getFrequentBuyers(3);
    }

    /**
     * Get total user count.
     */
    @GetMapping("/user-count")
    public ResponseEntity<?> getUserCount() {
        return purchaseApiService.getUserCount();
    }
}