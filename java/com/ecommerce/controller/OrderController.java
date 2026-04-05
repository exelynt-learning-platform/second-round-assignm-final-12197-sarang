package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.PayPalService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	private final PayPalService payPalService;

	public OrderController(OrderService orderService, PayPalService payPalService) {
		this.orderService = orderService;
		this.payPalService = payPalService;
	}

	// Place an order and create a PayPal order
	@PostMapping("/place")
	public Map<String, String> placeOrder(@RequestParam Long userId, @RequestParam String shippingAddress)
			throws IOException {
		// 1. Create local order from cart
		Order localOrder = orderService.placeOrder(userId, shippingAddress);

		// 2. Create PayPal order (use full class name to avoid conflict)
		com.paypal.orders.Order paypalOrder = payPalService.createOrder("USD", localOrder.getTotalPrice().toString(),
				localOrder.getId());

		// 3. Return both local and PayPal order IDs
		Map<String, String> response = new HashMap<>();
		response.put("localOrderId", localOrder.getId().toString());
		response.put("paypalOrderId", paypalOrder.id());
		return response;
	}

	// Capture payment for an order
	@PostMapping("/capture")
	public Map<String, String> capturePayment(@RequestParam String paypalOrderId, @RequestParam Long localOrderId)
			throws IOException {
		// Capture PayPal payment
		payPalService.capturePayment(paypalOrderId, localOrderId);

		// Return success response
		Map<String, String> response = new HashMap<>();
		response.put("message", "Payment processed and order status updated.");
		response.put("orderId", localOrderId.toString());
		return response;
	}

	// Optional: Get order by ID
	@GetMapping("/{orderId}")
	public Order getOrder(@PathVariable Long orderId) {
		return (Order) orderService.getOrdersByUser(orderId);
	}
}