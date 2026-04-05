package com.ecommerce.service;

import com.ecommerce.entity.*;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

	private OrderRepository orderRepository;
	private CartRepository cartRepository;
	private UserRepository userRepository;
	private OrderService orderService;

	@BeforeEach
	void setUp() {
		orderRepository = mock(OrderRepository.class);
		cartRepository = mock(CartRepository.class);
		userRepository = mock(UserRepository.class);
		orderService = new OrderServiceImpl(userRepository, cartRepository, orderRepository);
	}

	@Test
	void testPlaceOrder_Success() {
		User user = new User();
		user.setId(1L);

		Product product = new Product();
		product.setId(1L);
		product.setName("Product1");
		product.setPrice(BigDecimal.valueOf(100));

		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(2);

		List<CartItem> items = new ArrayList<>();
		items.add(cartItem);

		Cart cart = new Cart();
		cart.setUser(user);
		cart.setItems(items);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
		when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

		Order order = orderService.placeOrder(1L, "123 Street");
		assertEquals(200, order.getItems().get(0).getQuantity() * 100); // total price 200
		assertTrue(cart.getItems().isEmpty());
	}

	@Test
	void testPlaceOrder_EmptyCart() {
		User user = new User();
		user.setId(1L);
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setItems(new ArrayList<>());

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

		assertThrows(BadRequestException.class, () -> orderService.placeOrder(1L, "123 Street"));
	}

	@Test
	void testPlaceOrder_UserNotFound() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(1L, "123 Street"));
	}
}