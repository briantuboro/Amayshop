package com.shoppingcartexercise.program;

public class PricingRules {
	private Integer smallCountNumber;
	private Integer mediumCountNumber;
	private Integer largeCountNumber;
	private Integer oneGbCountNumber;
	private MonetaryAmount largeProductDiscountAmount;

	public Integer getSmallCountNumber() {
		return smallCountNumber;
	}

	public void setSmallCountNumber(Integer smallCountNumber) {
		this.smallCountNumber = smallCountNumber;
	}

	public Integer getMediumCountNumber() {
		return mediumCountNumber;
	}

	public void setMediumCountNumber(Integer mediumCountNumber) {
		this.mediumCountNumber = mediumCountNumber;
	}

	public Integer getLargeCountNumber() {
		return largeCountNumber;
	}

	public void setLargeCountNumber(Integer largeCountNumber) {
		this.largeCountNumber = largeCountNumber;
	}

	public Integer getOneGbCountNumber() {
		return oneGbCountNumber;
	}

	public void setOneGbCountNumber(Integer oneGbCountNumber) {
		this.oneGbCountNumber = oneGbCountNumber;
	}

	public MonetaryAmount getLargeProductDiscountAmount() {
		return largeProductDiscountAmount;
	}

	public void setLargeProductDiscountAmount(MonetaryAmount largeProductDiscountAmount) {
		this.largeProductDiscountAmount = largeProductDiscountAmount;
	}

}
