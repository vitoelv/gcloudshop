package com.jcommerce.core.test.case1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.core.util.DataStoreUtils;

public class TestBulkLoader extends BaseDAOTestCase {
	public static String ENCODING = "UTF-8";
    protected String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/testdatastore";
//    	return "D:/JCommerce/JCommerceGae/war";
    }
    
    @Override
    public boolean needCleanOnStartup() {
    	return false;
    }
    
    public void testLoadAndUnload() {
		System.out.println("start of testLoadAndUnload");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		IDefaultManager manager = getDefaultManager();
		try {
			
			String pkn = "pkn";
			String akn = "akn";
			String pid = "";
			String aid = "";
			Key key = null;
			String newId = "";
			clearDS();
			
			pm.currentTransaction().begin();
			Person p = new Person();
//			key = new KeyFactory.Builder(Person.class.getSimpleName(), pkn).getKey();
//			pid = KeyFactory.keyToString(key);
//			p.setId(pid);

			p.setName("xxx");
			p.setKeyName(pkn);
			pm.makePersistent(p);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Address a = new Address();
//			key = new KeyFactory.Builder(Person.class.getSimpleName(), pkn).
//				addChild(Address.class.getSimpleName(), akn).getKey();
//			aid = KeyFactory.keyToString(key);
//			a.setId(aid);
			a.setLoc("x11");
			a.setKeyName(akn);
			a.setPerson(p);
//			p.getAddresses().add(a);

			pm.makePersistent(a);
			pm.currentTransaction().commit();
			pid = p.getId();
			aid = a.getId();
			System.out.println("pid: "+p.getId()+", aid: "+a.getId());
			
			pkn = KeyFactory.stringToKey(pid).getName();
			akn = KeyFactory.stringToKey(aid).getName();
			System.out.println("pkn: "+pkn+", akn: "+akn);
			
			
			clearDS();
			
			
			// recover the datastore
//			pm.currentTransaction().begin();
//			p = new Person();
////			p.setKeyName(pkn);
//			
//			key = new KeyFactory.Builder(Person.class.getSimpleName(), pkn).getKey();
//			newId = KeyFactory.keyToString(key);
//			System.out.println("newId: "+newId);
//			p.setId(pid);
//			p.setName("xxx");
//			pm.makePersistent(p);
//			
//			pm.currentTransaction().commit();
//			pm.currentTransaction().begin();
//			
//			a = new Address();
//			key = new KeyFactory.Builder(Person.class.getSimpleName(), pkn).
//				addChild(Address.class.getSimpleName(), akn).getKey();
//			newId = KeyFactory.keyToString(key); 
//			System.out.println("newId: "+newId);
//			a.setId(aid);
////			a.setKeyName(key.getName());
//			a.setLoc("x11");
//			pm.makePersistent(a);
////			a.setKeyName(akn);
//			pm.currentTransaction().commit();
			

			p = new Person();
			p.setId(pid);
			p.setName("xxx");
			manager.txattach(p);
			
			a = new Address();
			a.setId(aid);
			a.setLoc("x11");
			manager.txattach(a);
			
			System.out.println("pid="+p.getId());
			System.out.println("aid="+a.getId());
			System.out.println(", akn="+a.getKeyName());
			
			// verify
			// ID recovered
			assertTrue(pid.equals(p.getId()) && aid.equals(a.getId()));
			
			pm.currentTransaction().begin();
			Person p2 = pm.getObjectById(Person.class,pid);
			System.out.println("pkn="+p2.getKeyName());
			Set addresses = p2.getAddresses();
			int size = addresses.size();
			System.out.println("size="+size);
			pm.currentTransaction().commit();
			
			// relation recovered
			assertTrue(1==size);
			System.out.println("end of testLoadAndUnload");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
    
    public String getEncodedId(Class cls, String kn) {
    	return KeyFactory.keyToString(new KeyFactory.Builder(cls.getSimpleName(), kn).getKey());
    }
    
    public void testLoadBrand() {
    	System.out.println("start of testLoadBrand");
		try {
			clearDS();
			
			String bkn1 = "";
			String bkn2 = "";
//			String id = "";
			IDefaultManager manager = getDefaultManager();
			
			Brand b = new Brand();
//			b.setId(getEncodedId(Brand.class, bkn1));
			b.setName("微软");
			b.setDescription("microsoft");			 
//			b.setKeyName(bkn1);
			manager.txadd(b);
			bkn1 = b.getKeyName();
			
			
			b = new Brand();
//			b.setId(getEncodedId(Brand.class, bkn2));
			b.setName("诺基亚");
			b.setDescription("nokia");			 
//			b.setKeyName(bkn1);
			manager.txadd(b);
			bkn2 = b.getKeyName();
			
			System.out.println("bkn1="+bkn1+", bkn2="+bkn2);
			
			// verify
			List res = new ArrayList();
			manager.getList(res, Brand.class.getName(), null, -1, -1);
			int size = res.size();
			System.out.println("size="+size);
			assertTrue(2==size);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end of testLoadBrand");    	
    }
    
    
    public void testExportDataSample() {
    	System.out.println("start of testExportDataSample");
    	PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			IDefaultManager manager = getDefaultManager();
			
			GoodsType g = new GoodsType();
			g.setName("手机");
			manager.txadd(g);
			
			Attribute a = new Attribute();
			a.setName("颜色");
			a.setGoodsType(g);
			manager.txadd(a);
			
			Brand b = new Brand();
			b.setName("微软");
			b.setDescription("microsoft");			 
			manager.txadd(b);
			
			Category c = new Category();
			c.setName("手机通讯");
			c.setMeasureUnit("台");
			c.setShow(true);
			c.setShowInNavigator(true);
			manager.txadd(c);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			
			DataStoreUtils.exportDS2(out, GoodsType.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Attribute.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Brand.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Category.class.getName(), manager);			
			out.flush();
			String exported = new String(out.toByteArray(), ENCODING);
			
			System.out.println("exported:\r\n "+exported);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		System.out.println("end of testExportDataSample");    
    }
    
    
    public void testLoadGoodsType() {
    	System.out.println("start of testLoadGoodsType");
    	PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			
			IDefaultManager manager = getDefaultManager();
			
			GoodsType b = new GoodsType();
			b.setName("手机");
			manager.txadd(b);
			
			Attribute a = new Attribute();
			a.setName("颜色");
			a.setGoodsType(b);
			manager.txadd(a);
			
			a = new Attribute();
			a.setName("大小");
			a.setGoodsType(b);
			manager.txadd(a);
			
			
			b = new GoodsType();
			b.setName("电脑");			 
			manager.txadd(b);
			
			a = new Attribute();
			a.setName("功能");
			a.setGoodsType(b);
			manager.txadd(a);

			a = new Attribute();
			a.setName("耗电");
			a.setGoodsType(b);
			manager.txadd(a);
			
			
			
			// verify
			verifyloading(pm);
			
//			pm.currentTransaction().commit();
			
			// export "D:/logs/exported.txt"
			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			
			DataStoreUtils.exportDS2(out, GoodsType.class.getName(), manager);
			DataStoreUtils.exportDS2(out, Attribute.class.getName(), manager);
			out.flush();
			String exported = new String(out.toByteArray(), ENCODING);
			
			System.out.println("exported:\r\n "+exported);
			
			clearDS();
			InputStream in = new ByteArrayInputStream(exported.getBytes(ENCODING));
			DataStoreUtils.importDS2(in, manager);
			
			// verify
			verifyloading(pm);
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		System.out.println("end of testLoadGoodsType");    	
    }

	private void verifyloading(PersistenceManager pm) {
		//			pm.currentTransaction().begin();
					Query query = pm.newQuery(GoodsType.class);
					List<GoodsType> res = (List<GoodsType>)query.execute();
					int size = res.size();
					System.out.println("size="+size);
					assertTrue(2==size);
					for(GoodsType gt:res) {
						Set set = gt.getAttributes();
						System.out.println("att count="+set.size());
						assertTrue(2==set.size());
					}
					
					
		//			pm.currentTransaction().commit();
		//			pm.currentTransaction().begin();
					
					query = pm.newQuery(Attribute.class);
					List<Attribute> atts = (List<Attribute>)query.execute();
					size = atts.size();
					System.out.println("size="+size);
					assertTrue(4==size);
					for(Attribute att:atts) {
						GoodsType gt = att.getGoodsType();
						System.out.println("att="+att.getName()+", gt="+gt.getName());
					}
	}
    
    

    
    public void exportAttribute() {
    	StringBuffer buf = new StringBuffer();
    	IDefaultManager manager = getDefaultManager();
		List res = new ArrayList();
		manager.getList(res, Attribute.class.getName(), null, -1, -1);
		for(Iterator it = res.iterator();it.hasNext();) {
			Attribute gt = (Attribute)it.next();
			buf.append(gt.getClass().getName()).append(",");
			buf.append(gt.getKeyName()).append(",");
			buf.append(gt.getName()).append(",");
			buf.append(gt.getGroup()).append(",");
			buf.append(gt.getIndex()).append(",");
			buf.append(gt.getInputType()).append(",");
			buf.append(gt.getSortOrder()).append(",");
			
		}
    }

    
    public void testRead() {
    	System.out.println("start of testRead");
    	try {
    		IDefaultManager manager = getDefaultManager();
    		
//    		manager.delete(Goods.class.getName(), "agByCwsSBUdvb2RzGA4M");
    		
//    		manager.delete(Goods.class.getName(), "agByCwsSBUdvb2RzGA8M");
    		
//    		manager.delete(Goods.class.getName(), "agByCwsSBUdvb2RzGBAM");
    		
//    		List brandList = new ArrayList();
//    		manager.getList(brandList, Brand.class.getName(), null, 0, 10);
//    		assertTrue(brandList.size()>=2);
//    		String bid1 = ((Brand)brandList.get(0)).getId();
//    		String bid2 = ((Brand)brandList.get(1)).getId();
    		
    		Goods g1 = new Goods();
    		g1.setAddTime(new Date());
//    		g1.setBrandId(bid1);
    		g1.getCategoryIds().add("123");
    		g1.getCategoryIds().add("456");
    		String gid1 = manager.txadd(g1);
    		System.out.println("gid1: "+gid1);
    		
    		Goods g2 = new Goods();
    		g2.setAddTime(new Date());
//    		g2.setBrandId(bid2);
    		g2.getCategoryIds().add("123sss");
    		g2.getCategoryIds().add("456xxx");
    		String gid2 = manager.txadd(g2);
    		System.out.println("gid2: "+gid2);
    		
    		List res = new ArrayList();
    		int length = manager.getList(res, Goods.class.getName(), new Criteria(), 0, 5);
    		System.out.println("Test case 1.... size: "+res.size()+", length="+length);
    		
    		for(Iterator it = res.iterator();it.hasNext();) {
    			ModelObject mo = (ModelObject)it.next();
    			manager.txdelete(Goods.class.getName(), mo.getId());
    		}
    		
    		
    		res = new ArrayList();
    		length = manager.getList(res, Goods.class.getName(), new Criteria(), 0, 5);
    		System.out.println("Test case 2.... size: "+res.size()+", length="+length);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    
    
    
    

}
