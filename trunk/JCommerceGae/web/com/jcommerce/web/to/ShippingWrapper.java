package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.web.util.WebFormatUtils;

public class ShippingWrapper extends BaseModelWrapper {

	
	String configure;
	
	double shippingFee;
	double freeMoney;
	
	
	
	Shipping shipping;
	@Override
	protected Object getWrapped() {
		return getShipping();
	}
	public ShippingWrapper(ModelObject shipping) {
		super();
		this.shipping = (Shipping)shipping;
	}
	
	public Shipping getShipping() {
		return shipping;
	}
    // for template
    public String getShippingId() {
    	return getShipping().getPkId();
    }
	public double getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}
	public String getFreeMoney() {
		return WebFormatUtils.priceFormat(freeMoney);
	}
	public void setFreeMoney(double freeMoney) {
		this.freeMoney = freeMoney;
	}
	public String getFormatShippingFee() {
		return WebFormatUtils.priceFormat(shippingFee);
	}
	public String getInsureFormated() {
		
		String insure = getShipping().getInsure();
		if(insure == null) {
			insure = "0";
		}
		String res = insure.indexOf("%")>=0 ? insure : WebFormatUtils.priceFormat(insure);
		return res;
	}
	public String getConfigure() {
		return configure;
	}
	public void setConfigure(String configure) {
		this.configure = configure;
	}
	
	public String getSupportCod() {
		return getShipping().getSupportCod().toString();
	}
	
    
}
