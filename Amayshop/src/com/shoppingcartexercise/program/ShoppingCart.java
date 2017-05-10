package com.shoppingcartexercise.program;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	
	private List<Product> products = new ArrayList<>();
	private BigDecimal amount;

	public List<Product> getProducts() {
		return products;
	}

	public List<Product> addToCart(Product item) {
		products.add(item);
		
		return products;
	}
	

}
