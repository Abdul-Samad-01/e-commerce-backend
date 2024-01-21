#Instructions To Run


**Step 1**


Run the Project


**Step 2**


### Alter Table


1. **Alter Table Query:**


   - Execute the following query to alter the `purchase_product_information` table:
     
     
     sql
     
     ALTER TABLE purchase_product_information DROP CONSTRAINT uk_ssk5hny34qmbylgiua1uk25l6;



### Insert Data into ROLE and USER_TABLE Tables


2. **Insert Data into ROLE Table:**
   - Execute the following queries to insert data into the `ROLE` table:
   
   
   sql
    
    
     -INSERT INTO ROLE(id, role) VALUES (1, 'ROLE_BUYER');
     -INSERT INTO ROLE VALUES (2, 'ROLE_SELLER');
     -INSERT INTO ROLE VALUES (3, 'ROLE_ADMIN');
     -INSERT INTO ROLE VALUES (4, 'ROLE_UNAPPROVEDSELLER');

3. **Insert Data into USER_TABLE Table:**
   - Execute the following queries to insert data into the `USER_TABLE` table:
     
     
     sql
     
     
     -INSERT INTO USER_TABLE(uuid, country_code, is_active, is_staff, is_super_user, last_login, last_logout, password, phone_number, user_name, role_id, email) VALUES (1, 'abc', true, true, true, '2024-01-18 12:35:02.337', '2024-01-18 12:35:02.337', '$2a$10$7zBqx.YuSc.TSljfqnbwseGKDTf5mmOgwZjuotK4h82b1o6SVjbqG', 'aa', 'admin', 3, 'a');
     
     
     -INSERT INTO USER_TABLE(uuid, country_code, is_active, is_staff, is_super_user, last_login, last_logout, password, phone_number, user_name, role_id, email) VALUES (2, 'abc', true, true, true, '2024-01-18 12:35:02.337', '2024-01-18 12:35:02.337', '$2a$10$wAtRroh20vj0pbT/ShDwluLtTWsMIjV65CJqArS3bHfVCbfaCEsh2', 'aa', 'seller', 2, 'b@gmail.com');
     
     
     -INSERT INTO USER_TABLE(uuid, country_code, is_active, is_staff, is_super_user, last_login, last_logout, password, phone_number, user_name, role_id, email) VALUES (3, 'abc', true, true, true, '2024-01-18 12:35:02.337', '2024-01-18 12:35:02.337', '$2a$10$8diai.qxXLVSAS6UV/M2/.2e.OGSLg8Kr4TkLjQYLGjJR.NkqIfd6', 'aa', 'buyer', 1, 'c@gmail.com');
     
     
    
# Seller Buyer Management API

## Overview

The E-Commerce Management API is a comprehensive solution for managing buyers, sellers, products, and purchase transactions in an e-commerce system. The API provides various endpoints for different user roles, including sellers, buyers, admins, and general users.

## Table of Contents

- [Product Controller](#product-controller)
- [Product Category Controller](#product-category-controller)
- [Purchase Transaction Controller](#purchase-transaction-controller)
- [User Controller](#user-controller)
- [Buyer Controller](#buyer-controller)
- [Admin Controller](#admin-controller)
- [Seller Controller](#seller-controller)

---

## Product Controller

### Get a Product by ID

- **Endpoint:** `GET /seller/products/{id}`
- **Description:** Get a product by its ID.

### Update an Existing Product

- **Endpoint:** `PUT /seller/products/{id}`
- **Description:** Update details of an existing product.

### Get All Products with Pagination

- **Endpoint:** `GET /seller/products`
- **Description:** Get all products with pagination support.

### Create a New Product

- **Endpoint:** `POST /seller/products`
- **Description:** Create a new product.

### Mark a Product Out of Stock

- **Endpoint:** `PATCH /seller/products/{productId}/out-of-stock`
- **Description:** Mark a product out of stock by its ID.

### Deactivate a Product

- **Endpoint:** `PATCH /seller/products/{productId}/deactivate`
- **Description:** Deactivate a product by its ID.

### Activate a Product

- **Endpoint:** `PATCH /seller/products/{productId}/activate`
- **Description:** Activate a product by its ID.

### Get Products by Category ID with Pagination

- **Endpoint:** `GET /seller/products/category/{categoryId}`
- **Description:** Get all products by their category ID with pagination support.

---

## Product Category Controller

### Get a Product Category by ID

- **Endpoint:** `GET /seller/product-categories/{id}`
- **Description:** Get a product category by its ID.

### Update an Existing Product Category

- **Endpoint:** `PUT /seller/product-categories/{id}`
- **Description:** Update details of an existing product category.

### Delete a Product Category

- **Endpoint:** `DELETE /seller/product-categories/{id}`
- **Description:** Delete a product category by its ID.

### Get All Product Categories with Pagination

- **Endpoint:** `GET /seller/product-categories`
- **Description:** Get all product categories with pagination support.

### Create a New Product Category

- **Endpoint:** `POST /seller/product-categories`
- **Description:** Create a new product category.

### Deactivate a Product Category

- **Endpoint:** `PATCH /seller/product-categories/{id}/deactivate`
- **Description:** Deactivate a product category by its ID.

### Activate a Product Category

- **Endpoint:** `PATCH /seller/product-categories/{id}/activate`
- **Description:** Activate a product category by its ID.

---

## Purchase Transaction Controller

### Update an Existing Purchase Transaction

- **Endpoint:** `PUT /buyer/purchase-transaction`
- **Description:** Update details of an existing purchase transaction.

### Create a New Purchase Transaction

- **Endpoint:** `POST /buyer/purchase-transaction`
- **Description:** Create a new purchase transaction.

---

## User Controller

### Register a New User

- **Endpoint:** `POST /user`
- **Description:** Register a new user.

---

## Buyer Controller

### Initiate a Refund for a Purchase

- **Endpoint:** `POST /buyer/refund`
- **Description:** Initiate a refund for a purchase.

### Find Purchase Transactions for a Buyer

- **Endpoint:** `GET /buyer/purchases`
- **Description:** Find purchase transactions for a buyer.

### Get All Products Available for Buyers

- **Endpoint:** `GET /buyer/products`
- **Description:** Get all products available for buyers.

### Search for Products by Title

- **Endpoint:** `GET /buyer/products/search`
- **Description:** Search for products by title.

---

## Admin Controller

### Get All Users with Pagination

- **Endpoint:** `GET /admin`
- **Description:** Get all users with pagination support.

### Create a New User

- **Endpoint:** `POST /admin`
- **Description:** Create a new user.

### Deactivate a User

- **Endpoint:** `PATCH /admin/{id}/deactivate`
- **Description:** Deactivate a user by their ID.

### Approve a User

- **Endpoint:** `PATCH /admin/{id}/approve`
- **Description:** Approve a user by their ID.

### Activate a User

- **Endpoint:** `PATCH /admin/{id}/activate`
- **Description:** Activate a user by their ID.

### Get a User by ID

- **Endpoint:** `GET /admin/{id}`
- **Description:** Get a user by their ID.

### Get a User Based on Their Role

- **Endpoint:** `GET /admin/role/{role}`
- **Description:** Get a user based on their role.

---

## Seller Controller

### Get a Page of Top Products Sold by the Seller

- **Endpoint:** `GET /seller/top-products`
- **Description:** Get a page of top products sold by the seller.

### Get a Page of Purchases Made from the Seller

- **Endpoint:** `GET /seller/purchases`
- **Description:** Get a page of purchases made from the seller.
