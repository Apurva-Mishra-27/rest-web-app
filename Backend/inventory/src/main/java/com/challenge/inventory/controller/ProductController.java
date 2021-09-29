package com.challenge.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.challenge.inventory.VO.ProductVO;
import com.challenge.inventory.exception.CustomException;
import com.challenge.inventory.model.Product;
import com.challenge.inventory.service.ProductServiceInterface;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins= "http://localhost:4200")
public class ProductController {
	
	@Autowired
	ProductServiceInterface productServiceInterface;
	
	@GetMapping("")
	public ResponseEntity<?> getAllProducts(){
		List<ProductVO> productVOs=new ArrayList<ProductVO>();
		try {
		List<Product> products=productServiceInterface.findAllProducts();
		if(products.isEmpty()) {
			throw new CustomException("EC_8001","Product table is empty.");
		}
		for(Product product:products)
		{	
			ProductVO productVO=new ProductVO();
			productVO.setProductId(product.getProductId());
			productVO.setProductCategory(product.getProductCategory());
			productVO.setProductName(product.getProductName());
			productVO.setProductDescription(product.getProductDescription());
			productVO.setUnits(product.getUnits().toString());
			productVOs.add(productVO);
		}
		}catch(CustomException e) {
			throw e;
		}catch (Exception e) {
			throw new CustomException("EC_8999","Something went wrong while fetching product details. "+e.getMessage());
		}
		return new ResponseEntity<List<ProductVO>>(productVOs,HttpStatus.OK);
		
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable("productId") String productId){
		ProductVO productVO= new ProductVO();
		if(productId.isEmpty() || productId.length() ==0)
		{	CustomException ce= new CustomException("EC_8002","Product id cannot be empty or null while fetching details by Id.");
			return new ResponseEntity<CustomException>(ce,HttpStatus.BAD_REQUEST);
		}
		try {
			Product product=productServiceInterface.findById(productId);
			if(product==null)
			{
				throw new CustomException("EC_8003","No product with given product id found");
			}
			else
			{
				productVO.setProductId(product.getProductId());
				productVO.setProductCategory(product.getProductCategory());
				productVO.setProductDescription(product.getProductDescription());
				productVO.setProductName(product.getProductName());
				productVO.setUnits(product.getUnits().toString());
			}
		}catch(CustomException e) {
			throw e;
		}catch(Exception e) {
			throw new CustomException("EC_8998","Something went wrong while fetching product details by id."+ e.getMessage());
		}
		return new ResponseEntity<ProductVO>(productVO,HttpStatus.OK);
	}
	
	@GetMapping("/category/{productCategory}")
	public ResponseEntity<List<ProductVO>> getProductByProductCategory(@PathVariable("productCategory") String productCategory){
		List<ProductVO> productVOs=new ArrayList<ProductVO>();
			if(productCategory.isEmpty() || productCategory.length() ==0)
				throw new CustomException("EC_8004","Product Category cannot be empty or null while fetching details by category.");
			try {
				List<Product> products=productServiceInterface.findByProductCategory(productCategory);
				if(products.isEmpty())
					throw new CustomException("EC_8005","No Product found with this category.");
				for(Product product:products)
				{	
					ProductVO productVO=new ProductVO();
					productVO.setProductId(product.getProductId());
					productVO.setProductCategory(product.getProductCategory());
					productVO.setProductName(product.getProductName());
					productVO.setProductDescription(product.getProductDescription());
					productVO.setUnits(product.getUnits().toString());
					productVOs.add(productVO);
				}
			}catch(CustomException e) {
				throw e;
			}catch(Exception e) {
				throw new CustomException("EC_8997","Something went wrong while fetching product details by category."+ e.getMessage());
			}
			return new ResponseEntity<List<ProductVO>>(productVOs,HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<ProductVO> addProduct(@RequestBody ProductVO productVO) throws CustomException{
		ProductVO createdProductVO=new ProductVO();
		try {
		if(productVO.getProductId().isEmpty()|| productVO.getProductId().length()==0)
			throw new CustomException("EC_8006","Product Id cannot be null or empty.");
		
		if(productVO.getProductName().isEmpty()|| productVO.getProductName().length()==0)
			throw new CustomException("EC_8007","Product Name cannot be null or empty.");
		
		if(productVO.getProductCategory().isEmpty()|| productVO.getProductCategory().length()==0)
			throw new CustomException("EC_8008","Product Id cannot be null or empty.");
		
		if(productVO.getProductDescription().isEmpty() || productVO.getProductDescription().length()==0)
			throw new CustomException("EC_8009","Product Description cannot be null or empty.");
		
		if(productVO.getUnits().isEmpty() || productVO.getUnits().length()==0)
			throw new CustomException("EC_8010","Units cannot be null or empty.");
		
		if(Long.parseLong(productVO.getUnits())<0)
				throw new CustomException("EC_8011","Units cannot be less than 0");
		
		
		Product product=new Product();
		product.setProductId(productVO.getProductId());
		product.setProductCategory(productVO.getProductCategory());
		product.setProductName(productVO.getProductName());
		product.setProductDescription(productVO.getProductDescription());
		product.setUnits(Long.parseLong(productVO.getUnits()));
		
			Product createdProduct=productServiceInterface.createUpdateProduct(product);
			if(createdProduct.getId()!=null)
			{	
				System.out.println("Successfully created product.");
				createdProductVO.setProductId(createdProduct.getProductId());
				createdProductVO.setProductCategory(createdProduct.getProductCategory());
				createdProductVO.setProductName(createdProduct.getProductName());
				createdProductVO.setProductDescription(createdProduct.getProductDescription());
				createdProductVO.setUnits(createdProduct.getUnits().toString());
			}
			else
				throw new CustomException("EC_8012","Product created with id as null.");
		}catch(CustomException e)
		{
			throw e;
		}catch(NumberFormatException e)
		{
			throw new CustomException("EC_8013","Only numbers are allowed in units");
		}catch(IllegalArgumentException e)
		{
			throw new CustomException("EC_8014","Input given cannot be null." +e.getMessage());
		}catch(Exception e)
		{
			throw new CustomException("EC_8996","Something went wrong in creating product."+e.getMessage());
		}
		
		return new ResponseEntity<ProductVO>(createdProductVO,HttpStatus.OK);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductVO> updateProduct(@PathVariable("productId") String productId, @RequestBody ProductVO productVO) throws CustomException{
		Product toBeUpdatedProduct=new Product();
		ProductVO updatedProductVO=new ProductVO();
		if(productId.isEmpty() || productId.length() ==0)
			throw new CustomException("EC_8015","Product Id cannot be empty or null while updating product.");
		try {
			Product product=productServiceInterface.findById(productId);
			    if(product==null)
				throw new CustomException("EC_8003","No product with given product id found");

				if(!productVO.getProductId().equals(product.getProductId()))
				{
					throw new CustomException("EC_8016","ProductId cannot be updated");
				}
	
				toBeUpdatedProduct.setProductId(productId);

				System.out.println(toBeUpdatedProduct.getProductId());
				toBeUpdatedProduct.setId(product.getId());
				if(productVO.getProductCategory().isEmpty()||productVO.getProductCategory().length()==0)
					toBeUpdatedProduct.setProductCategory(product.getProductCategory());
				else
					toBeUpdatedProduct.setProductCategory(productVO.getProductCategory());
				
				if(productVO.getProductName().isEmpty()||productVO.getProductName().length()==0)
					toBeUpdatedProduct.setProductName(product.getProductName());
				else
					toBeUpdatedProduct.setProductName(productVO.getProductName());
				
				if(productVO.getProductDescription().isEmpty()||productVO.getProductDescription().length()==0)
					toBeUpdatedProduct.setProductDescription(product.getProductDescription());
				else
					toBeUpdatedProduct.setProductDescription(productVO.getProductDescription());
				
				if(productVO.getUnits().isEmpty()||productVO.getUnits().length()==0)
					toBeUpdatedProduct.setUnits(product.getUnits());
				else
					toBeUpdatedProduct.setUnits(Long.parseLong(productVO.getUnits()));
				
				Product updatedProduct=productServiceInterface.createUpdateProduct(toBeUpdatedProduct);
				if(updatedProduct.getId()!=null)
				{	
					System.out.println("Successfully created product.");
					updatedProductVO.setProductId(updatedProduct.getProductId());
					updatedProductVO.setProductCategory(updatedProduct.getProductCategory());
					updatedProductVO.setProductName(updatedProduct.getProductName());
					updatedProductVO.setProductDescription(updatedProduct.getProductDescription());
					updatedProductVO.setUnits(updatedProduct.getUnits().toString());
				}
				else
					throw new CustomException("EC_8017","Product updated with id as null.");
			
		}catch(CustomException e)
		{
			throw e;
		}catch(NumberFormatException e)
		{
			throw new CustomException("EC_8013","Only numbers are allowed in units");
		}catch(IllegalArgumentException e)
		{
			throw new CustomException("EC_8018","Input given cannot be null." +e.getMessage());
		}catch(Exception e)
		{
			throw new CustomException("EC_8995","Something went wrong in updating product."+e.getMessage());
		}
		return new ResponseEntity<ProductVO>(updatedProductVO,HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") String productId)
	{
		if(productId.isEmpty() || productId.length() ==0)
			throw new CustomException("EC_8019","Product Id cannot be empty or null while deleting product");
		try {
			Product product=productServiceInterface.findById(productId);
			if(product==null)
				throw new CustomException("EC_8003","No product with given product id found");
			
			productServiceInterface.deleteProduct(product);
		}catch(CustomException e)
		{
			throw e;
		}catch(Exception e)
		{
			throw new CustomException("EC_8994","Something went wrong in deleting product."+e.getMessage());
			
		}
		return new ResponseEntity<String>("Product deleted successfully.",HttpStatus.OK);
		
	}
	
}
