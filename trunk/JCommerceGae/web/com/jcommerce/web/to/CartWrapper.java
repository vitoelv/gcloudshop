package com.jcommerce.web.to;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.web.util.WebFormatUtils;

public class CartWrapper extends BaseModelWrapper {

	Cart cart;
	private IDefaultManager manager;
	
	public void setManager(IDefaultManager manager) {
		this.manager = manager;
	}
	@Override
	protected Object getWrapped() {
		return getCart();
	}
	public CartWrapper(ModelObject cart) {
		super();
		this.cart = (Cart)cart;
	}
	
	public Cart getCart() {
		return cart;
	}
	
	public String getRecId() {
		return getCart().getPkId();
	}
	
	public String getParentId() {
		System.out.println(getCart().getParentId()+"+++++++++++++++++++++++++++++++++++++++");
		return getCart().getParentId();
	}
	
	public String getFormatedGoodsPrice() {
		String goodsId = getCart().getGoodsId();
		Goods goods = (Goods) manager.get(ModelNames.GOODS, goodsId);
		
		//判断是否促销
		Long promoteEndTime = goods.getPromoteEndDate();
		Long promoteStartTime = goods.getPromoteStartDate();
		Long nowTime = new Date().getTime();
		if(nowTime > promoteEndTime || nowTime < promoteStartTime) {
			return WebFormatUtils.priceFormat(getCart().getGoodsPrice());
		}
		else {
			return WebFormatUtils.priceFormat(goods.getPromotePrice());
		}		
	}
	
	public double getPrice() {
		String goodsId = getCart().getGoodsId();
		Goods goods = (Goods) manager.get(ModelNames.GOODS, goodsId);
		
		//判断是否促销
		Long promoteEndTime = goods.getPromoteEndDate();
		Long promoteStartTime = goods.getPromoteStartDate();
		Long nowTime = new Date().getTime();
		if(nowTime > promoteEndTime || nowTime < promoteStartTime) {
			return getCart().getGoodsPrice();
		}
		else {
			return goods.getPromotePrice();
		}
	}
	
	public String getFormatedSubtotal() {
		return WebFormatUtils.priceFormat(getCart().getGoodsPrice() * getCart().getGoodsNumber());
	}
	
    public String getFormatedMarketPrice(){
    	return WebFormatUtils.priceFormat(getCart().getMarketPrice());
    }
    
    public String getGoodsAttr(){
    	if( getCart().getGoodsAttr() != null ){
    		return getCart().getGoodsAttr();
    	}
    	else{
    		return "";
    	}
    	
    }
}
