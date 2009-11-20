package com.jcommerce.web.to;

import com.jcommerce.core.model.GoodsGallery;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.util.URLConstants;

public class GoodsGalleryWrapper extends BaseModelWrapper implements URLConstants{

	GoodsGallery goodsGallery;
	@Override
	protected Object getWrapped() {
		return getGoodsGallery();
	}
	public GoodsGalleryWrapper(ModelObject goodsGallery) {
		super();
		this.goodsGallery = (GoodsGallery)goodsGallery;
	}
	
	public GoodsGallery getGoodsGallery() {
		return goodsGallery;
	}
	
	public Long getImageId() {
		return getGoodsGallery().getLongId();
	}
	public String getThumbUrl() {
		if (getGoodsGallery().getThumbFileId() == null)
			return null;
		return SERVLET_IMAGE + getGoodsGallery().getThumbFileId();
	}
	public String getImgUrl() {
		return SERVLET_IMAGE + getGoodsGallery().getImageFileId();
	}

}
