package com.ecommerce.controller;

import com.ecommerce.service.PayPalService;
import com.paypal.orders.Order;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PayPalController {

	private final PayPalService payPalService;

	public PayPalController(PayPalService payPalService) {
		this.payPalService = payPalService;
	}

	// Step 1: Create order
	@PostMapping("/paypal")
	public Map<String, String> createOrder(@RequestParam String amount) throws IOException {
		Order order = payPalService.createOrder("USD", amount, null);
		Map<String, String> response = new HashMap<>();
		response.put("orderId", order.id());
		return response;
	}

	// Step 2: Capture order
	@PostMapping("/paypal/capture")
	public Map<String, String> captureOrder(@RequestParam String orderId) throws IOException {
		Order order = payPalService.captureOrder(orderId);
		Map<String, String> response = new HashMap<>();
		response.put("status", order.status());
		return response;
	}
}