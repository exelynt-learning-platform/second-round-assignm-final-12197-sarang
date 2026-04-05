package com.ecommerce.controller;

import com.ecommerce.entity.User;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.LoginResponse;
import com.ecommerce.security.JwtUtil;
import com.ecommerce.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserService userService;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		try {
			// Authenticate user
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			// Fetch the user
			User user = userService.getUserByEmail(request.getEmail());

			// Generate JWT token
			String token = jwtUtil.generateToken(user);

			return new LoginResponse(token);
		} catch (AuthenticationException e) {
			throw new RuntimeException("Invalid credentials");
		}
	}

	@PostMapping("/register")
	public String register(@RequestBody User user) {
		// Register the user
		userService.registerUser(user);
		return "User registered successfully";
	}
}