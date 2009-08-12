package com.jcommerce.core.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.DataStoreUtils;

public class LiveDSHelper extends BaseDAOTestCase {
	
	@Override
    public String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/war";
//    	return "D:/JCommerce/JCommerceGae/testdatastore";
    }
	
	@Override
	public boolean needCleanOnStartup() {
    	return false;
    }
	
	public void testInitialDS() {
		System.out.println("start of testInitialDS...");
		try {
			clearDS();
			
			IDefaultManager manager = getDefaultManager();
			InputStream in = LiveDSHelper.class.getClassLoader().getResourceAsStream("com/jcommerce/core/test/mydata.txt");
			
//			BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
//			while(true) {
//				String newline = reader.readLine();
//				if(newline==null) {
//					break;
//				}
//				System.out.println("newline: "+newline);
//			}
			DataStoreUtils.importDS2(in, manager);
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		System.out.println("end of testInitialDS...");
		
	}
	
	public void testExportDS() {
		System.out.println("start of testExportDS...");
		try {
			IDefaultManager manager = getDefaultManager();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			DataStoreUtils.exportDS2(out, GoodsType.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Attribute.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Brand.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Category.class.getName(), manager);
			out.flush();
			String exported = new String(out.toByteArray(), ENCODING);

			System.out.println("exported:\r\n " + exported);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		System.out.println("end of testExportDS...");
	}
	
	public void testQueryGoods() {
		System.out.println("start of testQueryGoods...");
		try {
			IDefaultManager manager = getDefaultManager();

			List<Goods> list = (List<Goods>)manager.getList(Goods.class.getName(), null);
			for(Goods goods:list) {
				System.out.println("name="+goods.getGoodsName()+", longid="+goods.getLongId());
				
				
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		System.out.println("end of testQueryGoods...");
		
		
	}

}
