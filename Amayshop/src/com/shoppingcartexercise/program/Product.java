package com.shoppingcartexercise.program;

import java.math.BigDecimal;

public class Product {

	private String productCode;
	private String productName;
	private BigDecimal price;

	public Product() {

	}

	public Product(String productCode, String productname, BigDecimal price) {
		this.productCode = productCode;
		this.productName = productname;
		this.price = price;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
