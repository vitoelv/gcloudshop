package com.jcommerce.web.test;

import com.jcommerce.core.model.Article;
import com.jcommerce.core.model.ArticleCat;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IArticle;
import com.jcommerce.gwt.client.model.IArticleCat;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.front.action.BaseAction;
import com.jcommerce.web.front.action.IWebConstants;
import com.jcommerce.web.to.ArticleWrapper;
import com.jcommerce.web.to.BrandWrapper;
import com.jcommerce.web.util.LibCommon;
import com.opensymphony.xwork2.Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PerfTestAction extends BaseAction{
    private static Log log = LogFactory.getLog(PerfTestAction.class);
    
    private static String KEY_STEP = "step";
    
    public void debug(String s) {
        log.debug(" in [PerfTestAction]: "+s );
    }
    
    @Override
    public String onExecute() throws Exception {
        
        HttpServletRequest request = getRequest();   
        String step = request.getParameter(KEY_STEP);
        
        Date start = new Date();
        if("para".equals(step)) {
            initParameters(request);
        }else if("brand".equals(step)) {
            includeBrands(request);   
        }else if("goods".equals(step)) {
            includeRecommendBest(request);                
        }else if("other".equals(step)) {
            includeCart();
            includeCategoryTree(request);
            includeNewArticle(request);
        } else {
            // default
            includeRecommendBest(request);
            includeRecommendHot(request);
            includeRecommendNew(request);
        }
        Date end = new Date();
        long time = end.getTime()-start.getTime();
        
        request.setAttribute("step", step);
        request.setAttribute("timeCost", time);
        
        return  Action.SUCCESS;
    }
    
    protected void initParameters(HttpServletRequest request) {
        request.setAttribute("flashTheme", "default");
        // TODO shopNotice
        request.setAttribute("shopNotice", getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOP_NOTICE));
        
    }
    
    public void includeBrands(HttpServletRequest request) {
        // brands.ftl
        List<Brand> brands = (List<Brand>)getDefaultManager().getList(ModelNames.BRAND, null,0,11);
        
        Criteria criteria = new Criteria();
        Condition cond = new Condition();
        cond.setField(IGoods.BRAND_ID);
        cond.setOperator(Condition.EQUALS);
        List<BrandWrapper> brandInfoList = new ArrayList<BrandWrapper>();
        for (Brand brand:brands) {
            cond.setValue(brand.getPkId());
            criteria.addCondition(cond);
            Integer goodsNum = getDefaultManager().getCount(ModelNames.GOODS, criteria);
            BrandWrapper bw = new BrandWrapper(brand);
            bw.put("goodsNum", goodsNum);
            bw.put("brandLogo", URLConstants.SERVLET_BRANDLOGO+brand.getLogoFileId());
            brandInfoList.add(bw);
            criteria.removeAllCondition();
        }        
        request.setAttribute("brandList", brandInfoList);
        
        
    }
    public void includeNewArticle(HttpServletRequest request) {
        // new_articles.ftl
//      request.setAttribute("new_articles", articleManager.getArticleList());
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IArticleCat.CAT_TYPE,Condition.EQUALS,"1"));
        List<ArticleCat> articleCats = getDefaultManager().getList(ModelNames.ARTICLE_CAT, criteria);
        List articleList = new ArrayList();
        int limit = getCachedShopConfig().getInt(IShopConfigMeta.CFG_KEY_ARTICLE_NUMBER);
        Map acMap = new HashMap();
        for (ArticleCat ac : articleCats) {
            criteria.removeAllCondition();
            criteria.addCondition(new Condition(IArticle.IS_OPEN,Condition.EQUALS,"true"));
            criteria.addCondition(new Condition(IArticle.ARTICLE_CAT_ID,Condition.EQUALS,ac.getPkId()));
            articleList.addAll(getDefaultManager().getList(ModelNames.ARTICLE, criteria, 0, limit));
            acMap.put(ac.getPkId(), ac.getCatName());
        }
                
        List newArticles = new ArrayList();
        limit = limit > articleList.size() ? articleList.size() : limit;
        for (int i = 0 ; i < limit ; i++){
            ArticleWrapper aw = new ArticleWrapper((Article)articleList.get(i));
            Map res = new HashMap();
            res.put("id", aw.getPkId());
            res.put("title", aw.getArticle().getTitle());
            res.put("shortTitle", aw.getShortTitle());
            
            res.put("catName", acMap.get(aw.getArticle().getArticleCatId()));
            res.put("addTime", aw.getAddTime());
            res.put("url", aw.getUrl());
            Map map = new HashMap();
            map.put("acid", aw.getArticle().getArticleCatId());
            res.put("catUrl", LibCommon.buildUri(IWebConstants.APP_ARTICLE_CAT, map , ""));
            newArticles.add(res);
        }
        request.setAttribute("newArticles", newArticles );      
    }

}
