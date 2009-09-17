package com.jcommerce.core.test.web;

import java.util.HashMap;
import java.util.Map;

import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.web.front.action.LibCommon;
import com.jcommerce.web.to.CategoryWrapper;

public class TestLibCommon extends BaseDAOTestCase {
	@Override
    public String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/war";
//    	return "D:/JCommerce/JCommerceGae/testdatastore";
    }
	
	@Override
	public boolean needCleanOnStartup() {
    	return false;
    }
	
	public void testCatList() {
		try {		
			IDefaultManager manager = getDefaultManager();
			/*
			 * 1
			 * - 2
			 *   - 3
			 *   - 4
			 * - 5
			 *   - 6
			 *   - 7
			 *     - 8
			 * 9
			 * - 10
			 */
			
			
			Map<String, String> cpMap = new HashMap<String, String>();
			cpMap.put("1", null);
			cpMap.put("2", "1");
			cpMap.put("3", "2");
			cpMap.put("4", "2");
			cpMap.put("5", "1");
			cpMap.put("6", "5");
			cpMap.put("7", "5");
			cpMap.put("8", "7");
			cpMap.put("9", null);
			cpMap.put("10", "9");

			int initialSearchLevel = -1;
			
			int i1 = LibCommon.findParent(initialSearchLevel, "4", "1", cpMap);
			System.out.println("i1="+i1);
			
			i1 = LibCommon.findParent(initialSearchLevel, "8", "1", cpMap);
			System.out.println("i1="+i1);
			
			i1 = LibCommon.findParent(initialSearchLevel, "3", "10", cpMap);
			System.out.println("i1="+i1);
			
			i1 = LibCommon.findParent(initialSearchLevel, "6", "6", cpMap);
			System.out.println("i1="+i1);
			
			i1 = LibCommon.findParent(initialSearchLevel, "6", "5", cpMap);
			System.out.println("i1="+i1);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
}
