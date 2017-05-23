package com.shoppingcartexercise.program;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ShoppingCartImplTest {

	private static final String PROMO_CODE = "|<3AMAYSIM";
	private static final String USD = "USD";
	private static final String _1GB = "1gb";
	private static final String ULT_LARGE = "ult_large";
	private static final String ULT_MEDIUM = "ult_medium";
	private static final String ULT_SMALL = "ult_small";
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
		productSmall = productBuilder(ULT_SMALL, "Unlimited 1GB", new MonetaryAmount(new BigDecimal(24.90), USD));
		productMedium = productBuilder(ULT_MEDIUM, "Unlimited 2GB", new MonetaryAmount(new BigDecimal(29.90), USD));
		productLarge = productBuilder(ULT_LARGE, "Unlimited 5GB", new MonetaryAmount(new BigDecimal(44.90), USD));
		productOneGb = productBuilder(_1GB, "1GB Data-pack", new MonetaryAmount(new BigDecimal(9.90), USD));
		pricingRules = new PricingRules();
	}

	@Test
	public void testFirsScenarioWhen3xUnlimited1GbAnd1xUnlimited5Gb() {
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
		assertEquals("3 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get(ULT_SMALL));
		assertEquals("1 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get(ULT_LARGE));
	}

	@Test
	public void testSecondScenarioWhen2xUnlimited1GbAnd4xUnlimited5Gb() {
		// Add two small product to cart
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		// Add four large product to cart
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);

		shoppingCart.setPromoCode(null);
		
		pricingRules.setLargeCountNumber(3);
		pricingRules.setLargeProductDiscountAmount(new MonetaryAmount(new BigDecimal(39.90), USD));
		shoppingCartImpl.setPricingRules(pricingRules);

		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);

		// Expected cart total price
		assertEquals(new BigDecimal(209.40).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("2 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get(ULT_SMALL));
		assertEquals("4 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get(ULT_LARGE));
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
		assertEquals("1 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get(ULT_SMALL));
		assertEquals("2 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get(ULT_MEDIUM));
		assertEquals("2 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get(_1GB));
	}

	@Test
	public void testFourthScenarioWhen1xUnimitedOneGbAnd1x1GbDataPackWithPromoApplied() {
		// Add a small product to cart
		shoppingCart.addToCart(productSmall);
		// Add a 1gb product to cart
		shoppingCart.addToCart(productOneGb);

		shoppingCart.setPromoCode(PROMO_CODE);

		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);

		// Expected cart total price
		assertEquals(new BigDecimal(31.32).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());

		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("1 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get(ULT_SMALL));
		assertEquals("1 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get(_1GB));
	}
	
	@Test
	public void testWhen4xUnlimited5GbAnd2xUnlimited2GbWithPromo() {
		// Add two large product to cart
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		// Add two medium product to cart
		shoppingCart.addToCart(productMedium);
		shoppingCart.addToCart(productMedium);
		
		shoppingCart.setPromoCode(PROMO_CODE);
		
		pricingRules.setLargeCountNumber(3);
		pricingRules.setLargeProductDiscountAmount(new MonetaryAmount(new BigDecimal(39.90), USD));
		shoppingCartImpl.setPricingRules(pricingRules);

		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);
		
		// Expected cart total price
		assertEquals(new BigDecimal(197.46).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());
		
		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("4 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get(ULT_LARGE));
		assertEquals("2 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get(ULT_MEDIUM));
		assertEquals("2 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get(_1GB));
		
	}
	
	@Test
	public void testWhen4xUnlimited5GbAnd2xUnlimited2GbWithOutPromo() {
		// Add two large product to cart
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		shoppingCart.addToCart(productLarge);
		// Add two medium product to cart
		shoppingCart.addToCart(productMedium);
		shoppingCart.addToCart(productMedium);
		
		shoppingCart.setPromoCode(null);
		
		pricingRules.setLargeCountNumber(3);
		pricingRules.setLargeProductDiscountAmount(new MonetaryAmount(new BigDecimal(39.90), USD));
		shoppingCartImpl.setPricingRules(pricingRules);

		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);
		
		// Expected cart total price
		assertEquals(new BigDecimal(219.40).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());
		
		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("4 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get(ULT_LARGE));
		assertEquals("2 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get(ULT_MEDIUM));
		
	}
	
	@Test
	public void testWhen3xUnlimited1GbAnd1xUnlimited2GbAnd1GbDataPackWithPromo() {
		// Add three small product to cart
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		// Add a medium product to cart
		shoppingCart.addToCart(productMedium);
		// Add a 1gb product to cart
		shoppingCart.addToCart(productOneGb);
		
		shoppingCart.setPromoCode(PROMO_CODE);
		
		pricingRules.setSmallCountNumber(3);
		shoppingCartImpl.setPricingRules(pricingRules);
		
		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);
		
		// Expected cart total price
		assertEquals(new BigDecimal(80.64).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());
		
		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("3 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get(ULT_SMALL));
		assertEquals("1 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get(ULT_MEDIUM));
		assertEquals("2 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get(_1GB));
		
	}
	
	@Test
	public void testWhen3xUnlimited1GbAnd1xUnlimited2GbAnd1GbDataPackWithoutPromo() {
		// Add three small product to cart
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		shoppingCart.addToCart(productSmall);
		// Add a medium product to cart
		shoppingCart.addToCart(productMedium);
		// Add a 1gb product to cart
		shoppingCart.addToCart(productOneGb);
		
		shoppingCart.setPromoCode(null);
		
		pricingRules.setSmallCountNumber(3);
		shoppingCartImpl.setPricingRules(pricingRules);
		
		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);
		
		// Expected cart total price
		assertEquals(new BigDecimal(89.6).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());
		
		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("3 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get(ULT_SMALL));
		assertEquals("1 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get(ULT_MEDIUM));
		assertEquals("2 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get(_1GB));
		
	}
	
	@Test
	public void testWhenSingleOrderForEachProductsWithoutPromo() {
		// Add a small product to cart
		shoppingCart.addToCart(productSmall);
		// Add a medium product to cart
		shoppingCart.addToCart(productMedium);
		// Add a large product to cart
		shoppingCart.addToCart(productLarge);
		// Add a 1gb product to cart
		shoppingCart.addToCart(productOneGb);
		
		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);
		
		// Expected cart total price
		assertEquals(new BigDecimal(109.6).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());
		
		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("1 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get(ULT_SMALL));
		assertEquals("1 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get(ULT_MEDIUM));
		assertEquals("1 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get(ULT_LARGE));
		assertEquals("2 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get(_1GB));
	}
	
	@Test
	public void testWhenSingleOrderForEachProductsWithPromo() {
		// Add a small product to cart
		shoppingCart.addToCart(productSmall);
		// Add a medium product to cart
		shoppingCart.addToCart(productMedium);
		// Add a large product to cart
		shoppingCart.addToCart(productLarge);
		// Add a 1gb product to cart
		shoppingCart.addToCart(productOneGb);
		
		shoppingCart.setPromoCode(PROMO_CODE);
		
		ShoppingCart shopProduct = shoppingCartImpl.shop(shoppingCart);
		
		// Expected cart total price
		assertEquals(new BigDecimal(98.64).setScale(2, BigDecimal.ROUND_HALF_UP), shopProduct.getProductTotalPrice());
		
		// Expected cart items
		assertFalse(shopProduct.getSelectedProductDetails().isEmpty());
		assertEquals("1 x Unlimited 1GB", shopProduct.getSelectedProductDetails().get(ULT_SMALL));
		assertEquals("1 x Unlimited 2GB", shopProduct.getSelectedProductDetails().get(ULT_MEDIUM));
		assertEquals("1 x Unlimited 5GB", shopProduct.getSelectedProductDetails().get(ULT_LARGE));
		assertEquals("2 x 1GB Data-pack", shopProduct.getSelectedProductDetails().get(_1GB));
	}

	private Product productBuilder(String productCode, String productName, MonetaryAmount price) {
		Product item = new Product();

		item.setProductCode(productCode);
		item.setProductName(productName);
		item.setPrice(price);

		return item;
	}
	
}
