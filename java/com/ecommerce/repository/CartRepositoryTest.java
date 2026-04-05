package com.ecommerce.repository;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;
import com.ecommerce.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CartRepositoryTest {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testCartSaveAndFindByUser() {
		User user = new User();
		user.setUsername("cartuser");
		user.setEmail("cart@example.com");
		user.setPassword("password");
		user.setRole(Role.ROLE_USER);
		user = userRepository.save(user);

		Cart cart = new Cart();
		cart.setUser(user);
		Cart savedCart = cartRepository.save(cart);

		assertNotNull(savedCart.getId());
		Optional<Cart> fetched = cartRepository.findByUser(user);
		assertTrue(fetched.isPresent());
		assertEquals(savedCart.getId(), fetched.get().getId());
	}
}