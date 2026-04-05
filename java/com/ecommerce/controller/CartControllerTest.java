package com.ecommerce.controller;
import com.ecommerce.controller.*;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartService cartService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser(username = "user1", roles = { "USER" })
	void testAddToCart() throws Exception {
		Product product = new Product();
		product.setId(1L);
		product.setName("Laptop");
		product.setPrice(BigDecimal.valueOf(1200));

		CartItem item = new CartItem();
		item.setProduct(product);
		item.setQuantity(1);

		Cart cart = new Cart();
		cart.setId(1L);
		cart.setItems(Collections.singletonList(item));

		when(cartService.addToCart(anyLong(), anyLong(), anyInt())).thenReturn(cart);

		mockMvc.perform(post("/api/cart/add?userId=1&productId=1&quantity=1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}