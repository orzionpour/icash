package com.icash.purchase.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class PurchaseProductId implements Serializable {
    @Column(name = "purchase_id")
    private Integer purchaseId;

    @Column(name = "product_id")
    private Integer productId;

    public PurchaseProductId() {}
    public PurchaseProductId(Integer purchaseId, Integer productId) {
        this.purchaseId = purchaseId;
        this.productId = productId;
    }
}