package com.jcommerce.web.to;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.URLConstants;
import com.jcommerce.web.util.WebFormatUtils;

public class GoodsWrapper extends BaseModelWrapper implements URLConstants{
	
	public static final String GOODS_BRAND = "goodsBrand";
	
	Goods goods;
	@Override
	protected Object getWrapped() {
		return getGoods();
	}
	public GoodsWrapper(ModelObject goods) {
		super();
		this.goods = (Goods)goods;
	}
	
	public Goods getGoods() {
		return goods;
	}
	
	
    // for template
    public Long getGoodsId() {
    	return getGoods().getLongId();
    }
    public String getName() {
    	return getGoods().getGoodsName();
    }
    public String getGoodsImg() {
    	return SERVLET_IMAGE+getGoods().getImageFileId();
    }
    public String getUrl() {
    	return ACTION_GOODS+getGoods().getPkId();    	
    }
    public String getShortStyleName() {
    	return getGoods().getGoodsName().length()>10? getGoods().getGoodsName().substring(0, 10)+"...":getGoods().getGoodsName();
    }
    public String getGoodsStyleName() {
    	return StringUtils.defaultIfEmpty(getGoods().getGoodsNameStyle(), getGoods().getGoodsName());
    }
    public String getMeasureUnit() {
    	return "TODO measureUnit";
    	
    }
    public String getGoodsBrandUrl() {
    	return ACTION_BRAND+getGoods().getBrandId();  
    }

    public String getShopPriceFormated() {
    	return WebFormatUtils.priceFormat(getGoods().getShopPrice());
    }
    
    public String getCommentRank() {
    	return "TODO comment Rank";
    }
    
    public String getBonusMoney() {
    	return "TODO bonus Money";
    }
    
    public String getThumb() {
    	return SERVLET_IMAGE+getGoods().getImageFileId();
    }
    public String getGoodsThumb() {
    	return SERVLET_IMAGE+getGoods().getImageFileId();
    }
    
    public String getPromotePrice() {
    	return WebFormatUtils.priceFormat(getGoods().getPromotePrice());
    }
    
//    public String getGoodsNumber() {
//    	return String.valueOf(getGoods().getPromotePrice());
//    }


}
