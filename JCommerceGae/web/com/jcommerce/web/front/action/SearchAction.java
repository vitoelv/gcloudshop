package com.jcommerce.web.front.action;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOW_ORDER_TYPE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SORT_ORDER_METHOD;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SORT_ORDER_TYPE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.compass.core.Compass;
import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsAttr;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibMain;
import com.jcommerce.web.util.WebFormatUtils;
import com.opensymphony.xwork2.Action;

public class SearchAction extends BaseAction {
    private static Log log = LogFactory.getLog(SearchAction.class);
    private String action ;

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
        Lang lang = getLangMap(request);

        
        Criteria criteria = new Criteria();
    	Condition cond = null;
    	
    	Map<String,Object> attributes = null;
    	request.setAttribute("attributeLinked", new String[0]);
    	String act = StringUtil.isEmpty(request.getParameter("act")) ? "" : request.getParameter("act");
        if(act.equals("advanced_search")){
        	Long goodsType = (request.getParameter("goods_type") != null)? Long.parseLong(request.getParameter("goods_type")) : 0 ;
        	attributes = getSearchableAttributes(goodsType);
        	request.setAttribute("goodsTypeSelected", goodsType );
        	request.setAttribute("goodsTypeList", attributes.get("cate"));
        	request.setAttribute("goodsAttributes", attributes.get("attr"));
        	
        	Map<String,Object> advValue = new HashMap<String,Object>();
    		advValue.put("keywords", "");
    		advValue.put("brand", "");
    		advValue.put("minPrice", "");
    		advValue.put("maxPrice", "");
    		advValue.put("category", "");
        	request.setAttribute("advVal", advValue);
        	
        	request.setAttribute("scck", "");
        	
        	request.setAttribute("catList", LibCommon.selectCatList(null, "0" , 2, false, getDefaultManager()));
        	request.setAttribute("brandList", LibCommon.getBrandList(getDefaultManager()));
        	request.setAttribute("useStorage", 1);
        	
        	LibMain.assignUrHere(request, "", Lang.getInstance().getString("advancedSearch"));
        	
        	action = "form";
//        	request.setAttribute("useStorage", ((ShopConfig)request.getAttribute("cfg")).getStoreRange());
        	
        }else if(act.equals("compassSearch")) {
        	String keywords = request.getParameter("keywords") != null ? request.getParameter("keywords").trim() : "";
        	String category = request.getParameter("category") != null ? request.getParameter("category").trim() : "0";
        	if(!category.equals("0"))
        		keywords = keywords + " " + category;
        	Compass compass = PMF.getCompass();
        	CompassSession session = compass.openSession();        	
        	CompassHits hits = session.find(keywords);
        	List<String> ids = new ArrayList<String>();
        	
        	for(Iterator i = hits.iterator(); i.hasNext();) {
        		CompassHit hit = (CompassHit) i.next();
        		String goodsId = ((Goods) hit.getData()).getPkId();
        		ids.add(goodsId);
        	}
        	List<Goods> goodsList = getDefaultManager().getListByIds(ModelNames.GOODS, ids);
        	session.close();
        	
        	String intromode = request.getParameter("intro") != null ? request.getParameter("intro").trim() : "";
    		String urHere = "";
    		
    		if(!StringUtil.isEmpty(intromode)){
    			if(intromode.equals("best")){
            		urHere = Lang.getInstance().getString("bestGoods");
    			}
    			else if(intromode.equals("new")){
            		urHere = Lang.getInstance().getString("newGoods");
    			}
    			else if(intromode.equals("hot")){
            		urHere = Lang.getInstance().getString("hotGoods");
    			}
    			else if(intromode.equals("promotion")){
            		urHere = Lang.getInstance().getString("promotionGoods");
    			}
    			else{
    				intromode = "";
    			}
    		}
    		else{
    			intromode = "";
    		}
    		
    		if(StringUtil.isEmpty(urHere)){
    			urHere = Lang.getInstance().getString("searchGoods");
    		}
    		
    		String sSize = getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_PAGE_SIZE);
    		int size = (sSize!=null && Integer.valueOf(sSize)>0) ? Integer.valueOf(sSize) : 10; 
    		Map<String,Object> search = new HashMap<String,Object>();
    		search.put("keywords", keywords);
    		search.put("act", "compassSearch");
    		String sPage = (String)request.getParameter("page");
    		int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
    		Pager pager = LibMain.getPager("search.action", search, hits.length(), page, size, getCachedShopConfig());
    		String display = (String)request.getParameter("display");
    		if(display == null){
    			display = getCachedShopConfig().getString(CFG_KEY_SHOW_ORDER_TYPE);
    		}
    		getSession().setAttribute("displaySearch", display);
    		pager.setDisplay(display);
    		
    		LibMain.assignUrHere(request, "", urHere);
    		request.setAttribute("pager", pager);
    		request.setAttribute("intromode", intromode);
        	request.setAttribute("goodsList", WrapperUtil.wrap(goodsList, GoodsWrapper.class));
        	
        }else{
        	String keywords = request.getParameter("keywords") != null ? request.getParameter("keywords").trim() : "";
        	String brand = request.getParameter("brand") != null ? request.getParameter("brand").trim() : "0";
        	String category = request.getParameter("category") != null ? request.getParameter("category").trim() : "0";
        	int minPrice = request.getParameter("min_price") != null ? Integer.parseInt(request.getParameter("min_price").equals("") ? "0" : request.getParameter("min_price")) : 0;
        	int maxPrice = request.getParameter("max_price") != null ? Integer.parseInt(request.getParameter("max_price").equals("") ? "0" : request.getParameter("max_price")) : 0;
        	Long goodsType = request.getParameter("goods_type") != null ? Long.parseLong(request.getParameter("goods_type").trim()) : 0;
        	int scDs = request.getParameter("scDs") != null ? Integer.parseInt(request.getParameter("scDs")) : 0;
        	int outstock = request.getParameter("outstock") != null ? 1: 0;
       
        	
        	if(request.getParameter("action") != null && request.getParameter("action").equals("form")){
        		/* 要显示高级搜索栏 */
        		Map<String,Object> advValue = new HashMap<String,Object>();
        		advValue.put("keywords", keywords);
        		advValue.put("brand", brand);
        		advValue.put("minPrice", minPrice);
        		advValue.put("maxPrice", maxPrice);
        		advValue.put("category", category);
        		
        		attributes = getSearchableAttributes(goodsType);
        		
        		/* 将提交数据重新赋值 */
        		for (Map<String,Object> val : (List<Map<String,Object>>)attributes.get("attr")) {
					if(request.getParameter("attr["+val.get("id")+"]") != null){
						if((Long)val.get("type") == 2){
							Map<String,String> map = new HashMap<String,String>();
							map.put("from", request.getParameter("attr["+val.get("id")+"][from]") != null ? request.getParameter("attr["+val.get("id")+"][from]").trim() : "");
							map.put("to", request.getParameter("attr["+val.get("id")+"][to]") != null ? request.getParameter("attr["+val.get("id")+"][to]").trim() : "");
							val.put("value", map);
						}
						else{
							val.put("value", request.getParameter("attr["+val.get("id")+"]") != null ? request.getParameter("attr["+val.get("id")+"]").trim() : "");
						}
					}
					else{
						val.put("value", request.getParameter("attr["+val.get("id")+"]") != null ? request.getParameter("attr["+val.get("id")+"]").trim() : "");
					}
				}
        		if(scDs != 0){
        			request.setAttribute("scck", "checked");
        		}
        		else{
        			request.setAttribute("scck", "");
        		}
        		request.setAttribute("advVal", advValue);
        		request.setAttribute("goodsTypeSelected", goodsType);
            	request.setAttribute("goodsTypeList", attributes.get("cate"));
            	request.setAttribute("goodsAttributes", attributes.get("attr"));
            	action = "form";
            	request.setAttribute("catList", LibCommon.selectCatList(null, (String)advValue.get("category"), 2, false, getDefaultManager()));
            	request.setAttribute("brandList", LibCommon.getBrandList(getDefaultManager()));
            	request.setAttribute("useStorage", 1);
            	
            	
            	action = "form";
            	
        	}
        	
        	/* 初始化搜索条件 */
        	        	
        	if(!StringUtil.isEmpty(keywords)){
        		cond = new Condition();
        		cond.setField(IGoods.GOODS_NAME);
        		cond.setOperator(Condition.EQUALS);
        		cond.setValue(keywords);
        		criteria.addCondition(cond);        		
        	}
        	
        	if(!category.equals("0")){
        		cond = new Condition();
        		cond.setField(IGoods.CAT_ID);
        		cond.setOperator(Condition.EQUALS);
        		cond.setValue(category);
        		criteria.addCondition(cond);
        	}
        	if(!brand.equals("0")){
        		cond = new Condition();
        		cond.setField(IGoods.BRAND_ID);
        		cond.setOperator(Condition.EQUALS);
        		cond.setValue(brand);
        		criteria.addCondition(cond);
        	}
        	if(outstock != 0){
        		cond = new Condition();
        		cond.setField(IGoods.GOODS_NUMBER);
        		cond.setOperator(Condition.GREATERTHAN);
        		cond.setValue("0");
        		criteria.addCondition(cond);
        	}
        	if(maxPrice != 0){
        		cond = new Condition();
        		cond.setField(IGoods.SHOP_PRICE);
        		cond.setOperator(Condition.LESSTHAN);
        		cond.setValue(maxPrice+"");
        		criteria.addCondition(cond);
        	}
        	if(minPrice != 0){
        		cond = new Condition();
        		cond.setField(IGoods.SHOP_PRICE);
        		cond.setOperator(Condition.GREATERTHAN);
        		cond.setValue(minPrice+"");
        		criteria.addCondition(cond);
        	}
        	String display = (String)request.getParameter("display");
    		if(display == null){
    			display = getCachedShopConfig().getString(CFG_KEY_SHOW_ORDER_TYPE);
    		}
    		getSession().setAttribute("displaySearch", display);
    		
    		String sort = (String)request.getParameter("sort");  //'goods_id', 'shop_price', 'last_update'
    		String order = (String)request.getParameter("order"); // ASC DESC
    		if(sort == null){
    			sort = getCachedShopConfig().getString(CFG_KEY_SORT_ORDER_METHOD);
    		}
    		if(order == null){
    			order = getCachedShopConfig().getString(CFG_KEY_SORT_ORDER_TYPE);
    		}
    		
    		String sPage = (String)request.getParameter("page");
    		int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
    		
    		String sSize = getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_PAGE_SIZE);
    		int size = (sSize!=null && Integer.valueOf(sSize)>0) ? Integer.valueOf(sSize) : 10; 
    		
    		String intromode = request.getParameter("intro") != null ? request.getParameter("intro").trim() : "";
    		String urHere = "";
    		
    		if(!StringUtil.isEmpty(intromode)){
    			if(intromode.equals("best")){
    				cond = new Condition();
            		cond.setField(IGoods.IS_BEST);
            		cond.setOperator(Condition.EQUALS);
            		cond.setValue("true");
            		criteria.addCondition(cond);
            		urHere = Lang.getInstance().getString("bestGoods");
    			}
    			else if(intromode.equals("new")){
    				cond = new Condition();
            		cond.setField(IGoods.IS_NEW);
            		cond.setOperator(Condition.EQUALS);
            		cond.setValue("true");
            		criteria.addCondition(cond);
            		urHere = Lang.getInstance().getString("newGoods");
    			}
    			else if(intromode.equals("hot")){
    				cond = new Condition();
            		cond.setField(IGoods.IS_HOT);
            		cond.setOperator(Condition.EQUALS);
            		cond.setValue("true");
            		criteria.addCondition(cond);
            		urHere = Lang.getInstance().getString("hotGoods");
    			}
    			else if(intromode.equals("promotion")){
    				cond = new Condition();
            		cond.setField(IGoods.PROMOTE_PRICE);
            		cond.setOperator(Condition.GREATERTHAN);
            		cond.setValue("0");
            		criteria.addCondition(cond);
            		
            		cond = new Condition();
            		cond.setField(IGoods.PROMOTE_START_DATE);
            		cond.setOperator(Condition.LESSTHAN);
            		cond.setValue((new Date()).toString());
            		criteria.addCondition(cond);
            		urHere = Lang.getInstance().getString("promotionGoods");
    			}
    			else{
    				intromode = "";
    			}
    		}
    		else{
    			intromode = "";
    		}
    		
    		if(StringUtil.isEmpty(urHere)){
    			urHere = Lang.getInstance().getString("searchGoods");
    		}
    		
    		/*------------------------------------------------------ */
    	    //-- 属性检索
    	    /*------------------------------------------------------ */
    		Criteria attrCriteria = new Criteria();
    		Set<Goods> attrResGoods = new HashSet();
    	    int attrNum = 0;
    	    Map attrArg = new HashMap();
    	    if(!StringUtil.isEmpty(request.getParameter("attr"))){
    	    	for (Map<String,Object> val : (List<Map<String,Object>>)attributes.get("attr")) {
					attrNum++;    	    		
    	    		if(!StringUtil.isEmpty(request.getParameter("attr["+val.get("id")+"]"))){
						if(!StringUtil.isEmpty(request.getParameter("attr["+val.get("id")+"][from]"))){
							cond = new Condition();
							cond.setField(IGoodsAttr.ATTR_VALUE);
							cond.setOperator(Condition.GREATERTHAN);
							cond.setValue(request.getParameter("attr["+val.get("id")+"][from]").trim());
							attrCriteria.addCondition(cond);
							attrArg.put("attr["+val.get("id")+"][from]", request.getParameter("attr["+val.get("id")+"][from]").trim());
						}
						if(!StringUtil.isEmpty(request.getParameter("attr["+val.get("id")+"][to]"))){
							cond = new Condition();
							cond.setField(IGoodsAttr.ATTR_VALUE);
							cond.setOperator(Condition.LESSTHAN);
							cond.setValue(request.getParameter("attr["+val.get("id")+"][to]").trim());
							attrCriteria.addCondition(cond);
							attrArg.put("attr["+val.get("id")+"][to]", request.getParameter("attr["+val.get("id")+"][to]").trim());
						}
						if(StringUtil.isEmpty(request.getParameter("attr["+val.get("id")+"][from]")) && StringUtil.isEmpty(request.getParameter("attr["+val.get("id")+"][to]"))){
							cond = new Condition();
							cond.setField("attr["+val.get("id")+"]");
							cond.setOperator(Condition.EQUALS);
							cond.setValue(request.getParameter("attr["+val.get("id")+"]").trim());
							attrCriteria.addCondition(cond);
							attrArg.put("attr["+val.get("id")+"]", request.getParameter("attr["+val.get("id")+"]").trim());
						}
					}
				}
    	    	/* 如果检索条件都是无效的，就不用检索 */
    	    	if(attrNum > 0 ){
    	    		List<GoodsAttr> attrRes = (List<GoodsAttr>)getDefaultManager().getList(ModelNames.GOODSATTR, attrCriteria);
    	    		if(attrRes.size() > 0 ){
    	    			for (GoodsAttr goodsAttr : attrRes) {
							attrResGoods.add(goodsAttr.getGoods());
						}
    	    		}
    	    	}
    	    }
    	    criteria.addCondition( new Condition(IGoods.IS_ON_SALE,Condition.EQUALS,"true"));
    	    //TODO set  is alone sale true
    		criteria.addCondition( new Condition(IGoods.IS_ALONE_SALE,Condition.EQUALS,"false"));
    		criteria.addCondition( new Condition(IGoods.IS_DELETE,Condition.EQUALS,"0"));
    	    List<Goods> resGoods = (List<Goods>)getDefaultManager().getList(ModelNames.GOODS, criteria );
    		List<GoodsWrapper> result = null;
    	    if(attrNum > 0 ){
    	    	result = new ArrayList<GoodsWrapper>();
    			for (Goods goods2 : resGoods) {
    				if(attrResGoods.contains(goods2)){
    					result.add(new GoodsWrapper(goods2));
    				}
				}
    		}
    	    else{
    	    	result = WrapperUtil.wrap(resGoods, GoodsWrapper.class);
    	    }
    	    
    	    int maxPage = result.size() > 0 ? (int)Math.ceil(result.size() / (double)size) : 1;
    		if(page > maxPage ){
    			page = maxPage;
    		}
    		request.setAttribute("goodsList", result);
    		request.setAttribute("category", category);
    		request.setAttribute("keywords", keywords);
    		request.setAttribute("brand", brand);
    		request.setAttribute("minPrice", minPrice);
    		request.setAttribute("outstock", outstock);
    		
    		
    		Map<String,Object> search = new HashMap<String,Object>();
    		search.put("keywords", keywords);
    		search.put("category", category);
    		search.put("brand", brand);
    		search.put("sort", sort);
    		search.put("order", order);
    		search.put("minPrice", minPrice);
    		search.put("maxPrice", maxPrice);
    		search.put("action", StringUtil.isEmpty(action)? "":action);
    		search.put("intro",intromode);
    		search.put("goodsType", goodsType);
    		search.put("scDs", scDs);
    		search.put("outstock", outstock);
    		
    		search.putAll(attrArg);
    		
    		Pager pager = LibMain.getPager("search.action", search, result.size(), page, size, getCachedShopConfig());
    		pager.setDisplay(display);
    		
    		request.setAttribute("pager", pager);
    		LibMain.assignUrHere(request, "", urHere);
    		request.setAttribute("intromode", intromode);
    		
        }

        return  Action.SUCCESS;
    }
   
    public Map<String, String> getUrhereIntro() {
    	Map<String, String> info = new HashMap<String, String>();
    	
    	
    	
    	return info;
    }
    
    public Map<String,Object> getSearchableAttributes(Long catId){
    	Map<String,Object> attributes = new HashMap<String,Object>();
    	Criteria criteria = new Criteria();
    	
        Condition cond = new Condition();
        cond.setField(IGoodsType.ENABLED);
        cond.setOperator(Condition.EQUALS);
        //TODO
        cond.setValue("false");
        criteria.addCondition(cond); 
        
    	List<GoodsType> goodsTypes = (List<GoodsType>)getDefaultManager().getList(ModelNames.GOODSTYPE,criteria);

    		Map<Long,Object> cate = new HashMap<Long,Object>();
    		for (GoodsType goodsType : goodsTypes) {
				for (Attribute attr : goodsType.getAttributes()) {
					if(attr.getAttrIndex()<= 0){
						continue;
					}
					cate.put(goodsType.getLongId(), goodsType.getCatName());
				}
    		}
    		attributes.put("cate", cate);
    		
    		if(cate.size() == 0 ){
    			attributes.put("attr", new ArrayList());
    			return attributes;
    		}
    		
    		cond = new Condition();
    		if(catId > 0){
				cond.setField(IGoodsType.LONG_ID);
				cond.setOperator(Condition.EQUALS);
    			cond.setValue(catId.toString());
    		}				
    		else{
    			cond.setField(IGoodsType.PK_ID);
    			cond.setOperator(Condition.EQUALS);
    			cond.setValue(cate.keySet().toArray()[0].toString());
    		}
    		criteria.addCondition(cond);
    		goodsTypes = (List<GoodsType>)getDefaultManager().getList(ModelNames.GOODSTYPE,criteria);
    		List<Map> attrList = new ArrayList();
			for (GoodsType goodsType : goodsTypes) {
				for (Attribute attr : goodsType.getAttributes()) {
					if(attr.getAttrIndex() == 1 && attr.getAttrInputType() == 1){
						String rowValues = attr.getAttrValues().replaceAll("\r", "");
						String[] options = rowValues.split("\n");
						Map<String,String> attrValues = new HashMap<String,String>();
						for (int i = 0; i < options.length; i++) {
							attrValues.put(options[i], options[i]);
						}
						Map<String,Object> attrArray = new HashMap<String,Object>();
						attrArray.put("id", attr.getPkId());
						attrArray.put("attr", attr.getAttrName());
						attrArray.put("options", attrValues);
						attrArray.put("type", 3);
						attrList.add(attrArray);
					}
					else{
						Map<String,Object> attrArray = new HashMap<String,Object>();
						attrArray.put("id", attr.getPkId());
						attrArray.put("attr", attr.getAttrName());
						attrArray.put("type", attr.getAttrIndex());
						attrList.add(attrArray);
					}
				}
			}
			attributes.put("attr", attrList);
    	return attributes;
    }
    
    
    
    
    
//    private String act = null;
//    
//    public String getAct() {
//		return act;
//	}
//
//	public void setAct(String act) {
//		this.act = act;
//	}

	int page = 0;
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
   
	int pageSize = 0;
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    String brand;
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getBrand() {
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
    public String toString() {
		// this is a tricky hacking
		// in freemarker the action variable points to the Action class
		if(action == null){
			action = "";
		}
		return WebFormatUtils.phpVarFromat(action);
	}

	
}
