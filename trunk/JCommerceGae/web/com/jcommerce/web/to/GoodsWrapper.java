package com.jcommerce.web.to;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.util.SpringUtil;
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
    	return getGoods().getGoodsName().length()>17? getGoods().getGoodsName().substring(0, 10)+"...":getGoods().getGoodsName();
    }
    public String getGoodsStyleName() {
    	return StringUtils.defaultIfEmpty(getGoods().getGoodsNameStyle(), getGoods().getGoodsName());
    }
    public String getMeasureUnit() {
        // TODO
    	return "";
    	
    }
    public String getGoodsBrandUrl() {
    	return ACTION_BRAND+getGoods().getBrandId();  
    }

    public String getShopPriceFormated() {
    	return WebFormatUtils.priceFormat(getGoods().getShopPrice());
    }
    
    public String getBonusMoney() {
        // TODO
    	return null;
    }
    
    public String getThumb() {
    	return SERVLET_IMAGE+getGoods().getImageFileId();
    }
    public String getGoodsThumb() {
    	return SERVLET_IMAGE+getGoods().getImageFileId();
    }
    
    public String getPromotePrice() {
    	if(getIsPromote()!=null){
    		return WebFormatUtils.priceFormat(getGoods().getPromotePrice());
    	}
    	else{
    		return "";
    	}
    }
    
    public String getIsPromote() {
    	if(!getGoods().getIsPromote())
    		return null;
    	else
    		return "true";
    }
    
    public String getMarketPrice(){
    	return WebFormatUtils.priceFormat(getGoods().getMarketPrice());
    }
        
    public String getShopPrice(){
    	return WebFormatUtils.priceFormat(getGoods().getShopPrice());
    }
    
//    public String getGoodsNumber() {
//    	return String.valueOf(getGoods().getPromotePrice());
//    }
    public String getType(){
    	return goods.getGoodsTypeId()==null?"":goods.getGoodsTypeId();
    }
    
    public String getShortName(){
    	return getShortStyleName();
    }


}
