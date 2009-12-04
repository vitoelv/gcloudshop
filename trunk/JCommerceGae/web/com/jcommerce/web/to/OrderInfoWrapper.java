package com.jcommerce.web.to;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	/*修改，获得订单时间*/
	public String getOrderTime() {
		long addTime = getOrderInfo().getAddTime();
		Date date = new Date(addTime);
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String orderTime = formatter.format(date);
		return orderTime;
	}
	
    // for template
    public String getOrderId() {
    	return getOrderInfo().getPkId();
    }
    
    public String getPostscript() {
    	if( orderInfo.getPostscript() != null){
			return orderInfo.getPostscript();
	    }
		else{
			return "";
		}
	}
}
