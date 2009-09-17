package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.web.util.WebFormatUtils;

public class OrderInfoWrapper extends BaseModelWrapper {

	OrderInfo orderInfo;
	@Override
	protected Object getWrapped() {
		return getOrderInfo();
	}
	public OrderInfoWrapper(ModelObject order) {
		super();
		this.orderInfo = (OrderInfo)order;
	}
	
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	
	
	public String getTotalFee() {
		OrderInfo oi = getOrderInfo();
		double total = oi.getGoodsAmount() + oi.getShippingFee() + 
			oi.getInsureFee() + oi.getPayFee()+ 
			oi.getPackFee() + oi.getCardFee() + 
			oi.getTax() - oi.getDiscount();
		return WebFormatUtils.priceFormat(total);
	}
	
    // for template
    public String getOrderId() {
    	return getOrderInfo().getPkId();
    }
}
