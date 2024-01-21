package com.sellerbuyer.sellerbuyer.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sellerbuyer.sellerbuyer.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ProductCategory extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;
    private String categoryName;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
