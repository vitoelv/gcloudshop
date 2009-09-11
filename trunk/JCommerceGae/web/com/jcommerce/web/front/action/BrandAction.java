package com.jcommerce.web.front.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.URLConstants;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.BrandWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.to.WrapperUtil;

public class BrandAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [BrandAction]: "+s );
	}
	

	
	public void includeGoodsList() {
		HttpServletRequest request = getRequest();
        String brandId = request.getParameter("id");
        request.setAttribute("brandId", brandId);
        

		String sPage = (String)request.getParameter("page");
		int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
		
		String sSize = (String)ShopConfigWrapper.getDefaultConfig().get("pageSize");
		int size = (sSize!=null && Integer.valueOf(sSize)>0) ? Integer.valueOf(sSize) : 10; 
		
		String cat = (String)request.getParameter("cat");
		int cate = (cat!=null && Integer.valueOf(cat)>0) ? Integer.valueOf(cat) : 0;
		request.setAttribute("category", cate);
		
		String sort = "goods_id";  //'goods_id', 'shop_price', 'last_update'
		String order = "ASC"; // ASC DESC
		
		// TODO
		int recordCount = goodsCountByBrand(brandId, cate);
		
		List<Goods> goods = brandGetGoods(brandId, cate, size, page, sort, order);
		List<GoodsWrapper> gws = WrapperUtil.wrap(goods, GoodsWrapper.class);
		request.setAttribute("goodsList", gws);
		
		List<Category> relatedCate = brandRelatedCat();
		request.setAttribute("brandCatList", relatedCate); // 相关分类

		assignPager("brand", cate, recordCount, size, sort, order, page, "", brandId, 0, 0, DISPLAY_LIST, "", "", null);
		
		request.setAttribute("priceMin", 0);
		request.setAttribute("priceMax", 0);
		request.setAttribute("filterAttr", "");
	}
	
	/**
	 * 获得指定的品牌下的商品总数
	 *
	 * @access  private
	 * @param   integer     $brand_id
	 * @param   integer     $cate
	 * @return  integer
	 */
	private int goodsCountByBrand(String brandId, int cat) {
        Criteria c1 = new Criteria();
        Condition cond1 = new Condition();
        cond1.setField(IGoods.BRAND_ID);
        cond1.setOperator(Condition.EQUALS);
        cond1.setValue(brandId);
        c1.addCondition(cond1);
        
        
        int count = getDefaultManager().getCount(ModelNames.GOODS, c1);
		return count;
	}
	
	// TODO assignPager -- refactor & support of sort/order
    protected void assignPager(
    		String app, int cat, int recordCount, int size, String sort, String order, int page, 
    		String keyword, String brandId, double priceMin, double priceMax,
    		String displayType, String filterAttr, String urlFormat, Map<String, Object> schArray) {
    	HttpServletRequest request = getRequest();
    	
    	int pageCount = recordCount > 0 ? (recordCount / size)+1 : 1;
    	
        Pager pager = new Pager();
        pager.getSearch().put("category", cat);
        pager.getSearch().put("keywords", keyword);
        pager.getSearch().put("sort", sort);
        pager.getSearch().put("order", order);
        pager.getSearch().put("cat", cat);
        pager.getSearch().put("brand", brandId);
        pager.getSearch().put("price_min", 0);
        pager.getSearch().put("price_max", 0);
        pager.getSearch().put("filter_attr", "");
        pager.getSearch().put("display", DISPLAY_LIST);
        pager.setPage(page);
        pager.setSize(size);
        pager.setSort(sort);
        pager.setOrder(order);
        pager.setRecordCount(recordCount);
        pager.setPageCount(pageCount);
        pager.setDisplay(displayType);
        request.setAttribute("pager", pager);
        
        Map<String, Object> uriArgs = new HashMap<String, Object>();
        if(IWebConstants.APP_BRAND.equals(app)) {
        	uriArgs.put("cid", cat);
        	uriArgs.put("bid", brandId);
        	uriArgs.put("sort", sort);
        	uriArgs.put("order", order);
        	uriArgs.put("display", displayType);
        }        
        
        int pagePrev  = (page > 1) ? page - 1 : 1;
        int pageNext  = (page < pageCount) ? page + 1 : pageCount;
        pager.setPageFirst(buildUri(app, uriArgs, "", 1, 0));
        pager.setPagePrev(buildUri(app, uriArgs, "", pagePrev, 0));
        pager.setPageNext(buildUri(app, uriArgs, "", pageNext, 0));
        pager.setPageLast(buildUri(app, uriArgs, "", pageCount, 0));
        
        Map<Integer, Integer> array = pager.getArray(); 
        for(int i=1;i<=pageCount; i++) {
        	array.put(i, i);
        }
        
        //${smarty.server.PHP_SELF}
        
        Map<String, String> server = new HashMap<String, String>();
        server.put("PHP_SELF", URLConstants.ACTION_BRAND);
        
        Map<String, Object> smarty = new HashMap<String, Object>();
        smarty.put("server", server);
        request.setAttribute("smarty", smarty);
        
    }
    private String buildUri(String app, Map<String, Object> uriArgs, String append, int page, int size){
    	StringBuffer buf = new StringBuffer();
    	if(IWebConstants.APP_BRAND.equals(app)) {
    		buf.append("brand.action?id=").append(uriArgs.get("bid"));
    		if(uriArgs.get("cid")!=null) {
    			buf.append("&amp;cat=").append(uriArgs.get("cid"));
    		}
   			buf.append("&amp;page=").append(page);

   			if(uriArgs.get("sort")!=null) {
    			buf.append("&amp;sort=").append(uriArgs.get("sort"));
    		}
    		if(uriArgs.get("order")!=null) {
    			buf.append("&amp;order=").append(uriArgs.get("order"));
    		}    		
    	}

    	return buf.toString();
    }
	private List<Goods> brandGetGoods(String brandId, int cate, int size, int page, String sort, String order) {
        Criteria c1 = new Criteria();
        Condition cond1 = new Condition();
        cond1.setField(IGoods.BRAND_ID);
        cond1.setOperator(Condition.EQUALS);
        cond1.setValue(brandId);
        c1.addCondition(cond1);
        int firstRow = (page-1)*size;
        int maxRow = size;
        List<Goods> list = (List<Goods>)getDefaultManager().getList(ModelNames.GOODS, c1, firstRow, maxRow );
        debug("brandId: "+brandId+", size of goods: "+list.size());
        return list;
	}
	
	private List<Category> brandRelatedCat() {
		// TODO refer to brand.php line333
		return new ArrayList<Category>();
	}
	
	@Override
	public String execute() throws Exception {
		try {
			debug("in execute");
			super.execute();	        
	        HttpServletRequest request = getRequest();
	        includeCart(request);
	        includeCategoryTree(request);
	        includeHistory(request);
	        
	        // TODO maybe this only include goods belong to the brand?
	        includeRecommendBest(request);
	        
	        includeFilterAttr();
	        includePriceGrade();
	        includeGoodsList();
	        
	        String brandId = request.getParameter("id");
	        debug("in [execute]: brandId="+brandId);
	        
	        IDefaultManager manager = getDefaultManager();
	        Brand brand = (Brand)manager.get(ModelNames.BRAND, brandId);
	        BrandWrapper bw = new BrandWrapper(brand);
	        request.setAttribute("brand", bw);
	        
	        
			return SUCCESS;
        
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}