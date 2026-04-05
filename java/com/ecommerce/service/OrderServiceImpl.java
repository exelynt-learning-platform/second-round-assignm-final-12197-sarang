package com.ecommerce.service;

import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	private final UserRepository userRepo;
	private final CartRepository cartRepo;
	private final OrderRepository orderRepo;

	public OrderServiceImpl(UserRepository userRepo, CartRepository cartRepo, OrderRepository orderRepo) {
		this.userRepo = userRepo;
		this.cartRepo = cartRepo;
		this.orderRepo = orderRepo;
	}

	@Override
	public Order createOrderFromCart(Long userId, String shippingAddress) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

		if (cart.getItems().isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		Order order = new Order();
		order.setUser(user);
		order.setShippingAddress(shippingAddress);
		order.setPaymentStatus(PaymentStatus.PENDING);

		// Add products and calculate totalPrice
		cart.getItems().forEach(item -> {
			order.addProduct(item.getProduct(), item.getQuantity());
		});

		// Save order
		Order savedOrder = orderRepo.save(order);

		// Clear cart
		cart.getItems().clear();
		cartRepo.save(cart);

		return savedOrder;
	}

	@Override
	public List<Order> getOrdersByUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return orderRepo.findByUser(user);
	}

	@Override
	public Order placeOrder(Long userId, String shippingAddress) {
		// TODO Auto-generated method stub
		return null;
	}
}