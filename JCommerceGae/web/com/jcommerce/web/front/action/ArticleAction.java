package com.jcommerce.web.front.action;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_NAME;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcommerce.core.model.Article;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IArticle;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.web.to.ArticleWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibMain;
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
        includeCart();
        includeCategoryTree(request);
        includeRecommendBest(request);
        includeRecommendHot(request);
        includeRecommendNew(request);
        includeComments(request);
        
        
        String articleId = request.getParameter("id");
        article = getArticleInfo(articleId);
        
        
        LibMain.assignUrHere(getRequest(), article.getArticle().getArticleCatId(), article.getArticle().getTitle());

        includeComment(IComment.TYPE_ARTICLE, articleId);
        
        request.setAttribute("type", 1);
      //TODO captcha验证码 应从后台设置
		request.setAttribute("enabledCaptcha", false);
		request.setAttribute("rand", new Double(1000000*Math.random()).longValue());
		request.setAttribute("keywords", article.getString("keywords"));
		request.setAttribute("descriptions", article.getString("descriptions"));
		
		 /* 上一篇下一篇文章 */
		includeAroundArticles();
		
        return  Action.SUCCESS;
    }
   
    
    
    private void includeAroundArticles() {
    	Criteria criteria = new Criteria();
    	criteria.addCondition(new Condition(IArticle.ARTICLE_CAT_ID , Condition.EQUALS, article.getArticle().getArticleCatId()));
		List<Article> articleList = getDefaultManager().getList(ModelNames.ARTICLE, criteria);
		
		if( articleList != null ){
			int num = articleList.indexOf(article.getArticle());
			if(num > 0){
				ArticleWrapper aw = new ArticleWrapper(articleList.get(num-1));
				Map map = new HashMap();
	    		map.put("aid", aw.getPkId());
	    		
				aw.put("url", LibCommon.buildUri(IWebConstants.APP_ARTICLE, map , "" , 0 , 0) );
				getRequest().setAttribute("prevArticle", aw	);
			}
			if( num < (articleList.size() - 1 )){
				ArticleWrapper aw = new ArticleWrapper(articleList.get(num+1));
				Map map = new HashMap();
	    		map.put("aid", aw.getPkId());
				aw.put("url", LibCommon.buildUri(IWebConstants.APP_ARTICLE, map , "" , 0 , 0) );
				getRequest().setAttribute("nextArticle", aw	);
			}
		}
		
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

	public ArticleWrapper getArticleInfo(String articleId){
		ArticleWrapper aw ;
		Lang lang = getLangMap(getRequest());
		String shopName = getCachedShopConfig().getString(CFG_KEY_SHOP_NAME);
		Article article = (Article)getDefaultManager().get(ModelNames.ARTICLE, articleId);
		if( article == null ){
			article = new Article();
			article.setTitle(lang.getString("articleNotFoundTitle"));
			article.setContent(lang.getString("articleNotFoundContent"));
		}
		
		if(article.getAuthor() == null || article.getAuthor().equals("_SHOPHELP") ){
			article.setAuthor(shopName);
        }
        if(article.getAddTime() == 0L ){
        	article.setAddTime(System.currentTimeMillis());
        }

		aw = new ArticleWrapper(article);
		
		Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IComment.ID_VALUE,Condition.EQUALS,articleId));
        List<Comment> comments = (List<Comment>) getDefaultManager().getList(ModelNames.COMMENT,criteria);
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
        aw.put("commentRank", rank);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        aw.put("addTime", sdf.format(aw.getArticle().getAddTime()));
        
        
		return aw;
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
