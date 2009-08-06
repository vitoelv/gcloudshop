package com.jcommerce.web.to;

public class Total extends BaseWrapper{

	Double discount = 0.0;
	Double tax = 0.0;
	Double shippingFee = 0.0;
	Double shippingInsure = 0.0;
	Double payFee = 0.0;
	Double packFee = 0.0;
	Double cardFee = 0.0;
	Double surplus = 0.0;
	Double integral = 0.0;
	Double bonus = 0.0;
	String amountFormated= "0.0";

	public Total() {
		
	}


	public Double getDiscount() {
		return discount;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	public Double getTax() {
		return tax;
	}


	public void setTax(Double tax) {
		this.tax = tax;
	}


	public Double getShippingFee() {
		return shippingFee;
	}


	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}


	public Double getShippingInsure() {
		return shippingInsure;
	}


	public void setShippingInsure(Double shippingInsure) {
		this.shippingInsure = shippingInsure;
	}


	public Double getPayFee() {
		return payFee;
	}


	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}


	public Double getPackFee() {
		return packFee;
	}


	public void setPackFee(Double packFee) {
		this.packFee = packFee;
	}


	public Double getCardFee() {
		return cardFee;
	}


	public void setCardFee(Double cardFee) {
		this.cardFee = cardFee;
	}


	public Double getSurplus() {
		return surplus;
	}


	public void setSurplus(Double surplus) {
		this.surplus = surplus;
	}


	public Double getIntegral() {
		return integral;
	}


	public void setIntegral(Double integral) {
		this.integral = integral;
	}


	public Double getBonus() {
		return bonus;
	}


	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}


	public String getAmountFormated() {
		return amountFormated;
	}


	public void setAmountFormated(String amountFormated) {
		this.amountFormated = amountFormated;
	}
	
}
