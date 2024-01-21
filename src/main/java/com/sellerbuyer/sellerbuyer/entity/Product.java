package com.sellerbuyer.sellerbuyer.entity;

import com.sellerbuyer.sellerbuyer.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Product extends Auditable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID productId;
    private boolean isActive;
    private String title;
    private String description;
    private long stockCount;
    private double price;
    private double sellingPrice;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;
}
