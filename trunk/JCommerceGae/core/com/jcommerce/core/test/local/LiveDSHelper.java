package com.jcommerce.core.test.local;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsGallery;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.test.BaseDAOTestCase;

public class LiveDSHelper extends BaseDAOTestCase {
	// the real path -- don't overwrite the content
	public static final String LIVEDS_PATH = "D:/JCommerce/JCommerce/liveDSData/";
	
	// test path
//	public static final String LIVEDS_PATH = "D:/JCommerce/liveDSData/";
	public static final String LIVEDS_DATA = "mydata.txt";
	public static final String LIVEDS_FILE = "files/";
	
	@Override
    public String getDbStorePath() {
//    	return "D:/JCommerce/JCommerceGae/war";
    	return "D:/JCommerce/JCommerceGae/testdatastore";
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
	
	public void testInitialDS() {
		System.out.println("start of testInitialDS...");
		try {
			if(!isIncremental()) {
				clearDS();
			}
			
			IDefaultManager manager = getDefaultManager();
//			InputStream in = LiveDSHelper.class.getClassLoader().getResourceAsStream(LIVEDS_PATH+LIVEDS_FILE);
			InputStream in = new FileInputStream(new File(LIVEDS_PATH, LIVEDS_DATA));
			
			
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
			

			
			// prepare it first
			File filedir = new File(LIVEDS_PATH+LIVEDS_FILE);
			if(!filedir.exists()) {
				filedir.mkdir();
			} else {
				File[] files = filedir.listFiles();
				for(File file : files) {
					file.delete();
				}
				filedir.delete();
				filedir.mkdir();
			}
			
			IDefaultManager manager = getDefaultManager();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			DataStoreUtils.exportDS2(out, Region.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Goods.class.getName(), manager);
			DataStoreUtils.exportDS2(out, GoodsGallery.class.getName(), manager);
			DataStoreUtils.exportDS2(out, GoodsAttr.class.getName(), manager);
			DataStoreUtils.exportDS2(out, DSFile.class.getName(), manager);
			DataStoreUtils.exportDS2(out, GoodsType.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Attribute.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Brand.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Category.class.getName(), manager);
			out.flush();
			String exported = new String(out.toByteArray(), ENCODING);

			System.out.println("exported:\r\n " + exported);
			
			
			writeToData(out.toByteArray());
			

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		System.out.println("end of testExportDS...");
	}
	


	public static void writeToFiles(String fileName, byte[] bytes) {
		// TODO eleborate
		try {
			
			System.out.println("in[writeToFiles] NOTE: remember to switch on the commented codes to write file");
			
//			FileOutputStream fos = new FileOutputStream(new File(LiveDSHelper.LIVEDS_PATH+LiveDSHelper.LIVEDS_FILE, fileName));
//			fos.write(bytes);
//			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeToData(byte[] bytes) {
		try {
			
			System.out.println("in[writeToData] NOTE: remember to switch on the commented codes to write file");
			// TODO eleborate
//			FileOutputStream dataos = new FileOutputStream(new File(LIVEDS_PATH, LIVEDS_DATA));
//			dataos.write(bytes);
//			dataos.close();	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
