package com.jcommerce.web.front.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.datanucleus.util.StringUtils;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.to.WrapperUtil;

public class CategoryAction extends BaseAction {
	
	public void debug(String s) {
		System.out.println(" in [CategoryAction]: "+s );
	}
	
	@Override
	public String execute() throws Exception {
		try {
			debug("in execute");
			super.execute();
			HttpServletRequest request = getRequest();
			includeCart(request);
			includeCategoryTree(request);

			includeFilterAttr();
			includePriceGrade();
			includeHistory(request);

			// TODO maybe this only include goods belong to the category?
			includeRecommendBest(request);

			includeGoodsList();

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
        
        Long catLongId = Long.valueOf(_id);
        debug("in [includeGoodsList]: catLongId="+catLongId);
        

		String sPage = (String)request.getParameter("page");
		int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
		
		String sSize = (String)ShopConfigWrapper.getDefaultConfig().get("pageSize");
		int size = (sSize!=null && Integer.valueOf(sSize)>0) ? Integer.valueOf(sSize) : 10; 
		
		String brandId = (String)request.getParameter("brand");
		if(brandId == null) {
			brandId = "";
		}

		
		String sort = "goods_id";  //'goods_id', 'shop_price', 'last_update'
		String order = "ASC"; // ASC DESC
		String display = DISPLAY_LIST;
		
		
		Category cat = getCatInfo(catLongId);
		String catId = cat.getPkId();
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
		
		LibMain.assignPager("category", catLongId, count, size, sort, order, page, "", brandId, 0, 0, display, "", "", null, getRequest());
		

	}
	
	private Category getCatInfo(Long catLongId) {
		return (Category)getDefaultManager().get(ModelNames.CATEGORY, catLongId);
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
		
		return manager.getCount(ModelNames.GOODS, null);
	}
	
	private List<Goods> categoryGetGoods(List<String> children, String brandId, int priceMin, int priceMax,
			int size, int page, String sort, String order) {
		
		// TODO categoryGetGoods
		IDefaultManager manager = getDefaultManager();
        int firstRow = (page-1)*size;
        int maxRow = size;
		return (List<Goods>)manager.getList(ModelNames.GOODS, null, firstRow, maxRow);
		
	}
}
