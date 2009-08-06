package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Shipping;

public class ShippingWrapper extends BaseModelWrapper {

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
    	return getShipping().getId();
    }
}
