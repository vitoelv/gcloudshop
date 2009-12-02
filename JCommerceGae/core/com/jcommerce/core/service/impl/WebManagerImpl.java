package com.jcommerce.core.service.impl;

import java.util.Iterator;
import java.util.List;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IWebManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.core.service.Condition;

public class WebManagerImpl extends DefaultManagerImpl implements IWebManager {

	public boolean addToCart(Long goodsId, long num, List spec, String sessionId, String userId, String parentId) {
		
    	try {
    		boolean res = true;
    		Goods goods = (Goods)super.get(Goods.class.getName(), goodsId);
    		
//    		if(StringUtils.isNotEmpty(cartId))

    		String goodsSpecId = "";
    		for(Iterator iterator = spec.iterator();iterator.hasNext();) {
    			String id = (String) iterator.next();
    			if(!goodsSpecId.equals(""))
    				goodsSpecId += ",";
    			goodsSpecId += id;
    		}
    		
    		//查找购物车中是否已有该商品，且规格不一样，如果有，将商品数加一
    		Criteria criteria = new Criteria();
    		criteria.addCondition(new Condition(ICart.SESSION_ID,Condition.EQUALS,sessionId));
    		criteria.addCondition(new Condition(ICart.GOODS_ID,Condition.EQUALS,goods.getPkId()));
    		criteria.addCondition(new Condition(ICart.GOODS_ATTR_ID,Condition.EQUALS,goodsSpecId));
    		List<Cart> carts = super.getList(ModelNames.CART, criteria); 
    		if(carts.size() > 0) {
    			Cart cart = carts.get(0);
    			cart.setGoodsNumber(cart.getGoodsNumber() + num);
    			super.txattach(cart);
    		}
    		
    		//不存在该商品
    		else {
	    		Cart cart = new Cart();
	    		cart.setGoodsId(goods.getPkId());
	    		cart.setSessionId(sessionId);
	    		cart.setUserId(userId);
	    		cart.setGoodsSn(goods.getGoodsSn());
	    		cart.setRecType(Constants.CART_GENERAL_GOODS);
	    		cart.setGoodsNumber(num);
	    		cart.setGoodsPrice(goods.getShopPrice());
	    		cart.setMarketPrice(goods.getMarketPrice());
	    		cart.setGoodsName(goods.getGoodsName());
	    		cart.setGoodsWeight(goods.getGoodsWeight());
	    		
	    		//获得商品规格
	    		String goodsSpec = "";
	    		for(Iterator iterator = spec.iterator();iterator.hasNext();) {
	    			long goodsAttrId = Long.parseLong((String) iterator.next());
	    			
	    			GoodsAttr goodsAttr = (GoodsAttr) super.get(ModelNames.GOODSATTR, goodsAttrId);
	    			String attrId = goodsAttr.getAttrId();
	    			Attribute attribute = (Attribute) super.get(ModelNames.ATTRIBUTE, attrId);
	    			
	    			String attrName = attribute.getAttrName();
	    			String attrValue = goodsAttr.getAttrValue();
	    			String attrPrice = goodsAttr.getAttrPrice();
	    			
	    			if(attrPrice == null || Double.parseDouble(attrPrice) == 0) {
	    				goodsSpec += attrName + ":" + attrValue + "<br>";
	    			}
	    			else {
	    				goodsSpec += attrName + ":" + attrValue + "[" + attrPrice + "]" + "<br>";
	    			}
	    		}
	    		cart.setGoodsAttrId(goodsSpecId);
	    		cart.setGoodsAttr(goodsSpec);
	    		
	    		String cartId = super.txadd(cart);
    		}
    		
			return res;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
