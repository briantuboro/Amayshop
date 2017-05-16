package com.shoppingcartexercise.program;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

	private List<Product> products = new ArrayList<>();
	private BigDecimal productTotalPrice;
	private Map<String, String> selectedProductDetails = new HashMap<>();

	public List<Product> getProducts() {
		return products;
	}

	public BigDecimal getProductTotalPrice() {
		return productTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public Map<String, String> getSelectedProductDetails() {
		return selectedProductDetails;
	}

	public void setSelectedProductDetails(Map<String, String> selectedProductDetails) {
		this.selectedProductDetails = selectedProductDetails;
	}

	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public List<Product> addToCart(Product item) {
		products.add(item);

		return products;
	}

	public BigDecimal totalPrice(List<Product> products) {
		BigDecimal price = new BigDecimal(0);

		for (Product product : products) {

			if ("ult_large".equals(product.getProductCode())) {

			}

			price = price.add(product.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		return price.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public int getItemsCount(List<Product> products, String productCode) {
		int count = 0;
		for (Product product : products) {
			if (productCode != null && productCode.equals(product.getProductCode())) {
				count = ++count;
			}
		}
		return count;
	}

	public Map<String, String> itemDetails(List<Product> products) {
		Map<String, String> itemDetailsMap = new HashMap<>();

		for (Product product : products) {

			if (!itemDetailsMap.containsKey(product.getProductCode())) {
				itemDetailsMap.put(product.getProductCode(),
						getItemsCount(products, product.getProductCode()) + " x " + product.getProductName());
			}
		}

		return itemDetailsMap;

	}

	public ShoppingCart shop(List<Product> products) {
		ShoppingCart shoppingCart = new ShoppingCart();
		String productSmallCode = "ult_small";
		String productLargeCode = "ult_large";
		int smallItemsCount = getItemsCount(products, productSmallCode);
		int largeItemsCount = getItemsCount(products, productLargeCode);

		for (Product product : products) {
			if (smallItemsCount == 3) {
				BigDecimal totalPrice = totalPrice(products);
				shoppingCart.setProductTotalPrice(totalPrice.subtract(product.getPrice()));
				break;
			} else {
				if (largeItemsCount > 3) {
					for (Product largeProduct : products) {
						if (productLargeCode.equals(largeProduct.getProductCode())) {
							largeProduct.setPrice(new BigDecimal(39.90));
						}
					}
					shoppingCart.setProductTotalPrice(totalPrice(products));
					break;
				} else {
					shoppingCart.setProductTotalPrice(totalPrice(products));
					break;
				}
			}
		}

		Map<String, String> itemDetails = this.itemDetails(products);

		shoppingCart.setSelectedProductDetails(itemDetails);

		return shoppingCart;
	}

}
