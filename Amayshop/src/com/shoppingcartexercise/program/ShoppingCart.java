package com.shoppingcartexercise.program;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private List<Product> products = new ArrayList<>();

	public List<Product> getProducts() {
		return products;
	}

	public List<Product> addToCart(Product item) {
		products.add(item);

		return products;
	}

	public BigDecimal totalPrice(List<Product> products) {
		BigDecimal price = new BigDecimal(0);

		for (Product product : products) {
			price = price.add(product.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		return price;
	}

	public int getItemsCount(ShoppingCart shoppingCart, String productCode) {
		int count = 0;
		for (Product product : shoppingCart.getProducts()) {
			if (productCode.equals(product.getProductCode())) {
				count = ++count;
			}
		}
		return count;
	}

	public String itemDetails(ShoppingCart shoppingCart, String productCode) {
		String itemDetails = null;
		
		for (Product product : shoppingCart.getProducts()) {
			if (productCode != null && productCode.equals(product.getProductCode())) {
				itemDetails = getItemsCount(shoppingCart, product.getProductCode()) + " " + product.getProductName();
			}
		}
		
		return itemDetails;

	}

}
