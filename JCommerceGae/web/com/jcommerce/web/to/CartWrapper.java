package com.jcommerce.web.to;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.web.util.FormatUtils;

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
	
	public String getFormatedGoodsPrice() {
		return FormatUtils.priceFormat(getCart().getGoodsPrice());
	}
	public String getFormatedSubtotal() {
		return FormatUtils.priceFormat(getCart().getGoodsPrice() * getCart().getGoodsNumber());
	}
}
