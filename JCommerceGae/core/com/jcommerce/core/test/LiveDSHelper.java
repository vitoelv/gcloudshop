package com.jcommerce.core.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsGallery;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Session;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.DataStoreUtils;

public class LiveDSHelper extends BaseDAOTestCase {
	
	public static final String LIVEDS_PATH = "com/jcommerce/core/test/liveDSData/";
	public static final String LIVEDS_DATA = "mydata.txt";
	public static final String LIVEDS_FILE = "files";
	
	@Override
    public String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/war";
//    	return "D:/JCommerce/JCommerceGae/testdatastore";
    }
	private boolean isIncremental() {
		// false if want to clear before importing
		return false;
		// true if want to import without clearing old data
//		return true;
	}
	
	@Override
	public boolean needCleanOnStartup() {
    	return false;
    }
	
	public void testDeleteAllSession() {
		System.out.println("started");		
		Session s = new Session();
		getDefaultManager().txadd(s);
		
		Collection<ModelObject> res = getDefaultManager().getList(Session.class.getName(), null);
		getDefaultManager().txdeleteall(res);
		System.out.println("finished");		
	}
	
	public void testInitialDS() {
		System.out.println("start of testInitialDS...");
		try {
			if(!isIncremental()) {
				clearDS();
			}
			
			IDefaultManager manager = getDefaultManager();
			InputStream in = LiveDSHelper.class.getClassLoader().getResourceAsStream(LIVEDS_PATH+LIVEDS_FILE);
			
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

//			DataStoreUtils.exportDS2(out, Region.class.getName(), manager);
//			DataStoreUtils.exportDS2(out, Goods.class.getName(), manager);
//			DataStoreUtils.exportDS2(out, GoodsGallery.class.getName(), manager);
//			DataStoreUtils.exportDS2(out, GoodsAttr.class.getName(), manager);
			DataStoreUtils.exportDS2(out, DSFile.class.getName(), manager);
			
//			DataStoreUtils.exportDS2(out, GoodsType.class.getName(), manager);
//			DataStoreUtils.exportDS2(out, Attribute.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Brand.class.getName(), manager);
//			DataStoreUtils.exportDS2(out, Category.class.getName(), manager);
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
	
	public void testQueryAttributes() {
		System.out.println("start of testQueryAttributes...");
		try {
			IDefaultManager manager = getDefaultManager();

			List<Attribute> list = (List<Attribute>)manager.getList(Attribute.class.getName(), null);
			for(Attribute att:list) {
				System.out.println("name: "+att.getAttrName());
				System.out.println("goodsType: "+att.getGoodsType());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		System.out.println("end of testQueryGoods...");
		
		
	}

}
