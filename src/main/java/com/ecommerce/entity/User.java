package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role; // enum role

	public void setUsername(String string) {
		// TODO Auto-generated method stub

	}

	public Object getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

}