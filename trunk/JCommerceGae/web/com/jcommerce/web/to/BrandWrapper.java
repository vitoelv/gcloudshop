package com.jcommerce.web.to;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.URLConstants;

public class BrandWrapper extends BaseModelWrapper implements URLConstants{
	Brand brand;
	@Override
	protected Object getWrapped() {
		return getBrand();
	}
	public BrandWrapper(ModelObject brand) {
		super();
		this.brand = (Brand)brand;
	}
	
	public Brand getBrand() {
		return brand;
	}
	
    public String getBrandId() {
    	return getBrand().getPkId();
    }
	public String getUrl() {
    	return ACTION_GOODS+ getBrand().getPkId();    	
    }


	
	
}
