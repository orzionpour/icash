package com.icash.purchase.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "purchase")
@Getter
@Setter
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "supermarket_id")
    private String supermarketId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "total_amount")
    private Double totalAmount;

}
