package com.jcommerce.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.web.front.action.IWebConstants;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.RegionWrapper;
import com.jcommerce.web.to.WrapperUtil;

public class LibCommon {

	
	public static String buildUri(String app, Map<String, Object> uriArgs, String append) {
		return buildUri(app, uriArgs, append, 0, 0);
	}
	/**
	 * 重写 URL 地址
	 *
	 * @access  public
	 * @param   string  $app    执行程序
	 * @param   array   $params 参数数组
	 * @param   string  $append 附加字串
	 * @param   integer $page   页数
	 * @return  void
	 */
    public static String buildUri(String app, Map<String, Object> uriArgs, String append, int page, int size){
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
    	else if(IWebConstants.APP_CATEGORY.equals(app)) {
    		buf.append("category.action?id=").append(uriArgs.get("cid"));
    		if(uriArgs.get("bid")!=null) {
    			buf.append("&amp;brand=").append(uriArgs.get("bid"));
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
	/**
	 * 获得指定分类下的子分类的数组
	 * 
	 * @access public
	 * @param int $cat_id 分类的ID
	 * @param int $selected 当前选中分类的ID
	 * @param boolean $re_type 返回的类型: 值为真时返回下拉列表,否则返回数组
	 * @param int $level 限定返回的级数。为0时返回所有级数
	 * @param int $is_show_all 如果为true显示所有分类，如果为false隐藏不可见分类。
	 * @return mix
	 */
	public static List<String> catList(String catId, int level,
			boolean isShowAll, IDefaultManager manager) {

		List<String> res = new ArrayList<String>();

		List<Category> allCats = (List<Category>) manager.getList(
				ModelNames.CATEGORY, null);
		if (catId == null) {
			for (Category cat : allCats) {
				res.add(cat.getPkId());
			}
			return res;
		}

		// child->parent
		Map<String, String> cpMap = new HashMap<String, String>();
		// parent->children
		Map<String, List<CategoryWrapper>> pcMap = new HashMap<String, List<CategoryWrapper>>();
		// 1st round loop
		for (Category cat : allCats) {
			cpMap.put(cat.getPkId(), cat.getParentId());

			List<CategoryWrapper> children = pcMap.get(cat.getParentId());
			if (children == null) {
				children = new ArrayList<CategoryWrapper>();
				pcMap.put(cat.getParentId(), children);
			}
			children.add(new CategoryWrapper(cat));
		}

		// 2st round loop
		for (String cId : cpMap.keySet()) {
			int foundLevel = findParent(-1, cId, catId, cpMap);
			if (foundLevel < 0) {
				// didn't find catId as parent

				// } else if(foundLevel > level){
				// level deeper than required

			} else {
				res.add(cId);
			}
		}

		return res;
	}

	public static int findParent(int searchLevel, String cId, String catId,
			Map<String, String> cpMap) {
		int foundLevel = -1;
		if (cId.equals(catId)) {
			// found
			foundLevel = searchLevel + 1;
			return foundLevel;
		}
		String pid = cpMap.get(cId);
		if (pid == null) {
			// didn't find
			return foundLevel;
		} else {
			return findParent(searchLevel + 1, pid, catId, cpMap);
		}

	}
	
	public static List<RegionWrapper> getRegion(Long regionType, String parentId, IDefaultManager manager) {
		Criteria c = new Criteria();
		c.addCondition(new Condition(IRegion.REGION_TYPE, Condition.EQUALS, regionType.toString()));
		if(parentId != null){
			c.addCondition(new Condition(IRegion.PARENT_ID, Condition.EQUALS, parentId));
		}
		List<Region> list = (List<Region>)manager.getList(ModelNames.REGION, c);
		return WrapperUtil.wrap(list, RegionWrapper.class);
		
		// TODO region list
//		if(regionType == IRegion.TYPE_COUNTRY) {
//			return getCountryList();
//		}
//		else if(regionType == IRegion.TYPE_PROVINCE) {
//			return getProvinceList();
//		}
//		else if(regionType == IRegion.TYPE_CITY) {
//			return getCityList();
//		}
//		else {
//			return getCountryList();
//		}
		
	}
    private static List<RegionWrapper> getCountryList() {
    	Region r = new Region();
    	r.setPkId("1");
    	r.setRegionName("中国");
    	r.setRegionType(IRegion.TYPE_COUNTRY);
    	RegionWrapper rw = new RegionWrapper(r);
    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
    	res.add(rw);
    	return res;
    }
    private static List<RegionWrapper> getProvinceList() {
    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
    	Region r = new Region();
    	r.setParentId("1");
    	r.setPkId("2");
    	r.setRegionName("北京");
    	r.setRegionType(IRegion.TYPE_PROVINCE);
    	RegionWrapper rw = new RegionWrapper(r);
    	res.add(rw);
    	
    	r = new Region();
    	r.setParentId("1");
    	r.setPkId("3");
    	r.setRegionName("广东");
    	r.setRegionType(IRegion.TYPE_PROVINCE);
    	rw = new RegionWrapper(r);
    	res.add(rw);
    	return res;
    }

    private static List<RegionWrapper> getCityList() {
    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
    	Region r = new Region();
    	r.setParentId("3");
    	r.setPkId("4");
    	r.setRegionName("广州");
    	r.setRegionType(IRegion.TYPE_CITY);
    	RegionWrapper rw = new RegionWrapper(r);
    	res.add(rw);
    	
    	r = new Region();
    	r.setParentId("3");
    	r.setPkId("5");
    	r.setRegionName("深圳");
    	r.setRegionType(IRegion.TYPE_CITY);
    	rw = new RegionWrapper(r);
    	res.add(rw);
    	

    	return res;
    }
    /**
	 * arraylist去重
	 * @param arlList
	 */
	public static void removeDuplicateWithOrder(List arlList) 
	{
	  Set set = new HashSet();
	  List newList = new ArrayList();
	  for (Iterator iter = arlList.iterator(); iter.hasNext(); )
	  {
	      Object element = iter.next();
	      if (set.add(element)){
	    	  newList.add(element);
	      }
	  }
	  arlList.clear();
	  arlList.addAll(newList);
	}
	
	/**
	 * 格式化重量：小于1千克用克表示，否则用千克表示
	 * @param   float   $weight     重量
	 * @return  string  格式化后的重量
	 */
	public static String formatedWeight(double weight){
		Long newWeight = Math.round(weight);
		Lang lang = Lang.getInstance();
		if( newWeight > 0 ){
			if( newWeight < 1 ){
				return newWeight.intValue() * 1000 + lang.getString("gram");
			}
			else{
				return newWeight.intValue() + lang.getString("kilogram");
			}
		}
		else{
			return "0";
		}
	}
	
}
