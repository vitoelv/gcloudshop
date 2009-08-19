package com.jcommerce.core.test.case2;

import java.util.Set;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;

public class TestBulkLoader2 extends BaseDAOTestCase {
	
    protected String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/testdatastore2";
//    	return "D:/JCommerce/JCommerceGae/war";
    }
    
    @Override
    public boolean needCleanOnStartup() {
    	return true;
    }
	

    
    public void testLoadAndUnload() {
		System.out.println("start of testLoadAndUnload");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			
			String pkn = "pkn";
			String akn = "akn";
			Key key = null;
			String newId = "";
			clearDS();
			
			pm.currentTransaction().begin();
			Parent p = new Parent();
//			p.setId("_"+UUIDHexGenerator.newUUID());
			p.setName("xxx");
//			p.setKeyName(pkn);

			Child a = new Child();
			a.setName("x11");
//			a.setKeyName(akn);
			p.getChildren().add(a);
			//			
			Parent p1 = pm.makePersistent(p);
//			
			pm.currentTransaction().commit();
			String pid = p1.getPkId();
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", aid: "+a.getPkId());
			
//			pkn = KeyFactory.stringToKey(pid).getName();
//			akn = KeyFactory.stringToKey(aid).getName();
//			System.out.println("pkn: "+pkn+", akn: "+akn);
			
			
			clearDS();
			
			
			// recover the datastore
			pm.currentTransaction().begin();
			p = new Parent();
//			p.setKeyName(pkn);
			
//			Key key = new KeyFactory.Builder(Person.class.getSimpleName(), pkn).getKey();
//			String newId = KeyFactory.keyToString(key);
//			System.out.println("newId: "+newId);
			p.setPkId(pid);
			p.setName("xxx");
			p1 = pm.makePersistent(p);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			a = new Child();
			key = new KeyFactory.Builder(Parent.class.getSimpleName(), pkn).
				addChild(Child.class.getSimpleName(), akn).getKey();
			newId = KeyFactory.keyToString(key); 
			System.out.println("newId: "+newId);
			a.setPkId(aid);
//			a.setKeyName(key.getName());
			a.setName("x11");
			a = pm.makePersistent(a);
//			a.setKeyName(akn);
			pm.currentTransaction().commit();
			
			System.out.println("pid="+p1.getPkId());
			System.out.println("aid="+a.getPkId());
//			System.out.println(", akn="+a.getKeyName());
			
			pm.currentTransaction().begin();
			Parent p2 = pm.getObjectById(Parent.class,pid);
//			System.out.println("pkn="+p2.getKeyName());
			Set children = p2.getChildren();
			int size = children.size();
			System.out.println("size="+size);
			pm.currentTransaction().commit();
			assertTrue(1==size);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
}
