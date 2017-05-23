package com.shoppingcartexercise.program;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ShoppingCartImplTest {

	private ShoppingCart shoppingCart;
	private ShoppingCartImpl shoppingCartImpl;
	private Product productSmall;
	private Product productMedium;
	private Product productLarge;
	private Product productOneGb;
	PricingRules pricingRules;

	@Before
	public void setUp() {
		shoppingCart = new ShoppingCart();
		shoppingCartImpl = new ShoppingCartImpl();
		productSmall = productBuilder("ult_small", "Unlimited 1GB", new MonetaryAmount(new BigDecimal(24.90), "USD"));
		productMedium = productBuilder("ult_medium", "Unlimited 2GB", new MonetaryAmount(new BigDecimal(29.90), "USD"));
		productLarge = productBuilder("ult_large", "Unlimited 5GB", new MonetaryAmount(new BigDecimal(44.90), "USD"));
		productOneGb = productBuilder("1gb", "1GB Data-pack", new MonetaryAmount(new BigDecimal(9.90), "USD"));
		pricingRules = new PricingRules();
	}

	@Test
	public void testFirsScenarioWhen3xUnlimited1GbAnd1xUnlimitedGb() {
		// Add three small product to cart
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		// Add a large product to cart
		shoppingCart.addToCart(productLarge);

		shoppingCart.setPromoCode(null);
		
		pricingRules.setSmallCountNumber(3);
		shoppingCartImpl.setPricingRules(pricingRules);

		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);

		// Expected cart total price
		assertEquals(new BigDecimal(94.70).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("3 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("1 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get("ult_large"));
	}

	@Test
	public void testSecondScenarioWhen2xUnlimited1GbAnd4xUnlimited5Gb() {
		// Add two small product to cart
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		// Add two large product to cart
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);

		shoppingCart.setPromoCode(null);
		
		pricingRules.setLargeCountNumber(3);
		pricingRules.setLargeProductDiscountAmount(new MonetaryAmount(new BigDecimal(39.90), "USD"));
		shoppingCartImpl.setPricingRules(pricingRules);

		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);

		// Expected cart total price
		assertEquals(new BigDecimal(209.40).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("2 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("4 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get("ult_large"));
	}

	@Test
	public void testThirdScenarioWhen2xUnlimited1GbAnd4xUnlimited5Gb() {
		// Add a small product to cart
		shoppingCart.addToCart(productSmall);
		// Add two medium product to cart
		shoppingCart.addToCart(productMedium);
		shoppingCart.addToCart(productMedium);

		shoppingCart.setPromoCode(null);

		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);

		// Expected cart total price
		assertEquals(new BigDecimal(84.70).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("1 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("2 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get("ult_medium"));
		assertEquals("2 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get("1gb"));
	}

	@Test
	public void testFourthScenarioWhen1xUnimitedOneGbAnd1x1GbDataPackWithPromoApplied() {
		// Add a small product to cart
		shoppingCart.addToCart(productSmall);
		// Add a 1gb product to cart
		shoppingCart.addToCart(productOneGb);

		shoppingCart.setPromoCode("|<3AMAYSIM");

		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);

		// Expected cart total price
		assertEquals(new BigDecimal(31.32).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("1 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get("ult_small"));
		assertEquals("1 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get("1gb"));
	}

	private Product productBuilder(String productCode, String productName, MonetaryAmount price) {
		Product item = new Product();

		item.setProductCode(productCode);
		item.setProductName(productName);
		item.setPrice(price);

		return item;
	}
	
}
