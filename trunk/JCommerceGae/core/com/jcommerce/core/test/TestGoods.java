package com.jcommerce.core.test;

import java.util.Arrays;
import java.util.Set;

import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.UUIDLongGenerator;

public class TestGoods extends BaseDAOTestCase {
	
	@Override
	public boolean needCleanOnStartup() {
		return true;
	}
	
	public void testAddGoods() {
		IDefaultManager manager = getDefaultManager();
		try {
			clearDS();
			
			Goods goods = new Goods();
			goods.setKeyName("xxx");
			goods.setGoodsName("abc");
			goods.setGoodsNumber(123l);
			
			String id = manager.txadd(goods);
			Long ld1 = goods.getLongId();
			
			System.out.println("id="+id);
			
			
			// verify
			goods = (Goods)manager.get(Goods.class.getName(), id);
			Long ld2 = goods.getLongId();
			System.out.println("ld1="+ld1+", ld2="+ld2);
			String name = goods.getGoodsName();
			System.out.println("name="+name);
			long gn = goods.getGoodsNumber();
			System.out.println("gn="+gn);
			assertTrue(ld1.equals(ld2));
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(" end fo testAddGoods...");
	}
	
	public void testAddGoodsAndCategoryIds() {
		IDefaultManager manager = getDefaultManager();
		try {
			Goods goods = new Goods();
			goods.setGoodsName("abc");
			String[] catIds = new String[] {"xxx", "yyy"};
			
			goods.getCategoryIds().addAll(Arrays.asList(catIds));
			
			String id = manager.txadd(goods);
			
			System.out.println("id="+id);
			
			Goods g = (Goods)manager.get(Goods.class.getName(), id);
			Set set = g.getCategoryIds();
			System.out.println("cats: "+set);
			if(set!=null) {
				System.out.println("size: "+set.size());
			}
			
			g.getCategoryIds().add("zzz");
			manager.txupdate(g);
			
			
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
