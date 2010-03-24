package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderGoods;

public class OrderGoodsWrapper extends BaseModelWrapper {

	@Override
	protected Object getWrapped() {
		return getOrderGoods();
	}
	private OrderGoods orderGoods;
	
	public OrderGoodsWrapper(ModelObject orderGoods) {
		this.orderGoods = (OrderGoods)orderGoods;
		
	}
	public OrderGoods getOrderGoods() {
		return orderGoods;
	}
	public String getParentId() {
		return getOrderGoods().getParentId();
	}
	public int getIsGift() {
		if(getOrderGoods().getIsGift()){
			return 1;
		}
		else{
			return 0;
		}
	}
}
