package com.jcommerce.web.front.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.web.to.CategoryWrapper;

public class LibCommon {

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
}
