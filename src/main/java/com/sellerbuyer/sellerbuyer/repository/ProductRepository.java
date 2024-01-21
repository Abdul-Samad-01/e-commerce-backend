package com.sellerbuyer.sellerbuyer.repository;

import com.sellerbuyer.sellerbuyer.entity.Product;
import com.sellerbuyer.sellerbuyer.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product,UUID> {
    Page<Product> findByCreatedBy(User seller, Pageable pageable);

    Page<Product> findByTitleContaining(String title, Pageable pageable);

    Page<Product> findByProductCategory_CategoryId(Long categoryId,Pageable pageable);
}
