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
import com.jcommerce.web.util.WebFormatUtils;

public class GoodsWrapper extends BaseModelWrapper implements URLConstants{
	
	public static final String GOODS_BRAND = "goodsBrand";
	private IDefaultManager manager = null;
	
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
     /*修改,获得用户评价等级*/
    public String getCommentRank() {
    	String id = getGoods().getPkId();
    	Condition codition = new Condition(IComment.ID_VALUE,Condition.EQUALS,id);
        Criteria criteria = new Criteria();
        criteria.addCondition(codition);
        List<Comment> comments = (List<Comment>) manager.getList(ModelNames.COMMENT,criteria);
        Long sum = 0L;
        int number = comments.size();
        for(Iterator iterator = comments.iterator();iterator.hasNext();) {
        	Comment comment = (Comment) iterator.next();
        	Long rank = comment.getCommentRank();
        	sum += rank;
        }
        String rank = "0";
        if(number > 0)
        	rank = ((int)Math.ceil((double)sum / number)) + "";
    	return rank;
    }
    /*修改完*/
    
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
    
    public void setManager(IDefaultManager manager) {
    	this.manager = manager;
    }
    
//    public String getGoodsNumber() {
//    	return String.valueOf(getGoods().getPromotePrice());
//    }
    public String getType(){
    	return goods.getGoodsTypeId();
    }


}
