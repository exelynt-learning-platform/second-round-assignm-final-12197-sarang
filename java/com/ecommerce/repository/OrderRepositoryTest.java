package com.ecommerce.repository;

import com.ecommerce.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void testFindByUser() {
		// Create and save a user
		User user = new User();
		user.setUsername("JohnDoe");
		user.setEmail("john@example.com");
		user.setPassword("password");
		user.setRole(Role.ROLE_USER); // Use enum, not String
		userRepository.save(user);

		// Create and save a product
		Product product = new Product();
		product.setName("Laptop");
		product.setDescription("Gaming Laptop");
		product.setPrice(new BigDecimal("1500.00"));
		product.setStock(10);
		product.setImageUrl("image.jpg");
		productRepository.save(product);

		// Create an order
		Order order = new Order();
		order.setUser(user);
		order.setStatus("CREATED");
		order.setAddress("123 Main St");
		order.setTotalPrice(product.getPrice()); // BigDecimal

		// Create order item
		OrderItem item = new OrderItem();
		item.setProductName(product.getName());
		item.setQuantity(1);
		item.setPrice(product.getPrice()); // BigDecimal
		item.setOrder(order);

		order.getItems().add(item);

		// Save order
		orderRepository.save(order);

		// Test findByUser
		List<Order> orders = orderRepository.findByUser(user);
		assertThat(orders).isNotEmpty();
		assertThat(orders.get(0).getUser().getUsername()).isEqualTo("JohnDoe");
		assertThat(orders.get(0).getItems().get(0).getProductName()).isEqualTo("Laptop");
	}
}