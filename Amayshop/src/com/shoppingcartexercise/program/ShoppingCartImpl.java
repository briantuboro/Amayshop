package com.shoppingcartexercise.program;

import java.math.BigDecimal;
import java.util.Map;

public class ShoppingCartImpl implements Shopping {

	private static String PRODUCT_SMALL_CODE = "ult_small";
	private static String PRODUCT_MEDIUM_CODE = "ult_medium";
	private static String PRODUCT_LARGE_CODE = "ult_large";
	private static String PRODUCT_ONE_GB_CODE = "1gb";
	private static String PRODUCT_ONE_GB_NAME = "1GB Data-pack";
	private PricingRules pricingRules;

	public ShoppingCart shop(ShoppingCart shoppingCart) {
		int smallItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), PRODUCT_SMALL_CODE);
		int largeItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), PRODUCT_LARGE_CODE);
		int mediumItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), PRODUCT_MEDIUM_CODE);

		for (Product product : shoppingCart.getProducts()) {
			if (pricingRules != null && pricingRules.getSmallCountNumber() != null) {
				if (smallItemsCount == pricingRules.getSmallCountNumber()) {
					BigDecimal totalPrice = shoppingCart.totalPrice(shoppingCart.getProducts());
					BigDecimal smallProductPriceAppliedDeal = totalPrice.subtract(product.getPrice().getAmount());
					BigDecimal grandTotalPrice = shoppingCart.applyDiscount(shoppingCart.getPromoCode(), smallProductPriceAppliedDeal);
					shoppingCart.setProductTotalPrice(grandTotalPrice);
					addOneGbProductForEachMediumProduct(shoppingCart, mediumItemsCount);
					break;
				}	
			} else if (pricingRules != null && pricingRules.getLargeCountNumber() != null) {
				if (largeItemsCount > pricingRules.getLargeCountNumber()) {
					for (Product largeProduct : shoppingCart.getProducts()) {
						if (PRODUCT_LARGE_CODE.equals(largeProduct.getProductCode())) {
							largeProduct.getPrice().setAmount(pricingRules.getLargeProductDiscountAmount().getAmount());
						}
					}
				
				BigDecimal grandTotalPrice = computeGrandTotalPrice(shoppingCart);
					
				shoppingCart.setProductTotalPrice(grandTotalPrice);
				addOneGbProductForEachMediumProduct(shoppingCart, mediumItemsCount);
				break;
				}
			} else {
				
				BigDecimal grandTotalPrice = computeGrandTotalPrice(shoppingCart);
				
				shoppingCart.setProductTotalPrice(grandTotalPrice);
				addOneGbProductForEachMediumProduct(shoppingCart, mediumItemsCount);
				break;
			}
		}
		
		Map<String, String> itemDetails = shoppingCart.itemDetails(shoppingCart.getProducts());
		
		shoppingCart.setSelectedProductDetails(itemDetails);
		
		return shoppingCart;
	}

	private void addOneGbProductForEachMediumProduct(ShoppingCart shoppingCart, int mediumItemsCount) {
		for (int i = 0; i < mediumItemsCount; i++) {
			Product oneGbProduct = new Product(PRODUCT_ONE_GB_CODE, PRODUCT_ONE_GB_NAME, null);
			shoppingCart.getProducts().add(oneGbProduct);
		}
	}

	private BigDecimal computeGrandTotalPrice(ShoppingCart shoppingCart) {
		BigDecimal grandTotalPrice = shoppingCart.totalPrice(shoppingCart.getProducts());
		
		grandTotalPrice = shoppingCart.applyDiscount(shoppingCart.getPromoCode(), grandTotalPrice);
		return grandTotalPrice;
	}

	public PricingRules getPricingRules() {
		return pricingRules;
	}

	public void setPricingRules(PricingRules pricingRules) {
		this.pricingRules = pricingRules;
	}

}