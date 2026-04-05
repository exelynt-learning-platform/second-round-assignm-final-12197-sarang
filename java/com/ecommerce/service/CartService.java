package com.ecommerce.service;

import com.ecommerce.entity.Cart;

public interface CartService {
	Cart addToCart(Long userId, Long productId, int quantity);

	Cart removeFromCart(Long userId, Long productId);
}