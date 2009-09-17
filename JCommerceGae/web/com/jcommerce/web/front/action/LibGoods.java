package com.jcommerce.web.front.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.web.to.CategoryWrapper;

public class LibGoods {

	/**
	 * 获得指定分类同级的所有分类以及该分类下的子分类
	 * 
	 * @access public
	 * @param integer
	 *            $cat_id 分类编号
	 * @return array
	 */

	public static List<CategoryWrapper> getCategoriesTree(String categoryId,
			IDefaultManager manager) {

		String parentId = null;
		if (categoryId != null) {

		} else {

		}

		// TODO tree query
		// we have to do it in memory
		// index on parentId??

		List<Category> allCats = (List<Category>) manager.getList(ModelNames.CATEGORY, null);

		
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

		
		
		
		List<CategoryWrapper> level1 = pcMap.get(parentId);

		for (CategoryWrapper cw : level1) {
			List<CategoryWrapper> level2 = pcMap.get(cw.getPkId());
			if (level2 != null) {
				cw.getChildren().addAll(level2);
			}
		}

		return level1;
	}

}
