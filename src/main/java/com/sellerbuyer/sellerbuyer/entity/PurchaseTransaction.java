package com.sellerbuyer.sellerbuyer.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sellerbuyer.sellerbuyer.audit.Auditable;
import com.sellerbuyer.sellerbuyer.enumClasses.PaymentMethod;
import com.sellerbuyer.sellerbuyer.enumClasses.TransactionStatus;
import com.sellerbuyer.sellerbuyer.enumClasses.TypeOfTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseTransaction extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tId;

    private String originalTransactionId;

    private double amount;

    private TypeOfTransaction transactionType;

    private TransactionStatus transactionStatus;

    private PaymentMethod paymentMethod;

    private String orderId;

    private String refundId;

    @OneToMany(mappedBy = "purchaseTransaction", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PurchaseProductInformation>
            purchaseProductInformationList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User purchasedById;

    public void addProductInformation(PurchaseProductInformation productInformation) {
        purchaseProductInformationList.add(productInformation);
        productInformation.setPurchaseTransaction(this);
    }

    public void removeProductInformation(PurchaseProductInformation productInformation) {
        purchaseProductInformationList.remove(productInformation);
        productInformation.setPurchaseTransaction(null);
    }
}
