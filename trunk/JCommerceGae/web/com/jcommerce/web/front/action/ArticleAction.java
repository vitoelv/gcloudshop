package com.jcommerce.web.front.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcommerce.core.model.Article;
import com.jcommerce.web.to.ArticleWrapper;
import com.opensymphony.xwork2.Action;

public class ArticleAction extends BaseAction {
    private static Log log = LogFactory.getLog(ArticleAction.class);
    private ArticleWrapper article = null;

	public void debug(String s) {
		System.out.println(" in [ArticleAction]: "+s );
	}
    

    @Override
    public String getText(String s) {
    	return s;
    	// TODO overcome the access error
//    	return super.getText(s);
    }
    


   

    @Override
    public String onExecute() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'article' method...");
        }
        debug("in execute");
        HttpServletRequest request = getRequest();     
        super.execute();
        includeCart();
        includeCategoryTree(request);
        includeRecommendBest(request);
        includeRecommendHot(request);
        includeRecommendNew(request);
        
        article = new ArticleWrapper(new Article());
        article.put("title", "找不到文章");

        article.put("content","维护中...");
        
        super.afterExecute();
        return  Action.SUCCESS;
    }
   
    
    
    int page = 0;
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
   



	public ArticleWrapper getArticle() {
		return article;
	}


	public void setArticle(ArticleWrapper article) {
		this.article = article;
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
