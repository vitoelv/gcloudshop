package com.jcommerce.web.to;

import java.util.Iterator;
import java.util.Set;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.web.util.WebFormatUtils;

public class CartWrapper extends BaseModelWrapper {

	Cart cart;
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
		return WebFormatUtils.priceFormat(getCart().getGoodsPrice());
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