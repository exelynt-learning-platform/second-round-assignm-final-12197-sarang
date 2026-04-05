package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

	private ProductRepository productRepository;
	private ProductService productService;

	@BeforeEach
	void setUp() {
		productRepository = mock(ProductRepository.class);
		productService = new ProductServiceImpl(productRepository);
	}

	@Test
	void testGetProductById_Success() {
		Product product = new Product();
		product.setId(1L);
		product.setName("Product1");
		product.setPrice(BigDecimal.valueOf(100));

		when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		Product found = productService.getProductById(1L);
		assertEquals("Product1", found.getName());
	}

	@Test
	void testGetProductById_NotFound() {
		when(productRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
	}
}