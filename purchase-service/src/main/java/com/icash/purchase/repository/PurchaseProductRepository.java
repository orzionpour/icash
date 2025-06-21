package com.icash.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icash.purchase.entity.PurchaseProduct;
import com.icash.purchase.entity.PurchaseProductId;

public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, PurchaseProductId> {}
