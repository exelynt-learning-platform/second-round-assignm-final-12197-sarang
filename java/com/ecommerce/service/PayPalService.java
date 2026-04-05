package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.repository.OrderRepository;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PayPalService {

	private final PayPalHttpClient client;
	private final OrderRepository orderRepository;

	public PayPalService(PayPalHttpClient client, OrderRepository orderRepository) {
		this.client = client;
		this.orderRepository = orderRepository;
	}

	// Create PayPal order
	public com.paypal.orders.Order createOrder(String currency, String amount, Long localOrderId) throws IOException {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");

		PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest()
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode(currency).value(amount))
				.customId(localOrderId.toString());

		orderRequest.purchaseUnits(java.util.List.of(purchaseUnit));

		OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(orderRequest);

		com.paypal.http.HttpResponse<com.paypal.orders.Order> response = client.execute(request);
		return response.result();
	}

	// Capture PayPal payment
	public void capturePayment(String paypalOrderId, Long localOrderId) throws IOException {
		OrdersCaptureRequest request = new OrdersCaptureRequest(paypalOrderId);
		request.requestBody(new OrderRequest()); // SDK requires non-null body

		com.paypal.http.HttpResponse<com.paypal.orders.Order> response = client.execute(request);
		com.paypal.orders.Order payPalOrder = response.result();

		if ("COMPLETED".equals(payPalOrder.status())) {
			Order localOrder = orderRepository.findById(localOrderId)
					.orElseThrow(() -> new RuntimeException("Local order not found"));
			localOrder.setPaymentStatus(PaymentStatus.PAID);
			orderRepository.save(localOrder);
		} else {
			throw new RuntimeException("Payment not completed");
		}
	}

	public com.paypal.orders.Order captureOrder(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}
}