package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotBlank
	private String description;

	@Positive
	@Column(nullable = false)
	private BigDecimal price;

	@Min(0)
	private int stock;

	@NotBlank
	private String imageUrl;
}