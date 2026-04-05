package com.ecommerce.service;

import com.ecommerce.entity.*;
import com.ecommerce.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CartServiceImpl.class) // Load your service implementation
public class CartServiceTest {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartService cartService;

	@Test
	public void testAddToCart_Success() {
		// User
		User user = new User();
		user.setUsername("cartserviceuser");
		user.setEmail("cartservice@example.com");
		user.setPassword("password");
		user.setRole(Role.ROLE_USER);
		user = userRepository.save(user);

		// Product
		Product product = new Product();
		product.setName("Headphones");
		product.setDescription("Wireless");
		product.setPrice(BigDecimal.valueOf(100));
		product.setStock(15);
		product.setImageUrl("http://example.com/headphones.jpg");
		product = productRepository.save(product);

		// Add to cart
		Cart updatedCart = cartService.addToCart(user.getId(), product.getId(), 2);

		assertNotNull(updatedCart);
		assertFalse(updatedCart.getItems().isEmpty());
		assertEquals(2, updatedCart.getItems().get(0).getQuantity());
		assertEquals("Headphones", updatedCart.getItems().get(0).getProduct().getName());
	}
}