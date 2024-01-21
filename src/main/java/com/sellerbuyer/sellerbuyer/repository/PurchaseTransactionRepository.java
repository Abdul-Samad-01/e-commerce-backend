package com.sellerbuyer.sellerbuyer.repository;

import com.sellerbuyer.sellerbuyer.entity.PurchaseTransaction;
import com.sellerbuyer.sellerbuyer.entity.User;
import com.sellerbuyer.sellerbuyer.enumClasses.TransactionStatus;
import com.sellerbuyer.sellerbuyer.enumClasses.TypeOfTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransaction,
        Long> {
    Optional<PurchaseTransaction> findByOrderId(String orderId);

    Page<PurchaseTransaction> findByPurchasedByIdAndTransactionStatusAndTransactionType(
            Pageable pageable, User user, TransactionStatus status,
            TypeOfTransaction type);

    Optional<PurchaseTransaction> findByPurchasedByIdAndTransactionStatusAndTransactionTypeAndOrderId(
            User user, TransactionStatus status, TypeOfTransaction type,
            String orderId
    );
}
