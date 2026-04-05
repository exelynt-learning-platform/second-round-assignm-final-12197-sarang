package com.ecommerce.controller;

import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	// Add product to cart
	@PostMapping("/add")
	public ResponseEntity<Cart> addToCart(@RequestParam Long userId, @RequestParam Long productId,
			@RequestParam int quantity) {

		Cart cart = cartService.addToCart(userId, productId, quantity);
		return ResponseEntity.ok(cart);
	}

	// Optional: remove product from cart
	@DeleteMapping("/remove")
	public ResponseEntity<Cart> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {

		Cart cart = cartService.removeFromCart(userId, productId);
		return ResponseEntity.ok(cart);
	}
}