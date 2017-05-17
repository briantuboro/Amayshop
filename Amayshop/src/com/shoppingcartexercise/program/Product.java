package com.shoppingcartexercise.program;

public class Product {

	private String productCode;
	private String productName;
	private MonetaryAmount price;

	public Product() {

	}

	public Product(String productCode, String productname, MonetaryAmount price) {
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

	public MonetaryAmount getPrice() {
		return price;
	}

	public void setPrice(MonetaryAmount price) {
		this.price = price;
	}

}
