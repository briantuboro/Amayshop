package com.shoppingcartexercise.program;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ShoppingCartTest {

	private ShoppingCart shoppingCart;
	private Product productSmall1;
	private Product productMedium1;
	private Product productLarge1;

	@Before
	public void setUp() {
		shoppingCart = new ShoppingCart();
		productSmall1 = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));
		productMedium1 = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));
		productLarge1 = productBuilder("ult_large", "Unlimited 5GB", new BigDecimal(44.90));
	}

	@Test
	public void testAddItemToShoppingCart() {
		shoppingCart.addToCart(productSmall1);

		assertFalse(shoppingCart.getProducts().isEmpty());
		assertEquals(productSmall1, shoppingCart.getProducts().get(0));
		assertEquals("ult_small", shoppingCart.getProducts().get(0).getProductCode());
	}

	@Test
	public void testAddItemsToShoppingCart() {
		shoppingCart.addToCart(productSmall1);
		shoppingCart.addToCart(productMedium1);

		assertFalse(shoppingCart.getProducts().isEmpty());
		assertEquals("ult_small", shoppingCart.getProducts().get(0).getProductCode());
	}

	@Test
	public void testTotalAmountOfProductPrice() {
		shoppingCart.addToCart(productSmall1);
		shoppingCart.addToCart(productMedium1);

		BigDecimal totalPrice = shoppingCart.totalPrice(shoppingCart.getProducts());

		assertEquals(new BigDecimal(54.80).setScale(2, BigDecimal.ROUND_HALF_UP), totalPrice);

	}

	@Test
	public void testGetItemsCount() {
		Product productMedium1 = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));

		shoppingCart.addToCart(productSmall1);
		shoppingCart.addToCart(productMedium1);
		shoppingCart.addToCart(productMedium1);

		assertEquals(1, shoppingCart.getItemsCount(shoppingCart.getProducts(), "ult_small"));
		assertEquals(2, shoppingCart.getItemsCount(shoppingCart.getProducts(), "ult_medium"));
	}

	@Test
	public void testPrintOfAddedItemDetails() {
		shoppingCart.addToCart(productSmall1);

		assertEquals("1 x Unlimited 1GB", shoppingCart.itemDetails(shoppingCart.getProducts()).get("ult_small"));
	}

	@Test
	public void testPrintOfAddedItemsDetails() {
		Product productMedium2 = productBuilder("ult_medium", "Unlimited 2GB", new BigDecimal(29.90));
		Product productOneGb = productBuilder("1gb", "1GB Data-pack", new BigDecimal(9.90));

		shoppingCart.addToCart(productSmall1);
		shoppingCart.addToCart(productMedium2);
		shoppingCart.addToCart(productMedium1);
		shoppingCart.addToCart(productLarge1);
		shoppingCart.addToCart(productOneGb);

		assertEquals("1 x Unlimited 1GB", shoppingCart.itemDetails(shoppingCart.getProducts()).get("ult_small"));
		assertEquals("2 x Unlimited 2GB", shoppingCart.itemDetails(shoppingCart.getProducts()).get("ult_medium"));
		assertEquals("1 x Unlimited 5GB", shoppingCart.itemDetails(shoppingCart.getProducts()).get("ult_large"));
		assertEquals("1 x 1GB Data-pack", shoppingCart.itemDetails(shoppingCart.getProducts()).get("1gb"));
	}

	@Test
	public void testShopOfFirstScenario() {
		Product productSmall2 = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));
		Product productSmall3 = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));

		shoppingCart.addToCart(productSmall1);
		shoppingCart.addToCart(productSmall2);
		shoppingCart.addToCart(productSmall3);
		shoppingCart.addToCart(productLarge1);

		ShoppingCart shopProduct = shoppingCart.shop(shoppingCart.getProducts());

		// Expected cart total
		assertEquals(new BigDecimal(94.70).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("3 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("1 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get("ult_large"));
	}

	@Test
	public void testShopOfSecondScenario() {
		Product productSmall2 = productBuilder("ult_small", "Unlimited 1GB", new BigDecimal(24.90));
		Product productLarge2 = productBuilder("ult_large", "Unlimited 5GB", new BigDecimal(44.90));
		Product productLarge3 = productBuilder("ult_large", "Unlimited 5GB", new BigDecimal(44.90));
		Product productLarge4 = productBuilder("ult_large", "Unlimited 5GB", new BigDecimal(44.90));

		shoppingCart.addToCart(productSmall1);
		shoppingCart.addToCart(productSmall2);
		shoppingCart.addToCart(productLarge1);
		shoppingCart.addToCart(productLarge2);
		shoppingCart.addToCart(productLarge3);
		shoppingCart.addToCart(productLarge4);

		ShoppingCart shopProduct = shoppingCart.shop(shoppingCart.getProducts());

		// Expected cart total
		assertEquals(new BigDecimal(209.40).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("2 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("4 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get("ult_large"));
	}

	private Product productBuilder(String productCode, String productName, BigDecimal price) {
		Product item = new Product();

		item.setProductCode(productCode);
		item.setProductName(productName);
		item.setPrice(price);

		return item;
	}

}
