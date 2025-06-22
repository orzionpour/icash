package com.icash.purchase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icash.purchase.dto.PurchaseRequest;
import com.icash.purchase.entity.Purchase;
import com.icash.purchase.service.PurchaseService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Purchase> registerPurchase(@RequestBody PurchaseRequest request) {
        Purchase purchase = purchaseService.registerPurchase(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(purchase);
    }
}
