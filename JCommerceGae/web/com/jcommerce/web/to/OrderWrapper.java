package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderInfo;

public class OrderWrapper extends BaseModelWrapper {

	OrderInfo order;
	@Override
	protected Object getWrapped() {
		return getOrder();
	}
	public OrderWrapper(ModelObject order) {
		super();
		this.order = (OrderInfo)order;
	}
	
	public OrderInfo getOrder() {
		return order;
	}
	
    // for template
    public String getOrderId() {
    	return getOrder().getPkId();
    }
}
