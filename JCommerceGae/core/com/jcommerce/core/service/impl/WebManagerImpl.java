package com.jcommerce.core.service.impl;

import java.util.List;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.IWebManager;

public class WebManagerImpl extends DefaultManagerImpl implements IWebManager {

	public boolean addToCart(Long goodsId, int num, List spec, String sessionId, String userId, String parentId) {
		
    	try {
    		boolean res = true;
    		Goods goods = (Goods)super.get(Goods.class.getName(), goodsId);
    		
//    		if(StringUtils.isNotEmpty(cartId))
    		
    		Cart cart = new Cart();
    		cart.setGoodsId(goodsId);
    		cart.setSessionId(sessionId);
    		cart.setUserId(userId);
    		cart.setGoodsSN(goods.getGoodsSn());
    		cart.setRecType(Constants.CART_GENERAL_GOODS);
    		cart.setGoodsNumber(num);
    		cart.setGoodsPrice(20.50);
    		cart.setGoodsName(goods.getGoodsName());
    		String cartId = super.txadd(cart);
    		
    		
			return res;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
