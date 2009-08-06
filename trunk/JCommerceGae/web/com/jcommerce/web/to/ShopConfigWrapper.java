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
	
	public ShopConfig getSC() {
		return shopConfig;
	}
	
	public Boolean getShowGoodsnumber() {
		// a sample to wrap it
//		if(getSC().getShowGoodsnumber() == true) {
//			return Boolean.TRUE;
//		}else {
//			return null;
//		}
		return null;
	}
	
	public Boolean getShowGoodssn() {
		return null;
	}
	public Boolean getShowGoodsweight() {
		return null;
	}
	public Boolean getShowBrand() {
		return null;
	}
	public Boolean getShowAddtime() {
		return null;
	}
	
	public Boolean getShowMarketprice() {
		return null;
	}
}
