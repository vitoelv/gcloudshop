package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Order;

public class OrderWrapper extends BaseModelWrapper {

	Order order;
	@Override
	protected Object getWrapped() {
		return getOrder();
	}
	public OrderWrapper(ModelObject order) {
		super();
		this.order = (Order)order;
	}
	
	public Order getOrder() {
		return order;
	}
	
    // for template
    public String getOrderId() {
    	return getOrder().getId();
    }
}
