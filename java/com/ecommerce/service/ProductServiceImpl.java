package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repo;

	public ProductServiceImpl(ProductRepository repo) {
		this.repo = repo;
	}

	@Override
	public Product createProduct(Product p) {
		return repo.save(p);
	}

	@Override
	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	@Override
	public Product updateProduct(Long id, Product p) {
		Product existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

		existing.setName(p.getName());
		existing.setDescription(p.getDescription());
		existing.setPrice(p.getPrice());
		existing.setStock(p.getStock());
		existing.setImageUrl(p.getImageUrl());

		return repo.save(existing);
	}

	@Override
	public void deleteProduct(Long id) {
		repo.deleteById(id);
	}
}