package com.jcommerce.web.front.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.util.LibGoods;
import com.jcommerce.web.util.WebUtils;
import com.jcommerce.web.util.LibGoods.GoodsPropertiesResult;

public class GoodsAction extends BaseAction {
	
	public static final String KEY_GOODS_ID = "goodsId";
	public static final String KEY_PROMOTE_END_TIME = "promoteEndTime";
	public static final String KEY_NOW_TIME = "nowTime";
	
	public void debug(String s) {
		System.out.println(" in [GoodsAction]: "+s );
	}
	
	@Override
	public String execute() throws Exception {
		try {
		super.execute();
        
        HttpServletRequest request = getRequest();        

        
        includeCart();
        includeCategoryTree(request);
        
        includeGoodsRelated(request);
        includeGoodsFittings(request);
        includeGoodsArticle(request);
        includeGoodsAttrLinked(request);
        includeHistory(request);
        includeGoodsGallery(request);
        includeGoodsTags(request);
        includeBoughtGoods(request);
        includeComments(request);
        // goods logic
        
        String goodsId = request.getParameter("id");
        debug("in [execute]: goodsId="+goodsId);
        Long goodsIdLong = WebUtils.tryGetLongId(goodsId);
        
        
        IDefaultManager manager = getDefaultManager();
        Goods goods = goodsIdLong == null? (Goods)manager.get(ModelNames.GOODS, goodsId) : 
        	(Goods)manager.get(ModelNames.GOODS, goodsIdLong);
        // make it always be pkId instead of LongId string
        goodsId = goods.getPkId();
        
        GoodsWrapper gw = new GoodsWrapper(goods);
        request.setAttribute("goods", gw);
        
        String brandId = goods.getBrandId();
        Brand brand = (Brand)manager.get(ModelNames.BRAND, brandId);
        gw.put(GoodsWrapper.GOODS_BRAND, brand.getBrandName());
        
        
        request.setAttribute("rankPrices", new String[0]);
        
        
        
        GoodsPropertiesResult result = LibGoods.getGoodsProperties(goodsId, manager); // 获得商品的规格和属性
        request.setAttribute("properties", result.getPro());
        request.setAttribute("specification", result.getSpe());
        
        includeComment(IComment.TYPE_GOODS, goodsId);
        
        request.setAttribute(KEY_GOODS_ID, gw.getGoodsId());
        request.setAttribute(KEY_PROMOTE_END_TIME, goods.getPromoteEndDate());
        request.setAttribute(KEY_NOW_TIME, new Date().getTime());
        
        // TODO 
        Map<String, String> affiliate = new HashMap<String, String>();
        affiliate.put("on", "yes");
        request.setAttribute("affiliate", affiliate);
        
        request.setAttribute("pointsName", getCachedShopConfig().get("pointsName"));
        
        return SUCCESS;
        
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void includeGoodsRelated(HttpServletRequest request) {
	}
	public void includeGoodsFittings(HttpServletRequest request) {
	}
	public void includeGoodsArticle(HttpServletRequest request) {
	}
	public void includeGoodsAttrLinked(HttpServletRequest request) {
		request.setAttribute("attributeLinked", new String[0]);
	}

	public void includeGoodsGallery(HttpServletRequest request) {
	}
	public void includeGoodsTags(HttpServletRequest request) {
		Lang lang = getLangMap(request);
		lang.put("goodsTag", "标签");
		
		request.setAttribute("tags", new String[0]);
	}
	public void includeBoughtGoods(HttpServletRequest request) {
	}



}
