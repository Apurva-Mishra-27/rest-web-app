package com.challenge.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.inventory.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

	Product findByProductId(String productId);

	List<Product> findByProductCategory(String productCategory);

}
