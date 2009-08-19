package com.jcommerce.core.test;

import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.service.IDefaultManager;


public class TestMiscDS extends BaseDAOTestCase {
	@Override
    public String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/war";
//    	return "D:/JCommerce/JCommerceGae/testdatastore";
    }
	
	@Override
	public boolean needCleanOnStartup() {
    	return false;
    }
	
	public void testDeleteBrand() {
		IDefaultManager manager = getDefaultManager();
		List<Brand> res = new ArrayList<Brand>();
		manager.getList(res, Brand.class.getName(), null, -1, -1);
		
		for(Brand brand:res) {
			String id = brand.getPkId();
			System.out.println("id="+id);
			Brand b = (Brand)manager.get(Brand.class.getName(), id);
			System.out.println("brand: "+b);
		}
		
	}
}
