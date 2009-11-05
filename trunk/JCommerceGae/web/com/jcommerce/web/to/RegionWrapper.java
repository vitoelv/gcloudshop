package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Region;

public class RegionWrapper extends BaseModelWrapper {

	Region region;
	@Override
	protected Object getWrapped() {
		return getRegion();
	}
	public RegionWrapper(ModelObject region) {
		super();
		this.region = (Region)region;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public String getRegionId() {
		return getRegion().getPkId();
	}
	
//	public String getRegion_id() {
//		return getRegion().getPkId();
//	}
//	public String getRegion_name() {
//		return getRegion().getRegionName();
//	}

}
