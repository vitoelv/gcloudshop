package com.jcommerce.web.front.action;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOW_ORDER_TYPE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SORT_ORDER_METHOD;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SORT_ORDER_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.datanucleus.util.StringUtils;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.service.Order;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibGoods;
import com.jcommerce.web.util.LibMain;
import com.jcommerce.web.util.WebUtils;

public class CategoryAction extends BaseAction {
	
	public void debug(String s) {
		System.out.println(" in [CategoryAction]: "+s );
	}
	@Override
	protected String getSelfURL() {
		return URLConstants.ACTION_CATEGORY;
	}
	@Override
	public String onExecute() throws Exception {
		try {
			debug("in execute");
			HttpServletRequest request = getRequest();
			
			includeCart();
			includeCategoryTree(request);

			includeFilterAttr();
			includePriceGrade();
			includeHistory(request);
			
			//必须在includeRecommendBest之前，否则includeRecommendBest无法获取信息
			includeGoodsList();
			
			// TODO maybe this only include goods belong to the category?
			includeRecommendBest(request);

			

			request.setAttribute("showMarketprice", getCachedShopConfig().getInt(IShopConfigMeta.CFG_KEY_SHOW_MARKETPRICE));
//			String categoryId = request.getParameter("id");
//			debug("in [execute]: categoryId=" + categoryId);
//
//			IDefaultManager manager = getDefaultManager();
//			Category category = (Category) manager.get(ModelNames.CATEGORY, categoryId);
//			CategoryWrapper cw = new CategoryWrapper(category);
//			request.setAttribute("category", cw);

			return SUCCESS;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void includeGoodsList() {
		HttpServletRequest request = getRequest();
        String _id = request.getParameter("id");
        if(StringUtils.isEmpty(_id)) {
        	_id = request.getParameter("category");
        }
        if(StringUtils.isEmpty(_id)) {
        	// sth wrong, maybe goto home
        	throw new RuntimeException("cat id is null");
        }
        
        Long catLongId = WebUtils.tryGetLongId(_id);
        String catId = catLongId == null? _id : null;
        debug("in [includeGoodsList]: catLongId="+catLongId+", catId="+catId);

		String sPage = (String)request.getParameter("page");
		int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
		
		String sSize = (String)getCachedShopConfig().get("pageSize");
	
		int size = (sSize!=null && Integer.valueOf(sSize)>0) ? Integer.valueOf(sSize) : 10; 
		
		String brandId = (String)request.getParameter("brand");
		if(brandId == null) {
			brandId = "";
		}

		
		String sort = (String)request.getParameter("sort");  //'goods_id', 'shop_price', 'last_update'
		String order = (String)request.getParameter("order"); // ASC DESC
		String display = (String)request.getParameter("display");
		if(display == null){
			display = getCachedShopConfig().getString(CFG_KEY_SHOW_ORDER_TYPE);
		}
		if(sort == null){
			sort = getCachedShopConfig().getString(CFG_KEY_SORT_ORDER_METHOD);
		}
		if(order == null){
			order = getCachedShopConfig().getString(CFG_KEY_SORT_ORDER_TYPE);
		}
		

		Category cat = null;
		if(catLongId!=null) {
			cat = getCatInfo(catLongId);
		}else {
			cat = getCatInfo(catId);
		}
		if(cat==null) {
			throw new RuntimeException("cannot find category");
		}
		catId = cat.getPkId();
		catLongId = cat.getLongId();
		

		
		CategoryWrapper cw = new CategoryWrapper(cat);
		String brandName = "";
		if(StringUtils.isEmpty(brandId)) {
			// TODO brandName 
			
		}
		if(cat.getGrade()==0 && cat.getParentId()!=null) {
			cw.put(ICategory.GRADE, getParentGrade(catId));
		}
		
		if((Long)cw.get(ICategory.GRADE)>1) {
			// TODO pricegrade
		}
		
		if(cat.getFilterAttr()>0) {
			// TODO filterAttr
		}
		
        List<String> children = LibCommon.catList(catId, -1, false, getDefaultManager());
        

		int priceMin = 0;
		int priceMax = 0;
		request.setAttribute("categories", LibGoods.getCategoriesTree(catId, getDefaultManager()));
        request.setAttribute("category", catLongId);
		request.setAttribute("brandId", brandId);
		request.setAttribute("priceMin", priceMin);
		request.setAttribute("priceMax", priceMax);
		request.setAttribute("filterAttr", "");
		
		int count = getCategoryGoodsCount(children, brandId, priceMin, priceMax);
		int maxPage = count>0? (count/size)+1 : 1;
		if(page > maxPage) {
			page = maxPage;
		}
		
		List<Goods> goods = categoryGetGoods(children, brandId, priceMin, priceMax, size, page, sort, order);
		List<GoodsWrapper> gws = WrapperUtil.wrap(goods, GoodsWrapper.class);
		request.setAttribute("goodsList", gws);
		
		LibMain.assignPager("category", catLongId, count, size, sort, order, page,
				"", brandId, 0, 0, display, "", "", null, getRequest(), getCachedShopConfig());
		
		
		LibMain.assignUrHere(request, catId, "");
		

	}
	
	private Category getCatInfo(Long catLongId) {
		return (Category)getDefaultManager().get(ModelNames.CATEGORY, catLongId);
	}
	private Category getCatInfo(String catId) {
		return (Category)getDefaultManager().get(ModelNames.CATEGORY, catId);
	}
	
	
	private Long getParentGrade(String catId) {
		Map<String, String> parentArr = new HashMap<String, String>();
		Map<String, Long> gradeArr = new HashMap<String, Long>();
		List<Category> allCats = getDefaultManager().getList(ModelNames.CATEGORY, null);
		for(Category cat: allCats) {
			parentArr.put(cat.getPkId(), cat.getParentId());
			gradeArr.put(cat.getPkId(), cat.getGrade());
		}
		
		String cid = catId;
		while(parentArr.get(cid)!=null && gradeArr.get(cid)==0) {
			cid = parentArr.get(cid);
		}
		
		return gradeArr.get(cid);
		
	}
	
	private int getCategoryGoodsCount(List<String> children, String brandId, int priceMin, int priceMax) {
		// TODO getCategoryGoodsCount
		IDefaultManager manager = getDefaultManager();
        List<Goods> goods = new ArrayList<Goods>();
        
        Criteria criteria = new Criteria();
        
        Condition cond = new Condition();
        cond.setField(IGoods.CAT_ID);
        cond.setOperator(Condition.EQUALS);
        criteria.addCondition(cond);
        
        if(!brandId.equals("")){
        	Condition cond2 = new Condition();
        	cond2.setField(IGoods.BRAND_ID);
        	cond2.setOperator(Condition.EQUALS);
        	cond2.setValue(brandId);
        	criteria.addCondition(cond2);
        }
        
        if(priceMin > 0){
        	Condition cond3 = new Condition();
        	cond3.setField(IGoods.SHOP_PRICE);
        	cond3.setOperator(Condition.GREATERTHAN);
        	cond3.setValue(priceMin+"");
        	criteria.addCondition(cond3);
        }
        
        if(priceMax > 0){
        	Condition cond4 = new Condition();
        	cond4.setField(IGoods.SHOP_PRICE);
        	cond4.setOperator(Condition.LESSTHAN);
        	cond4.setValue(priceMax+"");
        	criteria.addCondition(cond4);
        }     
        
        for (String cid : children) {
        	cond.setValue(cid);  
        	goods.addAll((List<Goods>)manager.getList(ModelNames.GOODS, criteria));
		}
        
        LibCommon.removeDuplicateWithOrder(goods);
		
		return goods.size();
	}
	
	private List<Goods> categoryGetGoods(List<String> children, String brandId, int priceMin, int priceMax,
			int size, int page, String sort, String order) {
		
		// TODO categoryGetGoods
		IDefaultManager manager = getDefaultManager();
        int firstRow = (page-1)*size;
        int maxRow = size;
        List<Goods> goods = new ArrayList<Goods>();
        
        Criteria criteria = new Criteria();
        
        Condition cond = new Condition();
        cond.setField(IGoods.CAT_ID);
        cond.setOperator(Condition.EQUALS);
        criteria.addCondition(cond);

        if(!brandId.equals("")){
        	Condition cond2 = new Condition();
        	cond2.setField(IGoods.BRAND_ID);
        	cond2.setOperator(Condition.EQUALS);
        	cond2.setValue(brandId);
        	criteria.addCondition(cond2);
        }
        
        if(priceMin > 0){
        	Condition cond3 = new Condition();
        	cond3.setField(IGoods.SHOP_PRICE);
        	cond3.setOperator(Condition.GREATERTHAN);
        	cond3.setValue(priceMin+"");
        	criteria.addCondition(cond3);
        }
        
        if(priceMax > 0){
        	Condition cond4 = new Condition();
        	cond4.setField(IGoods.SHOP_PRICE);
        	cond4.setOperator(Condition.LESSTHAN);
        	cond4.setValue(priceMax+"");
        	criteria.addCondition(cond4);
        }  

        Order order1 = new Order();
        order1.setField(IGoods.PK_ID);
        if(order.equals("ASC")){
        	order1.setAscend(Order.ASCEND);
        }
        else{
        	order1.setAscend(Order.DESCEND);
        }
        //criteria.addOrder(order1);
                
        for (String cid : children) {
        	cond.setValue(cid);
        	goods.addAll((List<Goods>)manager.getList(ModelNames.GOODS, criteria,firstRow,maxRow));
		}
        
        LibCommon.removeDuplicateWithOrder(goods);
        if(goods.size()<maxRow){
        	maxRow = goods.size();
        }
		return goods.subList(firstRow, maxRow);
		
	}
	@Override
	public void includeRecommendBest(HttpServletRequest request) {
    	setCatRecSign(request); 	
        request.setAttribute("bestGoods", getBestSoldGoods(getCatInfo((Long)request.getAttribute("category")).getPkId(),request));
    }
	
	private List getBestSoldGoods(String catId ,HttpServletRequest request) {
		List<GoodsWrapper> goodsList = (List<GoodsWrapper>)request.getAttribute("goodsList");
		List<GoodsWrapper> list = new ArrayList<GoodsWrapper>();
		for (GoodsWrapper goodsWrapper : goodsList) {
			if((Boolean)goodsWrapper.get("isBest")){
				list.add(goodsWrapper);
			}
		}
		if(list.size()>0){
			return list;
		}
		else{
			return null;
		}
		         
    }
	
}
