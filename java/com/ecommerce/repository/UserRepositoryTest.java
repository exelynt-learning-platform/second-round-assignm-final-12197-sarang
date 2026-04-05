package com.ecommerce.repository;

import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testSaveAndFindUser() {
		User user = new User();
		user.setUsername("testuser");
		user.setEmail("test@example.com");
		user.setPassword("password");
		user.setRole(Role.ROLE_USER); // ✅ Use enum, not string

		User saved = userRepository.save(user);
		assertNotNull(saved.getId());

		Optional<User> fetched = userRepository.findById(saved.getId());
		assertTrue(fetched.isPresent());
		assertEquals("testuser", fetched.get().getUsername());

		Optional<User> byEmail = userRepository.findByEmail("test@example.com");
		assertTrue(byEmail.isPresent());
		assertEquals(Role.ROLE_USER, byEmail.get().getRole());
	}
}