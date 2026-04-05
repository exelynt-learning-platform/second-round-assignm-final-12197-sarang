package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Cart addToCart(Long userId, Long productId, int quantity) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
			Cart c = new Cart();
			c.setUser(user);
			c.setItems(new ArrayList<>());
			return c;
		});

		CartItem item = new CartItem();
		item.setProduct(product);
		item.setQuantity(quantity);

		cart.getItems().add(item);
		return cartRepository.save(cart);
	}

	@Override
	public Cart removeFromCart(Long userId, Long productId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found"));

		cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

		return cartRepository.save(cart);
	}
}