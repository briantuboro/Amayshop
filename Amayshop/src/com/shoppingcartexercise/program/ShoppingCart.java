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
	private String promoCode;

	public List<Product> getProducts() {
		return products;
	}

	public Map<String, String> getSelectedProductDetails() {
		return selectedProductDetails;
	}

	public void setSelectedProductDetails(Map<String, String> selectedProductDetails) {
		this.selectedProductDetails = selectedProductDetails;
	}

	public BigDecimal getProductTotalPrice() {
		return productTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public List<Product> addToCart(Product item) {
		products.add(item);

		return products;
	}

	public BigDecimal totalPrice(List<Product> products) {
		BigDecimal price = new BigDecimal(0);

		for (Product product : products) {
			price = price.add(product.getPrice() != null ? product.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP)
					: new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		return price.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public int getItemsCount(List<Product> products, String productCode) {
		int count = 0;
		for (Product product : products) {
			if (productCode != null && productCode.equals(product.getProductCode())) {
				++count;
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

	protected BigDecimal applyDiscount(String promoCode, BigDecimal grandTotalPrice) {
		if (promoCode != null && promoCode.equals(promoCode)) {
			BigDecimal discountedPrice = grandTotalPrice.multiply(new BigDecimal(.10));
			
			grandTotalPrice = grandTotalPrice.subtract(discountedPrice);
		}
		return grandTotalPrice;
	}
}
