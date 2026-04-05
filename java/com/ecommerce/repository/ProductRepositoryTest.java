package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testProductCRUD() {
		Product product = new Product();
		product.setName("Laptop");
		product.setDescription("Gaming Laptop");
		product.setPrice(BigDecimal.valueOf(1200));
		product.setStock(10);
		product.setImageUrl("http://example.com/laptop.jpg");

		Product saved = productRepository.save(product);
		assertNotNull(saved.getId());

		Optional<Product> fetched = productRepository.findById(saved.getId());
		assertTrue(fetched.isPresent());
		assertEquals("Laptop", fetched.get().getName());

		List<Product> allProducts = productRepository.findAll();
		assertFalse(allProducts.isEmpty());

		productRepository.delete(saved);
		assertFalse(productRepository.findById(saved.getId()).isPresent());
	}
}