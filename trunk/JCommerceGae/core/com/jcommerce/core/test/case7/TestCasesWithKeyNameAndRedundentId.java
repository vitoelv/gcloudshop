package com.jcommerce.core.test.case7;

import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;

/*
 * case for two parents with single unowned child, and parents are tied to an archor
 * 
 */
public class TestCasesWithKeyNameAndRedundentId extends BaseDAOTestCase {
	
	public void testAdd() {
		System.out.println("start of testAdd");
		// 
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			// prepare
			
			String pkn = "p1";
//			String ckn = "c1";
			
			pm.currentTransaction().begin();
			
			Parent7 p1 = new Parent7();
			p1.setName("xxx");
			p1.setKeyName("p1");
			p1 = pm.makePersistent(p1);
//			Child7 c = new Child7();
//			c.setName("yyy");
//			c.setKeyName("c1");
			
			pm.currentTransaction().commit();
			
			String pid1 = p1.getId();

			System.out.println(", pid1="+pid1);
									
			// verify 
			p1 = pm.getObjectById(Parent7.class, pkn);
			System.out.println("pid="+p1.getId()+", pname="+p1.getName());
	
			
			System.out.println("end of testAdd");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    }		
	public void testAdd2() {
		System.out.println("start of testAdd2");
		// 
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			// prepare
			
			String pkn = "p1";
			String ckn = "c1";
			
			pm.currentTransaction().begin();
			
			Parent7 p1 = new Parent7();
			p1.setName("xxx");
			p1.setKeyName("p1");
			Child7 c = new Child7();
			c.setName("yyy");
			c.setKeyName("c1");
			p1.setChild7(c);
			p1.setChild7KN(ckn);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid1 = p1.getId();

			System.out.println(", pid1="+pid1);
									
			// verify 
			p1 = pm.getObjectById(Parent7.class, pkn);
			System.out.println("pid="+p1.getId()+", pname="+p1.getName()+", ckn="+p1.getChild7KN());
			c = p1.getChild7();
			System.out.println("cid="+c.getId()+", cname="+c.getName());

			// Exception: org.datanucleus.exceptions.NucleusObjectNotFoundException: Could not retrieve entity of kind Child7 with key Child7(c1)
			c = pm.getObjectById(Child7.class, ckn);
			System.out.println("cid="+c.getId()+", cname="+c.getName());
			System.out.println("end of testAdd2");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    }		
	
	
	public void testAdd3() {
		System.out.println("start of testAdd3");
		// 
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			// prepare
			
			String pkn = "p1";
			String ckn = "c1";
			
			pm.currentTransaction().begin();
			
			Parent7 p1 = new Parent7();
			p1.setName("xxx");
			p1.setKeyName("p1");
			Child7 c = new Child7();
			c.setName("yyy");
			c.setKeyName("c1");
//			c.setParent(p1);
			p1.setChild7(c);
			p1.setChild7KN(ckn);
			String cid = KeyFactory.keyToString(new KeyFactory.Builder("Parent7", pkn).addChild("Child7", ckn).getKey());

			p1.setChild7Id(cid);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid1 = p1.getId();
			System.out.println("cid="+cid+", pid1="+pid1);
			assertTrue(cid.equals(c.getId()));
									
			// verify 
			p1 = pm.getObjectById(Parent7.class, pkn);
			System.out.println("pid="+p1.getId()+", pname="+p1.getName()+", ckn="+p1.getChild7KN());
			c = p1.getChild7();
			System.out.println("cid="+c.getId()+", cname="+c.getName());
			
			p1 = c.getParent();
			System.out.println("pid="+p1.getId()+", pname="+p1.getName()+", ckn="+p1.getChild7KN());

			c = pm.getObjectById(Child7.class, cid);
			System.out.println("cid="+c.getId()+", cname="+c.getName());
			
			// Exception: org.datanucleus.exceptions.NucleusObjectNotFoundException: Could not retrieve entity of kind Child7 with key Child7(c1)
//			c = pm.getObjectById(Child7.class, ckn);
//			System.out.println("cid="+c.getId()+", cname="+c.getName());
//			System.out.println("end of testAdd3");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    }		
}
