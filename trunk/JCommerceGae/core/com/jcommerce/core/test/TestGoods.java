package com.jcommerce.core.test;

import java.util.Arrays;
import java.util.Set;

import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.IDefaultManager;

public class TestGoods extends BaseDAOTestCase {
	
	@Override
	public boolean needCleanOnStartup() {
		return true;
	}
	
	public void testAddGoodsAndCategoryIds() {
		IDefaultManager manager = getDefaultManager();
		try {
			Goods goods = new Goods();
			goods.setName("abc");
			String[] catIds = new String[] {"xxx", "yyy"};
			
			goods.getCategoryIds().addAll(Arrays.asList(catIds));
			
			String id = manager.add(goods);
			
			System.out.println("id="+id);
			
			Goods g = (Goods)manager.get(Goods.class.getName(), id);
			Set set = g.getCategoryIds();
			System.out.println("cats: "+set);
			if(set!=null) {
				System.out.println("size: "+set.size());
			}
			
			g.getCategoryIds().add("zzz");
			manager.update(g);
			
			
			g = (Goods)manager.get(Goods.class.getName(), id);
			set = g.getCategoryIds();
			System.out.println("cats: "+set);
			if(set!=null) {
				System.out.println("size: "+set.size());
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	
}
