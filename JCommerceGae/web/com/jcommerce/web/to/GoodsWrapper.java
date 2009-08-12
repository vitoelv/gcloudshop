package com.jcommerce.web.to;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.URLConstants;

public class GoodsWrapper extends BaseModelWrapper implements URLConstants{
	
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
    public String getGoodsId() {
    	String longId = getGoods().getLongId().toString();
    	return longId;
    }
    public String getGoodsImg() {
    	return SERVLET_IMAGE+getGoods().getImageFileId();
    }
    public String getUrl() {
    	return ACTION_GOODS+getGoods().getId();    	
    }
    public String getShortStyleName() {
    	return getGoods().getGoodsName().length()>10? getGoods().getGoodsName().substring(0, 10)+"...":getGoods().getGoodsName();
    }
    public String getGoodsStyleName() {
    	return StringUtils.defaultIfEmpty(getGoods().getGoodsNameStyle(), "goodsStyleName");
    }
    public String getMeasureUnit() {
    	return "TODO measureUnit";
    }
    public String getGoodsBrandUrl() {
    	return "TODO goodBrandUrl";
    }
    public String getGoodsBrand() {
    	return "TODO brandName: ";
    }
    public String getShopPriceFormatted() {
    	return ""+getGoods().getShopPrice();
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
    
    public String getPromotePrice() {
    	return String.valueOf(getGoods().getPromotePrice());
    }
    
//    public String getGoodsNumber() {
//    	return String.valueOf(getGoods().getPromotePrice());
//    }


}
