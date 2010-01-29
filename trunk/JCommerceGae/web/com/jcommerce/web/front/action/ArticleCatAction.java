package com.jcommerce.web.front.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcommerce.core.model.Article;
import com.jcommerce.core.model.ArticleCat;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IArticle;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.to.ArticleCatWrapper;
import com.jcommerce.web.to.ArticleWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.util.LibGoods;
import com.jcommerce.web.util.LibMain;
import com.jcommerce.web.util.WebUtils;
import com.opensymphony.xwork2.Action;

public class ArticleCatAction extends BaseAction {
    private static Log log = LogFactory.getLog(ArticleCatAction.class);

	public void debug(String s) {
		System.out.println(" in [ArticleAction]: "+s );
	}
	ArticleCat articleCat = null;

    @Override
    public String getText(String s) {
    	return s;
    	// TODO overcome the access error
//    	return super.getText(s);
    }
    


   

    @Override
    public String onExecute() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'articleCat' method...");
        }
        debug("in execute");
       
        HttpServletRequest request = getRequest();     
        Long acLongId = WebUtils.tryGetLongId(request.getParameter("id"));
        String acId = acLongId == null? request.getParameter("id") : null;
        
        if(acLongId!=null) {
        	articleCat = (ArticleCat)getDefaultManager().get(ModelNames.ARTICLE_CAT, acLongId);
		}else {
			articleCat = (ArticleCat)getDefaultManager().get(ModelNames.ARTICLE_CAT, acId);
		}
//        int page = request.getParameter("page") == null ? Integer.parseInt(request.getParameter("page")) : 1;

        includeCart();
        includeCategoryTree(request);
        includeRecommendBest(request);
        includeRecommendHot(request);
        includeRecommendNew(request);
        includeArticleCatInfo();
        includeArticleCategoriesTree(request);
        
        
        LibMain.assignUrHere(getRequest(), articleCat.getPkId(), "");

        
		
        return  Action.SUCCESS;
    }
   
	int page = 1;
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
   
	public void includeArticleCatInfo(){
		HttpServletRequest request = getRequest();     
		Lang lang = getLangMap(getRequest());
		
		if( articleCat == null ){
			return ;
		}
		
		request.setAttribute("keywords", articleCat.getKeywords() == null ? "" : articleCat.getKeywords());
		request.setAttribute("descriptions", articleCat.getCatDesc() == null ? "" : articleCat.getCatDesc() );
		
		int size = getCachedShopConfig().getInt(IShopConfigMeta.CFG_KEY_ARTICLE_NUMBER);
		size = ( size > 0 ) ? size : 10; 
				
		Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IArticle.ARTICLE_CAT_ID,Condition.EQUALS,articleCat.getPkId()));
        List<Article> articles = (List<Article>) getDefaultManager().getList(ModelNames.ARTICLE,criteria,size*(page-1),size*page);
     
        LibMain.assignPager("article_cat", articleCat.getLongId() , articles.size() , size, "", "", page,
				"", "", 0, 0, "", "", "", null, getRequest(), getCachedShopConfig());
		request.setAttribute("artcilesList", WrapperUtil.wrap(articles, ArticleWrapper.class)); 
	}
	
	public void includeArticleCategoriesTree(HttpServletRequest request) {
        List<ArticleCatWrapper> list = LibGoods.getArticleCategoriesTree(null, getDefaultManager());
        request.setAttribute("articleCategories", list);		
	}

	int pageSize = 0;
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    int brand;
    public void setBrand(int brand) {
        this.brand = brand;
    }
    public int getBrand() {
        return this.brand;
    }
    int priceMax;
    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }
    public int getPriceMax() {
        return this.priceMax;
    }
    int priceMin;
    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }
    public int getPriceMin() {
        return this.priceMin;
    }
    String filterAttr = "";
    public void setFilterAttr(String fa) {
        this.filterAttr = fa;
    }
    public String getFilterAttr() {
        return this.filterAttr;
    }
}
