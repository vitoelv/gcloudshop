package com.jcommerce.web.to;

import com.jcommerce.web.util.WebFormatUtils;

public class Total extends BaseWrapper{

	Double discount = 0.0;
	String discountFormated = "0.0";
	
	Double tax = 0.0;
	String taxFormated = "0.0";
	
	Double shippingFee = 0.0;
	String shippingFeeFormated = "0.0";
	Double shippingInsure = 0.0;
	String shippingInsureFormated = "0.0";
	
	Double payFee = 0.0;
	String payFeeFormated = "0.0";
	
	Double packFee = 0.0;
	String packFeeFormated = "0.0";
	Double cardFee = 0.0;
	String cardFeeFormated = "0.0";
	Double surplus = 0.0;
	String surplusFormated = "0.0";
	
	Integer integral = 0;
	String integralFormated = "0";
	
	Double bonus = 0.0;
	String bonusFormated = "0.0";
	
	Double amount = 0.0;
//	String amountFormated= "0.0";
	
	Integer realGoodsCount = 0;
	Double goodsPrice = 0.0;
//	String goodsPriceFormated = "0.0";
	Double marketPrice = 0.0;
//	String marketPriceFormated = "0.0";
	Double saving = 0.0;
	String savingFormated = "0.0";
	Double saveRate = 0.0;
	
	String willGetBonus = "0.0";
	String formatedSaving = "0.0";
	
	
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





	public Double getBonus() {
		return bonus;
	}


	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}


	public String getAmountFormated() {
		return WebFormatUtils.priceFormat(amount);
	}





	public Integer getRealGoodsCount() {
		return realGoodsCount;
	}


	public void setRealGoodsCount(Integer realGoodsCount) {
		this.realGoodsCount = realGoodsCount;
	}


	public String getDiscountFormated() {
		return discountFormated;
	}


	public void setDiscountFormated(String discountFormated) {
		this.discountFormated = discountFormated;
	}


	public String getTaxFormated() {
		return taxFormated;
	}


	public void setTaxFormated(String taxFormated) {
		this.taxFormated = taxFormated;
	}


	public String getShippingFeeFormated() {
		return shippingFeeFormated;
	}


	public void setShippingFeeFormated(String shippingFeeFormated) {
		this.shippingFeeFormated = shippingFeeFormated;
	}


	public String getShippingInsureFormated() {
		return shippingInsureFormated;
	}


	public void setShippingInsureFormated(String shippingInsureFormated) {
		this.shippingInsureFormated = shippingInsureFormated;
	}


	public String getPayFeeFormated() {
		return payFeeFormated;
	}


	public void setPayFeeFormated(String payFeeFormated) {
		this.payFeeFormated = payFeeFormated;
	}


	public String getPackFeeFormated() {
		return packFeeFormated;
	}


	public void setPackFeeFormated(String packFeeFormated) {
		this.packFeeFormated = packFeeFormated;
	}


	public String getCardFeeFormated() {
		return cardFeeFormated;
	}


	public void setCardFeeFormated(String cardFeeFormated) {
		this.cardFeeFormated = cardFeeFormated;
	}


	public String getSurplusFormated() {
		return surplusFormated;
	}


	public void setSurplusFormated(String surplusFormated) {
		this.surplusFormated = surplusFormated;
	}


	public String getIntegralFormated() {
		return integralFormated;
	}


	public void setIntegralFormated(String integralFormated) {
		this.integralFormated = integralFormated;
	}


	public String getBonusFormated() {
		return bonusFormated;
	}


	public void setBonusFormated(String bonusFormated) {
		this.bonusFormated = bonusFormated;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public Double getGoodsPrice() {
		return goodsPrice;
	}


	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}


	public String getGoodsPriceFormated() {
		return WebFormatUtils.priceFormat(goodsPrice);
	}

	public Double getMarketPrice() {
		return marketPrice;
	}


	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}


	public String getMarketPriceFormated() {
		return WebFormatUtils.priceFormat(marketPrice);
	}




	public Double getSaving() {
		return saving;
	}


	public void setSaving(Double saving) {
		this.saving = saving;
	}


	public String getSavingFormated() {
		return savingFormated;
	}


	public void setSavingFormated(String savingFormated) {
		this.savingFormated = savingFormated;
	}


	public Double getSaveRate() {
		return saveRate;
	}


	public void setSaveRate(Double saveRate) {
		this.saveRate = saveRate;
	}


	public void setIntegral(Integer integral) {
		this.integral = integral;
	}


	public String getWillGetBonus() {
		return willGetBonus;
	}


	public void setWillGetBonus(String willGetBonus) {
		this.willGetBonus = willGetBonus;
	}





	public String getFormatedSaving() {
		return formatedSaving;
	}


	public void setFormatedSaving(String formatedSaving) {
		this.formatedSaving = formatedSaving;
	}


	public Integer getIntegral() {
		return integral;
	}
	
}
