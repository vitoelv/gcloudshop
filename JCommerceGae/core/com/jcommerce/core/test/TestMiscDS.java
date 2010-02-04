package com.jcommerce.core.test;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.ShopConfig;
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
	
	public void testQueryShopConfig() {
		IDefaultManager manager = getDefaultManager();
		List res = new ArrayList();
		String className = ShopConfig.class.getName();
		manager.getList(res, className, null, -1, -1);
		System.out.println("size: "+res.size());
		
		
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
	
	public void testExtractKey() {
		System.out.println("start of testExtractKey");
		try {
			clearDS();
			// load data
			IDefaultManager mgr = getDefaultManager();

			String pid = null;
			Brand p = null;
			DSFile c1=null, c2=null;
			clearDS();
			
			String bkn = "bkn";
			String fkn1 = "fkn1";
			String fkn2 = "fkn2";
			
			
			p = new Brand();
			p.setKeyName(bkn);
			p.setBrandName("aaaaa");
			c1 = new DSFile();
			c1.setKeyName(fkn1);
			c1.setFileName("ff1");
			p.setLogoFile(c1);
			String fid1 = KeyFactory.keyToString(new KeyFactory.Builder("Brand",bkn).addChild("DSFile", fkn1).getKey());
			p.setLogoFileId(fid1);
			mgr.txattach(p);
			

			pid = p.getPkId();
			String c1id = c1.getPkId();


			// verify the relation
			p = (Brand)mgr.get(Brand.class.getName(), pid);
			DSFile c = p.getLogoFile();
			System.out.println("c1id: "+c1id+", cname: "+c.getKeyName());
			assertTrue(fid1.equals(c1id));

			
			// check the key
			Key key = KeyFactory.stringToKey(c1id);
			System.out.println("key: "+key);
			System.out.println("keyname: "+key.getName()+", kind: "+key.getKind());
			
			Key pkey = key.getParent();
			System.out.println("pkey: "+pkey);
			if(pkey!=null) {
				System.out.println("p keyname: "+pkey.getName()+", p kind: "+pkey.getKind());
				
			}
			
			Key ppkey = pkey.getParent();
			System.out.println("ppkey: "+ppkey);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("end of testExtractKey");
	}
}
