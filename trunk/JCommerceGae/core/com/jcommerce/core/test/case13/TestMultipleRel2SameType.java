package com.jcommerce.core.test.case13;

import java.util.Set;

import javax.jdo.PersistenceManager;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;

public class TestMultipleRel2SameType extends BaseDAOTestCase {
	
    protected String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/testdatastore2";
//    	return "D:/JCommerce/JCommerceGae/war";
    }
    
    @Override
    public boolean needCleanOnStartup() {
    	return true;
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
			p.getChildset1().add(c1);
//			c1.setParent(p);
			
			c2 = new Child3();
			c2.setName("abc");
			p.getChildset2().add(c2);
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
			Set s1 = p.getChildset1();
			Set s2 = p.getChildset2();
			System.out.println("size: "+s1.size()+", "+s2.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			pm.close();
		}
	}
}
