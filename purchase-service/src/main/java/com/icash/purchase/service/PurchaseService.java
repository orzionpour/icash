package com.icash.purchase.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.icash.purchase.dto.PurchaseRequest;
import com.icash.purchase.entity.Product;
import com.icash.purchase.entity.Purchase;
import com.icash.purchase.entity.PurchaseProduct;
import com.icash.purchase.entity.User;
import com.icash.purchase.repository.ProductRepository;
import com.icash.purchase.repository.PurchaseProductRepository;
import com.icash.purchase.repository.PurchaseRepository;
import com.icash.purchase.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final UserRepository userRepository;

    /**
     * Registers a purchase and links products to it.
     * @param request the purchase request DTO
     * @return the saved Purchase entity
     */
    @Transactional
    public Purchase registerPurchase(PurchaseRequest request) {
        UUID userId = resolveUserId(request.getUserId());
        List<Product> products = fetchAndValidateProducts(request.getProducts());
        double totalAmount = calculateTotalAmount(products);

        Purchase purchase = new Purchase();
        purchase.setSupermarketId(request.getSupermarketId());
        purchase.setUserId(userId);
        purchase.setTimestamp(request.getTimestamp() != null ? request.getTimestamp() : LocalDateTime.now());
        purchase.setTotalAmount(totalAmount);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        List<PurchaseProduct> links = products.stream()
            .map(product -> new PurchaseProduct(savedPurchase.getId(), product.getId()))
            .toList();

        purchaseProductRepository.saveAll(links);

        return savedPurchase;
    }

    private UUID resolveUserId(UUID userId) {
        if (userId == null) {
            User user = new User();
            User savedUser = userRepository.save(user);
            return savedUser.getId();
        } else if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with ID " + userId + " does not exist.");
        }
        return userId;
    }

    private List<Product> fetchAndValidateProducts(List<String> productNames) {
        List<Product> products = productRepository.findAllByNameIn(productNames);
        Set<String> foundNames = products.stream().map(Product::getName).collect(Collectors.toSet());
        List<String> missingNames = productNames.stream().filter(name -> !foundNames.contains(name)).toList();
        if (!missingNames.isEmpty()) {
            throw new IllegalArgumentException("Products not found: " + missingNames);
        }
        return products;
    }

    private double calculateTotalAmount(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}