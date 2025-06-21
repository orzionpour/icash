package com.icash.purchase.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class PurchaseRequest {
    private String supermarketId;
    private UUID userId;
    private LocalDateTime timestamp;
    private List<String> products;
}