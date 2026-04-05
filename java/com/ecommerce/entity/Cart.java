package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// One cart belongs to one user
	@OneToOne
	private User user;

	// Cart has multiple items
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items;
}