package com.icash.purchase.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.icash.purchase.entity.Product;
import com.icash.purchase.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Retrieves products by their names and validates that all requested products
     * exist.
     * 
     * @param productNames the list of product names to retrieve
     * @return a list of Product entities corresponding to the provided names
     * @throws IllegalArgumentException if any of the requested products are not
     *                                  found
     */
    public List<Product> getByNames(List<String> productNames) {
        List<Product> products = productRepository.findAllByNameIn(productNames);
        Set<String> foundNames = products.stream().map(Product::getName).collect(Collectors.toSet());
        List<String> missingNames = productNames.stream().filter(name -> !foundNames.contains(name)).toList();

        if (!missingNames.isEmpty()) {
            throw new IllegalArgumentException("Products not found: " + missingNames);
        }

        return products;
    }
}
