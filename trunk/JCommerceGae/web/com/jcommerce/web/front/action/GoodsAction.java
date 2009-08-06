package com.jcommerce.web.front.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.WrapperUtil;
import com.opensymphony.xwork2.ActionContext;

public class GoodsAction extends BaseAction {

	public void debug(String s) {
		System.out.println(" in [GoodsAction]: "+s );
	}
	
	@Override
	public String execute() throws Exception {
		try {
		super.execute();
        
		ActionContext ctx = ActionContext.getContext();        
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);        
        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE); 

        
        includeUrHere(request);
        includeCart(request);
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
        includeHelp(request);
        
        // goods logic
        
        String goodsId = request.getParameter("id");
        debug("in [execute]: goodsId="+goodsId);
        
        IDefaultManager manager = getDefaultManager();
        Goods goods = (Goods)manager.get(ModelNames.GOODS, goodsId);
        
        request.setAttribute("goods", WrapperUtil.wrap(goods, GoodsWrapper.class));
        

        
        
        request.setAttribute("specification", new String[0]);
        request.setAttribute("rankPrices", new String[0]);
        request.setAttribute("properties", new String[0]);
        request.setAttribute("specification", new String[0]);
        
        request.setAttribute("goodsId", goods.getId());
        request.setAttribute("promoteEndTime", "TODO promoteEndTime");
        request.setAttribute("nowTime", "TODO nowTime");
        
        // TODO 
        Map<String, String> affiliate = new HashMap<String, String>();
        affiliate.put("on", "yes");
        request.setAttribute("affiliate", affiliate);
        
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
