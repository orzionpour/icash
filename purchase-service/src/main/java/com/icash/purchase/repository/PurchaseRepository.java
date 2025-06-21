package com.icash.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icash.purchase.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
}
