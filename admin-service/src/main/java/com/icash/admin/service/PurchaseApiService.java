package com.icash.admin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseApiService {

    @Value("${external.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public ResponseEntity<?> getTopSellingProducts(int limit) {
        String url = baseUrl + "/products/top-sellers?limit=" + limit;
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        return ResponseEntity
            .status(response.getStatusCode())
            .headers(response.getHeaders())
            .body(response.getBody());    
    }

    public ResponseEntity<?> getFrequentBuyers(long minPurchases) {
        String url = baseUrl + "/user/frequent-buyers?minPurchases=" + minPurchases;
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        return ResponseEntity
            .status(response.getStatusCode())
            .headers(response.getHeaders())
            .body(response.getBody());    
    }

    public ResponseEntity<?> getUserCount() {
        String url = baseUrl + "/user/count";
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        return ResponseEntity
            .status(response.getStatusCode())
            .headers(response.getHeaders())
            .body(response.getBody());    
    }
}