package com.jcommerce.web.to;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.*;

public class ShopConfigWrapper extends BaseWrapper {
	
	public ShopConfigWrapper() {
		init();
	}
	
	@Override
	public void clear() {
		super.clear();
		init();
	}
	
	public void init() {
		// default values
		this.put("showGoodssn", "true");
		this.put("showGoodsnumber", "true");
		this.put("showBrand", "true");

		this.put("showAddtime", "true");
		this.put("showGoodsnumber", "true");
		this.put("showGoodsnumber", "true");
		this.put("showGoodsnumber", "true");
		this.put("goodsattrStyle", 1);
		this.put("pageSize", "2");
		this.put("pointsName", "积分");
		
		this.put("shopRegClosed", 0);
		this.put("pageStyle", 0);
		
		this.put("oneStepBuy", 2);
		
		//1只显示文字 2只显示图片 3显示文字与图片
		this.put("showGoodsInCart", 3);
		
		this.put("showGoodsAttribute", 0);
		
		// TODO make it configurable thru GUI
//		this.put(CFG_KEY_SHOW_GOODSWEIGHT, "true");
//		this.put(CFG_KEY_SHOW_MARKETPRICE, "true");
//		this.put(CFG_KEY_TIME_FORMAT, "yyyy-MMM-dd");
		
		this.put(CFG_KEY_SHOP_COUNTRY, "agpnY2xvdWRzaG9wciULEgZSZWdpb24iGV8yM2ZiMGJiYjAxMjNmYjBiYmI3NDAwMDAM");

		this.put(CFG_KEY_COMMENT_CHECK, 0);				
	}


}