package com.ecommerce.service;

import com.ecommerce.entity.Order;
import java.util.List;

public interface OrderService {
	Order createOrderFromCart(Long userId, String shippingAddress);

	List<Order> getOrdersByUser(Long userId);

	Order placeOrder(Long userId, String shippingAddress);
}