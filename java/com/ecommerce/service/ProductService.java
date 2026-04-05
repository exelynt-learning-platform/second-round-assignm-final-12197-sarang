package com.ecommerce.service;

import java.util.List;

import com.ecommerce.entity.Product;

public interface ProductService {

	Product createProduct(Product p);

	List<Product> getAllProducts();

	Product getProductById(Long id);

	Product updateProduct(Long id, Product p);

	void deleteProduct(Long id);
}