package com.ecommerce.service;

import com.ecommerce.entity.User;

public interface UserService {
	User registerUser(User user);

	User getUserById(Long id);

	User getUserByEmail(String email);
}