package com.ecommerce.service;

import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository repo;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User registerUser(User user) {
		// Encrypt password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Set default role if null
		if (user.getRole() == null) {
			user.setRole(Role.ROLE_USER);
		}

		return repo.save(user);
	}

	@Override
	public User getUserById(Long id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}

	@Override
	public User getUserByEmail(String email) {
		return repo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
	}
}