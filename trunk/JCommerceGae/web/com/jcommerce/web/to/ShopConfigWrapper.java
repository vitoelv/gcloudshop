package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.web.front.action.IWebConstants;

public class ShopConfigWrapper extends BaseModelWrapper {
	
	public static final String CFG_KEY_SHOP_COUNTRY = "shop_country";
	public static final String CFG_KEY_TIME_FORMAT = "time_format";
	public static final String CFG_KEY_COMMENT_CHECK = "comment_check";
	
	@Override
	protected Object getWrapped() {
		return getSC();
	}
	
	ShopConfig shopConfig;
	public ShopConfigWrapper(ModelObject sc) {
		this.shopConfig = (ShopConfig)sc;
		
		// TODO just temporarily overcome exceptions
	}
	
	public static final ShopConfigWrapper getDefaultConfig() {
		
		ShopConfig sc =  new ShopConfig();
		ShopConfigWrapper scw = (ShopConfigWrapper)WrapperUtil.wrap(sc, ShopConfigWrapper.class);
		scw.put("showGoodssn", "true");
		scw.put("showGoodsnumber", "true");
		scw.put("showBrand", "true");
		scw.put("showGoodsweight", "true");
		scw.put("showAddtime", "true");
		scw.put("showMarketprice", "true");
		scw.put("showGoodsnumber", "true");
		scw.put("showGoodsnumber", "true");
		scw.put("showGoodsnumber", "true");
		scw.put("goodsattrStyle", 1);
		scw.put("pageSize", "2");
		scw.put("pointsName", "积分");
		
		scw.put("shopRegClosed", 0);
		scw.put("pageStyle", 0);
		
		scw.put("oneStepBuy", 2);
		
		//1只显示文字 2只显示图片 3显示文字与图片
		scw.put("showGoodsInCart", 3);
		
		scw.put("showGoodsAttribute", 0);
		
		// TODO make it configurable thru GUI
		scw.put(CFG_KEY_SHOP_COUNTRY, "agpnY2xvdWRzaG9wciULEgZSZWdpb24iGV8yM2ZiMGJiYjAxMjNmYjBiYmI3NDAwMDAM");
		scw.put(CFG_KEY_TIME_FORMAT, "yyyy-MMM-dd");
		scw.put(CFG_KEY_COMMENT_CHECK, 0);
		return scw;
		
	}
	
	public ShopConfig getSC() {
		return shopConfig;
	}
	
	

}
