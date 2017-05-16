package com.shoppingcartexercise.program;

import java.math.BigDecimal;
import java.util.Map;

public class ShoppingCartImpl implements Shopping {

	public ShoppingCart shop(ShoppingCart shoppingCart) {
		String productSmallCode = "ult_small";
		String productMediumCode = "ult_medium";
		String productLargeCode = "ult_large";
		int smallItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), productSmallCode);
		int largeItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), productLargeCode);
		int mediumItemsCount = shoppingCart.getItemsCount(shoppingCart.getProducts(), productMediumCode);
		String productOneGbCode = "1gb";
		String productOneGbName = "1GB Data-pack";

		for (Product product : shoppingCart.getProducts()) {
			if (smallItemsCount == 3) {
				BigDecimal totalPrice = shoppingCart.totalPrice(shoppingCart.getProducts());
				shoppingCart.setProductTotalPrice(totalPrice.subtract(product.getPrice()));
				break;
			} else if (largeItemsCount > 3) {
				for (Product largeProduct : shoppingCart.getProducts()) {
					if (productLargeCode.equals(largeProduct.getProductCode())) {
						largeProduct.setPrice(new BigDecimal(39.90));
					}
				}
				shoppingCart.setProductTotalPrice(shoppingCart.totalPrice(shoppingCart.getProducts()));
				break;
			} else {
				for (int i = 0; i < mediumItemsCount; i++) {
					Product oneGbProduct = new Product(productOneGbCode, productOneGbName, null);
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