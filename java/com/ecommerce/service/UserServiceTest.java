package com.ecommerce.service;

import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserServiceImpl userService;

	private User testUser;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		testUser = new User();
		testUser.setId(1L);
		testUser.setUsername("user1");
		testUser.setEmail("user1@example.com");
		testUser.setPassword("password");
		testUser.setRole(Role.ROLE_USER);
	}

	@Test
	void testRegisterUser() {
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(testUser);

		User savedUser = userService.registerUser(testUser);

		assertNotNull(savedUser);
		assertEquals("user1", savedUser.getUsername());
		assertEquals("encodedPassword", savedUser.getPassword());
		assertEquals(Role.ROLE_USER, savedUser.getRole());

		verify(passwordEncoder, times(1)).encode("password");
		verify(userRepository, times(1)).save(testUser);
	}

	@Test
	void testGetUserById_UserExists() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

		User foundUser = userService.getUserById(1L);

		assertNotNull(foundUser);
		assertEquals("user1", foundUser.getUsername());
	}

	@Test
	void testGetUserById_UserNotFound() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(2L));
	}
}