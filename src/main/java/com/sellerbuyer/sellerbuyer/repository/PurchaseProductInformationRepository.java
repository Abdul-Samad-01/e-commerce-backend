package com.sellerbuyer.sellerbuyer.repository;

import com.sellerbuyer.sellerbuyer.entity.PurchaseProductInformation;
import com.sellerbuyer.sellerbuyer.entity.TopProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseProductInformationRepository extends JpaRepository<PurchaseProductInformation, Long> {

    @Query("SELECT new com.sellerbuyer.sellerbuyer.entity.TopProduct(ppi.product as product, SUM(ppi.quantity) as totalQuantity) FROM PurchaseProductInformation ppi WHERE ppi.product.createdBy.uuid = :sellerId AND ppi.purchaseTransaction.transactionType != 1 GROUP BY ppi.product ORDER BY totalQuantity DESC")
    Page<TopProduct> findTopProductsBySeller(@Param("sellerId") Long sellerId,Pageable pageable);

    @Query("SELECT ppi FROM PurchaseProductInformation ppi WHERE ppi.product.createdBy.uuid  = :sellerId")
    Page<PurchaseProductInformation> findPurchasesBySellerId(@Param("sellerId") Long sellerId, Pageable pageable);
}
