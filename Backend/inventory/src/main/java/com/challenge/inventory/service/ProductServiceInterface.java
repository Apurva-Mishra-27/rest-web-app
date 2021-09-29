package com.challenge.inventory.service;

import java.util.List;

import com.challenge.inventory.model.Product;

public interface ProductServiceInterface {

	Product createUpdateProduct(Product product);

	List<Product> findAllProducts();

	Product findById(String productId);

	List<Product> findByProductCategory(String productCategory);

	void deleteProduct(Product product);

}
