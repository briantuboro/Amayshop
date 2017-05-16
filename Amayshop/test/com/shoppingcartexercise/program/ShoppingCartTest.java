package com.shoppingcartexercise.program;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ShoppingCartTest {

	private ShoppingCart shoppingCart;
	private Product productSmall;
	private Product productMedium;
	private Product productLarge;
	private Product productOneGb;

	@Before
	public void setUp() {
		shoppingCart = new ShoppingCart();
		productSmall = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));
		productMedium = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));
		productLarge = productBuilder("ult_large", "Unlimited 5GB", new BigDecimal(44.90));
		productOneGb = productBuilder("1gb", "1GB Data-pack", new BigDecimal(9.90));
	}

	@Test
	public void testAddItemToShoppingCart() {
		shoppingCart.addToCart(productSmall);

		assertFalse(shoppingCart.getProducts().isEmpty());
		assertEquals(productSmall, shoppingCart.getProducts().get(0));
		assertEquals("ult_small", shoppingCart.getProducts().get(0).getProductCode());
	}

	@Test
	public void testAddItemsToShoppingCart() {
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productMedium);

		assertFalse(shoppingCart.getProducts().isEmpty());
		assertEquals("ult_small", shoppingCart.getProducts().get(0).getProductCode());
	}

	@Test
	public void testTotalAmountOfProductPrice() {
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productMedium);

		BigDecimal totalPrice = shoppingCart.totalPrice(shoppingCart.getProducts());

		assertEquals(new BigDecimal(54.80).setScale(2, BigDecimal.ROUND_HALF_UP), totalPrice);

	}

	@Test
	public void testGetItemsCount() {
		Product productMedium1 = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));

		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productMedium1);
		shoppingCart.addToCart(productMedium1);

		assertEquals(1, shoppingCart.getItemsCount(shoppingCart.getProducts(), "ult_small"));
		assertEquals(2, shoppingCart.getItemsCount(shoppingCart.getProducts(), "ult_medium"));
	}

	@Test
	public void testPrintOfAddedItemDetails() {
		shoppingCart.addToCart(productSmall);

		assertEquals("1 x Unlimited 1GB", shoppingCart.itemDetails(shoppingCart.getProducts()).get("ult_small"));
	}

	@Test
	public void testPrintOfAddedItemsDetails() {
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productMedium);
		shoppingCart.addToCart(productMedium);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productOneGb);

		assertEquals("1 x Unlimited 1GB", shoppingCart.itemDetails(shoppingCart.getProducts()).get("ult_small"));
		assertEquals("2 x Unlimited 2GB", shoppingCart.itemDetails(shoppingCart.getProducts()).get("ult_medium"));
		assertEquals("1 x Unlimited 5GB", shoppingCart.itemDetails(shoppingCart.getProducts()).get("ult_large"));
		assertEquals("1 x 1GB Data-pack", shoppingCart.itemDetails(shoppingCart.getProducts()).get("1gb"));
	}

	@Test
	public void testShopOfFirstScenario() {
		// Add three small product to cart
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		// Add a large product to cart
		shoppingCart.addToCart(productLarge);

		ShoppingCart shopProduct = shoppingCart.shop(shoppingCart.getProducts(), null);

		// Expected cart total price
		assertEquals(new BigDecimal(94.70).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("3 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("1 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get("ult_large"));
	}

	@Test
	public void testShopOfSecondScenario() {
		// Add two small product to cart
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		// Add two large product to cart
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);

		ShoppingCart shopProduct = shoppingCart.shop(shoppingCart.getProducts(), null);

		// Expected cart total price
		assertEquals(new BigDecimal(209.40).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("2 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("4 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get("ult_large"));
	}

	@Test
	public void testShopOfThirdScenario() {
		// Add a small product to cart
		shoppingCart.addToCart(productSmall);
		// Add two medium product to cart
		shoppingCart.addToCart(productMedium);
		shoppingCart.addToCart(productMedium);

		ShoppingCart shopProduct = shoppingCart.shop(shoppingCart.getProducts(), null);

		// Expected cart total price
		assertEquals(new BigDecimal(84.70).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("1 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("2 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get("ult_medium"));
		assertEquals("2 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get("1gb"));
	}

	@Test
	public void testShopOfFourthScenario() {
		// Add a small product to cart
		shoppingCart.addToCart(productSmall);
		// Add a 1gb product to cart
		shoppingCart.addToCart(productOneGb);

		ShoppingCart shopProduct = shoppingCart.shop(shoppingCart.getProducts(), "|<3AMAYSIM");

		// Expected cart total price
		assertEquals(new BigDecimal(31.32).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("1 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("1 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get("1gb"));
	}

	private Product productBuilder(String productCode, String productName, BigDecimal price) {
		Product item = new Product();

		item.setProductCode(productCode);
		item.setProductName(productName);
		item.setPrice(price);

		return item;
	}

}
