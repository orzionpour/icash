package com.icash.purchase.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "purchase_products")
@Data
public class PurchaseProduct {

    @EmbeddedId
    private PurchaseProductId id;

    public PurchaseProduct(Integer purchaseId, Integer productId) {
        this.id = new PurchaseProductId(purchaseId, productId);
    }

    protected PurchaseProduct() {
    }
}