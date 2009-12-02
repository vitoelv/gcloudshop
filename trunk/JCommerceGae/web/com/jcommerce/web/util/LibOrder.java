package com.jcommerce.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.web.to.CartWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.OrderGoodsWrapper;
import com.jcommerce.web.to.OrderInfoWrapper;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.to.Total;
import com.jcommerce.web.to.UserAddressWrapper;
import com.jcommerce.web.to.WrapperUtil;

public class LibOrder {
	
	/**
	 * 取得订单信息
	 * @param   int     $order_id   订单id（如果order_id > 0 就按id查，否则按sn查）
	 * @param   string  $order_sn   订单号
	 * @return  array   订单信息（金额都有相应格式化的字段，前缀是formated_）
	 */
	public static OrderInfoWrapper orderInfo(String orderId, String orderSn, IDefaultManager manager) {
		OrderInfo order = (OrderInfo)manager.get(ModelNames.ORDERINFO, orderId);
		
		
		return new OrderInfoWrapper(order);
		
		
		
	}
	/**
	 * 取得订单商品
	 * @param   int     $order_id   订单id
	 * @return  array   订单商品数组
	 */
	public static List<OrderGoodsWrapper> orderGoods(String orderId, IDefaultManager manager) {
		Criteria c = new Criteria();
		Condition cond = new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, orderId);
		c.addCondition(cond);
		List<OrderGoods> list = (List<OrderGoods>)manager.getList(ModelNames.ORDERGOODS, c);
		List<OrderGoodsWrapper> res = WrapperUtil.wrap(list, OrderGoodsWrapper.class);
		
		return res;
		
		
		
	}
	
    public static Total orderFee(OrderInfo order, List<Cart> carts, UserAddressWrapper consignee) {
    	// refer to lib_order.php order_fee()
    	Total total = new Total();
    	for(Cart cart:carts) {
    		total.setRealGoodsCount(total.getRealGoodsCount()+1);
    		total.setGoodsPrice(total.getGoodsPrice() + cart.getGoodsPrice()*cart.getGoodsNumber());
    		total.setMarketPrice(total.getMarketPrice() + cart.getMarketPrice()*cart.getGoodsNumber());
    	}
    	total.setAmount(total.getGoodsPrice());
    	return total;
    }
    
    public static Map<String, Object> getCartGoods(String sessionId, IDefaultManager manager, ShopConfigWrapper scw) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	List<CartWrapper> goodsList = new ArrayList<CartWrapper>();
    	Total total = new Total();
    	
    	Criteria c = new Criteria();
    	Condition cond = new Condition(ICart.SESSION_ID, Condition.EQUALS, sessionId);
    	c.addCondition(cond);
    	List<Cart> carts = manager.getList(ModelNames.CART, c);
    	int virtualGoodsCount = 0;
    	int realGoodsCount = 0;
    	
    	
    	
    	for(Cart cart: carts) {
    		CartWrapper cw = new CartWrapper(cart);
    		
    		total.setGoodsPrice(total.getGoodsPrice() + cart.getGoodsPrice()*cart.getGoodsNumber());
    		total.setMarketPrice(total.getMarketPrice() + cart.getMarketPrice()*cart.getGoodsNumber());
    		//设置节省的钱及比例
    		total.setSaving(total.getSaving() + cart.getMarketPrice()*cart.getGoodsNumber() - cart.getGoodsPrice()*cart.getGoodsNumber());
    		total.setSaveRate(total.getSaving() / total.getMarketPrice());
    		
    		if(cart.getIsReal()) {
    			realGoodsCount++;
    		}else {
    			virtualGoodsCount++;
    		}
    		int showGoodsInCart = (Integer)scw.get("showGoodsInCart");
    		if(showGoodsInCart == 2 || showGoodsInCart ==3) {
    			String goodsId = cart.getGoodsId();
    			Goods goods = (Goods)manager.get(ModelNames.GOODS, goodsId);
    			cw.put("goodsThumb", new GoodsWrapper(goods).getGoodsThumb()); 
    		}
    		goodsList.add(cw);
    		cw.put("subtotal", WebFormatUtils.priceFormat(cart.getGoodsPrice()*cart.getGoodsNumber()));
    		cw.put("goodsPrice", WebFormatUtils.priceFormat(cart.getGoodsPrice()));
    		cw.put("marketPrice", WebFormatUtils.priceFormat(cart.getMarketPrice()));
    		
    		
    	}
    	map.put("goodsList", goodsList);
    	map.put("total", total);
    	
    	return map;
    }
}
