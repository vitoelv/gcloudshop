package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.ShopConfig;

public class ShopConfigWrapper extends BaseModelWrapper {

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
		return scw;
		
	}
	
	public ShopConfig getSC() {
		return shopConfig;
	}
	

}
