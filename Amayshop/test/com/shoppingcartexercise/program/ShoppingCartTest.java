package com.shoppingcartexercise.program;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ShoppingCartTest {
	
	private Product item;
	

	@Before
	public void setUp(){
		item = new Product();
		item.setProductCode("ult_small");
		item.setProductName("Unlimited 1GB");
		item.setPrice(new BigDecimal(24.90));
	}
	
	@Test
	public void testAddItemToShoppingCart() {
		ShoppingCart shoppingCart = new ShoppingCart();
		
		shoppingCart.addToCart(item);
		
		assertFalse(shoppingCart.getProducts().isEmpty());
		assertEquals(item, shoppingCart.getProducts().get(0));
		assertEquals("ult_small", shoppingCart.getProducts().get(0).getProductCode());
	}

	@Test
	public void testAddItemsToShoppingCart() {
		
		ShoppingCart shoppingCart = new ShoppingCart();
		Product productSmall = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));
		Product productMedium = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));
		
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productMedium);
		
		assertFalse(shoppingCart.getProducts().isEmpty());
		assertEquals("ult_small", shoppingCart.getProducts().get(0).getProductCode());
	}
	
	@Test
	public void testTotalAmountOfProductPrice() {
		
		ShoppingCart shoppingCart = new ShoppingCart();
		
		Product productSmall = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));
		Product productMedium = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));
		
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productMedium);
		
		BigDecimal totalPrice = shoppingCart.totalPrice(shoppingCart.getProducts());
		
		assertEquals(new BigDecimal(54.80).setScale(2, BigDecimal.ROUND_HALF_UP), totalPrice);
		
	}
	
	@Test
	public void testGetItemsCount() {
		ShoppingCart shoppingCart = new ShoppingCart();
		
		Product productSmall = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));
		Product productMedium = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));
		Product productMedium1 = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));
		
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productMedium);
		shoppingCart.addToCart(productMedium1);
		
		assertEquals(1, shoppingCart.getItemsCount(shoppingCart, "ult_small"));
		assertEquals(2, shoppingCart.getItemsCount(shoppingCart, "ult_medium"));
	}
	
	@Test
	public void testPrintOfPurchasedItemDetails() {
		ShoppingCart shoppingCart = new ShoppingCart();
		
		Product productSmall = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));
		
		shoppingCart.addToCart(productSmall);
		
		assertEquals("1 Unlimited 1GB", shoppingCart.itemDetails(shoppingCart, "ult_small"));
	}
	
	private Product productBuilder(String productCode, String productName, BigDecimal price) {
		Product item = new Product();
		
		item.setProductCode(productCode);
		item.setProductName(productName);
		item.setPrice(price);
		
		return item;
	}

}
