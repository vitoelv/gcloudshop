package com.jcommerce.core.test.case12;

import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.gwt.client.model.IBrand;


public class TestInheritance extends BaseDAOTestCase {
	
    protected String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/testdatastore2";
//    	return "D:/JCommerce/JCommerceGae/war";
    }
    
    @Override
    public boolean needCleanOnStartup() {
//    	return true;
    	return false;
    }
	
    public void testLoad() {
		System.out.println("start of testLoad");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {

			clearDS();
			pm.currentTransaction().begin();
			Parent p = new Parent();
			p.setName("xxx");
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();
			
			// this will work
			String pid = p.getPkId();
			System.out.println("pid: "+pid);
			
			
			Child c1=null, c2=null;
			pm.currentTransaction().begin();
			p = new Parent();
			p.setName("yyy");
			c1 = new Child();
			c1.setName("abc");
			p.getChildren().add(c1);
//			c1.setParent(p);
			
			c2 = new Child();
			c2.setName("abc");
			p.getChildren().add(c2);
//			c2.setParent(p);
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();
			
			// this will work
			pid = p.getPkId();
			System.out.println("pid: "+pid);
			String c1id = c1.getPkId();
			String c2id = c2.getPkId();
			System.out.println("c1id: "+c1id+", c2id: "+c2id);
			
			
			p = pm.getObjectById(Parent.class, pid);
			Set<Child> set = p.getChildren();
			System.out.println("size: "+set.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			pm.close();
		}
	}
    
    
    
    
    public void testLoad2() {
		System.out.println("start of testLoad2");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			String pid = null;
			Parent2 p = null;
			Child2 c1=null, c2=null;
			clearDS();
//			pm.currentTransaction().begin();
//			Parent2 p = new Parent2();
//			p.setName("xxx");
//			p = pm.makePersistent(p);
//			pm.currentTransaction().commit();
//			
//			// this will work
//			String pid = p.getPkId();
//			System.out.println("pid: "+pid);
			

			pm.currentTransaction().begin();
			p = new Parent2();
			p.setName("yyy");
			c1 = new Child2();
			c1.setName("abc");
			p.setThe1(c1);
//			c1.setParent(p);
			
			c2 = new Child2();
			c2.setName("abc");
			p.setThe2(c2);
//			c2.setParent(p);
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();
			
			// this will work
			pid = p.getPkId();
			System.out.println("pid: "+pid);
			String c1id = c1.getPkId();
			String c2id = c2.getPkId();
			System.out.println("c1id: "+c1id+", c2id: "+c2id);
			
			
			p = pm.getObjectById(Parent2.class, pid);
			c1 = p.getThe1();
			c2 = p.getThe2();
			System.out.println("size: "+c1+", "+c2);
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			pm.close();
		}
	}
    
    public void testLoad21() {
		System.out.println("start of testLoad21");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			String pid = null;
			Parent21 p = null;
			Child2 c1=null, c2=null;
			clearDS();

			String bn = "aaaaa";
			String bkn = "bkn";
			String fkn1 = "fkn1";
			String fkn2 = "fkn2";
			pm.currentTransaction().begin();
			p = new Parent21();
			p.setKeyName(bkn);
			p.setName(bn);
			c1 = new Child2();
			c1.setKeyName(fkn1);
			c1.setName("bbbb");
			p.setThe1(c1);
			String fid1 = KeyFactory.keyToString(new KeyFactory.Builder("Parent21",bkn).addChild("Child2", fkn1).getKey());
//			c1.setParent(p);
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();
			String c1id = c1.getPkId();
			pid = p.getPkId();
			
			p = pm.getObjectById(Parent21.class, pid);
			Child2 c = p.getThe1();
			System.out.println("c1id: "+c1id+", cname: "+c.getKeyName());
			assertTrue(fid1.equals(c1id));
			
			pm.currentTransaction().begin();
			p = pm.getObjectById(Parent21.class, pid);
			c2 = new Child2();
			c2.setKeyName(fkn2);
			p.setThe1(c2);
//			c1.setParent(p);
			String fid2 = KeyFactory.keyToString(new KeyFactory.Builder("Parent21",bkn).addChild("Child2", fkn2).getKey());
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();
			String c2id = c2.getPkId();
			
			
			p = pm.getObjectById(Parent21.class, pid);
			c = p.getThe1();
			System.out.println("c2id: "+c2id+", cname: "+c.getKeyName());
			assertTrue(fkn2.equals(c.getKeyName()));
			assertFalse(fid1.equals(c2id));
			assertTrue(fid2.equals(c2id));
			
			// verify that the old is deleted
//			c = pm.getObjectById(Child2.class, c1id);
//			assertTrue(c==null);
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			pm.close();
		}
	}
    
    
    public void testLoadBrand() {
		System.out.println("start of testLoadBrand");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			String pid = null;
			Brand p = null;
			DSFile c1=null, c2=null;
			clearDS();

			String bn = "aaaaa";
			
			String bkn = "bkn";
			String fkn1 = "fkn1";
			String fkn2 = "fkn2";
			pm.currentTransaction().begin();
			p = new Brand();
			p.setKeyName(bkn);
			p.setBrandName(bn);
			c1 = new DSFile();
			c1.setKeyName(fkn1);
			c1.setFileName("ff1");
			p.setLogoFile(c1);
			String fid1 = KeyFactory.keyToString(new KeyFactory.Builder("Brand",bkn).addChild("DSFile", fkn1).getKey());
			p.setLogoFileId(fid1);
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();

			pid = p.getPkId();
			String c1id = c1.getPkId();


			
			p = pm.getObjectById(Brand.class, pid);
			DSFile c = p.getLogoFile();
			System.out.println("c1id: "+c1id+", cname: "+c.getKeyName());
			assertTrue(fid1.equals(c1id));
			
			
			pm.currentTransaction().begin();
			p = pm.getObjectById(Brand.class, pid);
			c2 = new DSFile();
			c2.setKeyName(fkn2);
			c2.setFileName("ff2");
			p.setLogoFile(c2);
			String fid2 = KeyFactory.keyToString(new KeyFactory.Builder("Brand",bkn).addChild("DSFile", fkn2).getKey());
			p.setLogoFileId(fid2);
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();
			
			String c2id = c2.getPkId();
			
			// verify the new replaced the old
			p = pm.getObjectById(Brand.class, pid);
			c = p.getLogoFile();
			System.out.println("c2id: "+c2id+", cname: "+c.getKeyName());
			assertTrue(fkn2.equals(c.getKeyName()));
			assertTrue(fid2.equals(c2id));
			
			// verify that the old is deleted
			c = pm.getObjectById(DSFile.class, c1id);
			assertTrue(c==null);
			
			System.out.println("end of testLoadBrand");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			pm.close();
		}
	}
    
    public void testReadTest21() {
		System.out.println("start of testReadTest21");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
		String bn = "aaaaa";
		Query query = pm.newQuery(Parent21.class);
		List<Parent21> list = (List<Parent21>)query.execute(); 
		
		for(Parent21 b : list) {
			String pkid = b.getPkId();
			String name = b.getName();
			Child2 file = b.getThe1();
			if(file==null) {
			}
			String fileId = file.getPkId();
			String keyname = file.getKeyName();
			
			
			System.out.println("pkid="+pkid);
			System.out.println("name="+name);
			System.out.println("file="+file);
			System.out.println("fileId="+fileId);
			System.out.println("keyname="+keyname);
			
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
    }
    
    public void testReadTestBrand() {
		System.out.println("start of testReadTestBrand");
		try {
		String bn = "aaaaa";
		Criteria c = new Criteria();
		c.addCondition(new Condition(IBrand.BRAND_NAME, Condition.EQUALS, bn));
		List<Brand> list = getDefaultManager().getList(Brand.class.getName(), c);
		for(Brand b : list) {
			String pkid = b.getPkId();
			String fid = b.getLogoFileId();
			DSFile file = b.getLogoFile();
			if(file==null) {
			}
			String fileId = file.getPkId();
			String keyname = file.getKeyName();
			String fileName = file.getFileName();
			
			System.out.println("pkid="+pkid);
			System.out.println("fid="+fid);
			System.out.println("file="+file);
			System.out.println("fileId="+fileId);
			System.out.println("keyname="+keyname);
			System.out.println("fileName="+fileName);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
    }
    public void testReadLiveBrand() {
		System.out.println("start of testReadLiveBrand");
		try {
		String bn = "moto";
		Criteria c = new Criteria();
		c.addCondition(new Condition(IBrand.BRAND_NAME, Condition.EQUALS, bn));
		List<Brand> list = getDefaultManager().getList(Brand.class.getName(), c);
		for(Brand b : list) {
			String pkid = b.getPkId();
			String fid = b.getLogoFileId();
			DSFile file = b.getLogoFile();
			if(file==null) {
			}
			String fileId = file.getPkId();
			String keyname = file.getKeyName();
			String fileName = file.getFileName();
			
			System.out.println("pkid="+pkid);
			System.out.println("fid="+fid);
			System.out.println("file="+file);
			System.out.println("fileId="+fileId);
			System.out.println("keyname="+keyname);
			System.out.println("fileName="+fileName);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
    }
    public void testLoadBrand2() {
		System.out.println("start of testLoadBrand2");

		IDefaultManager mgr = getDefaultManager();
		try {
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


			
			p = (Brand)mgr.get(Brand.class.getName(), pid);
			DSFile c = p.getLogoFile();
			System.out.println("c1id: "+c1id+", cname: "+c.getKeyName());
			assertTrue(fid1.equals(c1id));
			
			
			
			p = (Brand)mgr.get(Brand.class.getName(), pid);
			c2 = new DSFile();
			c2.setKeyName(fkn2);
			c2.setFileName("ff2");
			p.setLogoFile(c2);
			String fid2 = KeyFactory.keyToString(new KeyFactory.Builder("Brand",bkn).addChild("DSFile", fkn2).getKey());
			p.setLogoFileId(fid2);
			mgr.txattach(p);
			
			String c2id = c2.getPkId();
			
			// verify the new replaced the old
			p = (Brand)mgr.get(Brand.class.getName(), pid);
			c = p.getLogoFile();
			System.out.println("c2id: "+c2id+", cname: "+c.getKeyName());
			assertTrue(fkn2.equals(c.getKeyName()));
			assertTrue(fid2.equals(c2id));
			
			// verify that the old is deleted
//			c = (DSFile)mgr.get(DSFile.class.getName(), c1id);
//			assertTrue(c==null);
			
			System.out.println("end of testLoadBrand2");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			
		}
	}
    public void testLoad3() {
		System.out.println("start of testLoad3");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			String pid = null;
			Parent3 p = null;
			Child3 c1=null, c2=null;
			clearDS();


			pm.currentTransaction().begin();
			p = new Parent3();
			p.setName("yyy");
			c1 = new Child3();
			c1.setName("abc");
			p.setThe1(c1);
//			c1.setParent(p);
			
			c2 = new Child3();
			c2.setName("abc");
			p.setThe2(c2);
//			c2.setParent(p);
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();
			
			// this will work
			pid = p.getPkId();
			System.out.println("pid: "+pid);
			String c1id = c1.getPkId();
			String c2id = c2.getPkId();
			System.out.println("c1id: "+c1id+", c2id: "+c2id);
			
			
			p = pm.getObjectById(Parent3.class, pid);
			c1 = p.getThe1();
			c2 = p.getThe2();
			System.out.println("size: "+c1+", "+c2);
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			pm.close();
		}
	}
}
