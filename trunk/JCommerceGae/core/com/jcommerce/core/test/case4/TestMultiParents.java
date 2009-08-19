package com.jcommerce.core.test.case4;

import java.util.Set;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.core.test.case2.Child;
import com.jcommerce.core.test.case2.Parent;

/**
 * case that multile parents with bi-directionaly owned relation to child 
 * @author yli
 *
 */
public class TestMultiParents extends BaseDAOTestCase {
    protected String getDbStorePath() {
    	return "D:/temp";
//    	return "D:/JCommerce/JCommerceGae/war";
    }
    
    @Override
    public boolean needCleanOnStartup() {
    	return true;
    }
    
    
    public void testAdd() {
		System.out.println("start of testAdd");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			

			clearDS();
			
			pm.currentTransaction().begin();
			Parent41 p1 = new Parent41();
			p1.setName("xxx");
			// javax.jdo.JDOException: App Engine ORM does not support multiple parent key provider fields.
			p1 = pm.makePersistent(p1);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Child4 a = new Child4();
			a.setName("x11");
			a.setParent41(p1);
			a = pm.makePersistent(a);
			//			
			
//			
			pm.currentTransaction().commit();
			String pid = p1.getPkId();
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", aid: "+a.getPkId());
			

			
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
    
    public void testAdd2() {
		System.out.println("start of testAdd2");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			

			clearDS();
			
			pm.currentTransaction().begin();
			Parent41 p1 = new Parent41();
			p1.setName("xxx");
			Child4 a = new Child4();
			a.setName("x11");
			p1.getChildren().add(a);
			// javax.jdo.JDOException: App Engine ORM does not support multiple parent key provider fields.
			p1 = pm.makePersistent(p1);
			
			pm.currentTransaction().commit();


			String pid = p1.getPkId();
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", aid: "+a.getPkId());
			

			
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
}
