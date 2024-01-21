package com.sellerbuyer.sellerbuyer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseProductInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Product product;

    private String title;

    private String description;

    private double price;

    private long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private PurchaseTransaction purchaseTransaction;
}
