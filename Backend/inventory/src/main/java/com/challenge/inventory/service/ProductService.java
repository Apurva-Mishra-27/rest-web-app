package com.challenge.inventory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.inventory.exception.CustomException;
import com.challenge.inventory.model.Product;
import com.challenge.inventory.repository.ProductRepository;

@Service
public class ProductService implements ProductServiceInterface {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product createUpdateProduct(Product product) {
		Product createdProduct=null;
		try {
		createdProduct=productRepository.save(product);
		if(createdProduct==null)
			throw new CustomException("ES_9001","Unable  to create/update product.");
		}catch(CustomException e) {
			throw e;
		}catch(Exception e) {
			throw new CustomException("ES_9999","Something went wrong while creating/updating product."+e.getMessage());
		}
		return createdProduct;
	}

	@Override
	public List<Product> findAllProducts() {
		List<Product> products=new ArrayList<>();
		try {
		products=productRepository.findAll();
			if(products.size()==0 || products==null)
				throw new CustomException("ES_9002","Table is empty.");
		}catch(CustomException e) {
			throw e;
		}catch(Exception e) {
			throw new CustomException("ES_9998","Something went wrong while retriving all products."+e.getMessage());
		}
		return products;
	}

	@Override
	public Product findById(String productId) {
		Product receivedProduct=null;
		try {
		receivedProduct=productRepository.findByProductId(productId);
		if(receivedProduct==null)
			throw new CustomException("ES_9003","Product with given productId not present.");
		}catch(CustomException e) {
			throw e;
		}catch(Exception e) {
			throw new CustomException("ES_9997","Something went wrong while retriving product with given productId."+e.getMessage());
		}
		return receivedProduct;
	}

	@Override
	public List<Product> findByProductCategory(String productCategory) {
		List<Product> products=new ArrayList<>();
		try {
		products=productRepository.findByProductCategory(productCategory);
			if(products.size()==0 || products==null)
				throw new CustomException("ES_9004","No record found with given category.");
		}catch(CustomException e) {
			throw e;
		}catch(Exception e) {
			throw new CustomException("ES_9996","Something went wrong while retriving products with given category."+e.getMessage());
		}
		return products;
	}

	@Override
	public void deleteProduct(Product product) {
		try {
		productRepository.delete(product);
		}catch(Exception e) {
			throw new CustomException("ES_9995","Something went wrong while deleting product with given product Id"+e.getMessage());
		}
		
	}

}
