package com.shoppingcartexercise.program;

import java.math.BigDecimal;
import java.util.Map;

public class ShoppingCartImpl implements Shopping {

	private static String PRODUCT_SMALL_CODE = "ult_small";
	private static String PRODUCT_MEDIUM_CODE = "ult_medium";
	private static String PRODUCT_LARGE_CODE = "ult_large";
	private static String PRODUCT_ONE_GB_CODE = "1gb";
	private static String PRODUCT_ONE_GB_NAME = "1GB Data-pack";

	public ShoppingCart shop(ShoppingCart shoppingCart) {
		int smallItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), PRODUCT_SMALL_CODE);
		int largeItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), PRODUCT_LARGE_CODE);
		int mediumItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), PRODUCT_MEDIUM_CODE);

		for (Product product : shoppingCart.getProducts()) {
			if (smallItemsCount == 3) {
				BigDecimal totalPrice = shoppingCart.totalPrice(shoppingCart.getProducts());
				shoppingCart.setProductTotalPrice(totalPrice.subtract(product.getPrice()));
				break;
			} else if (largeItemsCount > 3) {
				for (Product largeProduct : shoppingCart.getProducts()) {
					if (PRODUCT_LARGE_CODE.equals(largeProduct.getProductCode())) {
						largeProduct.setPrice(new BigDecimal(39.90));
					}
				}
				shoppingCart.setProductTotalPrice(shoppingCart.totalPrice(shoppingCart.getProducts()));
				break;
			} else {
				for (int i = 0; i < mediumItemsCount; i++) {
					Product oneGbProduct = new Product(PRODUCT_ONE_GB_CODE, PRODUCT_ONE_GB_NAME, null);
					shoppingCart.getProducts().add(oneGbProduct);
				}
				
				BigDecimal grandTotalPrice = shoppingCart.totalPrice(shoppingCart.getProducts());
				
				grandTotalPrice = shoppingCart.applyDiscount(shoppingCart.getPromoCode(), grandTotalPrice);
				
				shoppingCart.setProductTotalPrice(grandTotalPrice);
				break;
			}
		}
		
		Map<String, String> itemDetails = shoppingCart.itemDetails(shoppingCart.getProducts());
		
		shoppingCart.setSelectedProductDetails(itemDetails);
		
		return shoppingCart;
	}

}