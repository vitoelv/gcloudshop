package com.jcommerce.core.test.web;

import java.util.List;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.web.front.action.LibGoods;
import com.jcommerce.web.to.CategoryWrapper;

public class TestLibGoods extends BaseDAOTestCase {
	@Override
    public String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/war";
//    	return "D:/JCommerce/JCommerceGae/testdatastore";
    }
	
	@Override
	public boolean needCleanOnStartup() {
    	return false;
    }
	
	public void testGetCategoriesTree() {
		try {		
			IDefaultManager manager = getDefaultManager();
			
			List<CategoryWrapper> res = LibGoods.getCategoriesTree(null, manager);
			
			for(CategoryWrapper cw: res) {
				System.out.println("parent: "+cw.get(ICategory.CAT_NAME));
				for(CategoryWrapper children : cw.getChildren()) {
					System.out.println("children: "+children.get(ICategory.CAT_NAME));
				}
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void test2() {
		try {		
			IDefaultManager manager = getDefaultManager();
			
			List<Category> res = manager.getList(ModelNames.CATEGORY, null);
			
			for(Category cat: res) {
				CategoryWrapper cw = new CategoryWrapper(cat);
				System.out.println("pkId: "+cw.getPkId2());

			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
