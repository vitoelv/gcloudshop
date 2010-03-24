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
import com.jcommerce.web.util.SpringUtil;
import com.jcommerce.web.util.WebFormatUtils;

public class CartWrapper extends BaseModelWrapper {

	Cart cart;
	double price;
	
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
		return getCart().getParentId();
	}
	
	public int getIsGift() {
		if(getCart().getIsGift()){
			return 1;
		}
		else{
			return 0;
		}
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
