package com.jcommerce.core.test.case12;

import java.util.Set;

import javax.jdo.PersistenceManager;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;

public class TestInheritance extends BaseDAOTestCase {
	
    protected String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/testdatastore2";
//    	return "D:/JCommerce/JCommerceGae/war";
    }
    
    @Override
    public boolean needCleanOnStartup() {
    	return true;
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
