package com.sellerbuyer.sellerbuyer.repository;

import com.sellerbuyer.sellerbuyer.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);

    List<User> findByRole_Role(String role);
}
