package com.jcommerce.core.test.case6;

import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;

/*
 * case for two parents with single unowned child, and parents are tied to an archor
 * 
 */
public class TestCasesWithArchors extends BaseDAOTestCase {
	
	public void testAdd() {
		System.out.println("start of testAdd");
		// 
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			// prepare
			pm.currentTransaction().begin();
			Archor a = new Archor();
			a.setName("archor");
			a = pm.makePersistent(a);
			pm.currentTransaction().commit();
			
			pm.currentTransaction().begin();
			
			Parent61 p1 = new Parent61();
			p1.setName("xxx");
//			a.getParent61s().add(p1);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			
			pm.currentTransaction().begin();
			Parent62 p2 = new Parent62();
			p2.setName("yyy");
//			a.getParent62s().add(p2);
			p2 = pm.makePersistent(p2);
			
			pm.currentTransaction().commit();

			String aid = a.getPkId();
			String pid1 = p1.getPkId();
			String pid2 = p2.getPkId();
			
			System.out.println("aid: "+aid+", pid1="+pid1+", pid2="+pid2);
			
			a = pm.getObjectById(Archor.class, aid);
			Set p61s = a.getParent61s();
			Set p62s = a.getParent62s();
			System.out.println("p61s size: "+(p61s==null? "null":p61s.size()));
			System.out.println("p62s size: "+(p62s==null? "null":p62s.size()));
			
			// delete both in one txn
			pm.currentTransaction().begin();
			pm.deletePersistent(p1);
			pm.deletePersistent(p2);
			pm.currentTransaction().commit();
			
			// verify delete
			try {
				p1 = pm.getObjectById(Parent61.class, pid1);
			} catch(JDOObjectNotFoundException ex) {
				assertTrue(true);
			}
			try {
				p2 = pm.getObjectById(Parent62.class, pid2);
			} catch(JDOObjectNotFoundException ex) {
				assertTrue(true);
			}
			
			
//			pm.currentTransaction().begin();
//			Child6 c = new Child6();
//			c.setName("ccc");
//			p1.getChild61().add(c);
//			// exception: 
//			// org.datanucleus.exceptions.NucleusUserException: Detected attempt to establish !gmail.com:Parent62(30) as the parent of !gmail.com:Parent61(29)/Child6(31) but the entity identified by !gmail.com:Parent61(29)/Child6(31) is already a child of !gmail.com:Parent61(29).  A parent cannot be established or changed once an object has been persisted.
////			p2.getChild62().add(c);
//			pm.makePersistent(c);
//			
//			pm.currentTransaction().commit();
//			String cid = c.getPkId();
//			System.out.println("cid="+cid);
//			
//			
//			
//			// delete both in one txn
//			pm.currentTransaction().begin();
//			pm.deletePersistent(c);
//			p1.getChild61().remove(c);
//			pm.deletePersistent(p2);
//			pm.currentTransaction().commit();
//			
//			// verify delete
//			try {
//				c = pm.getObjectById(Child6.class, cid);
//			} catch(JDOObjectNotFoundException ex) {
////				ex.printStackTrace();
//				assertTrue(true);
//			}
//
//			try {
//				p2 = pm.getObjectById(Parent62.class, pid2);
//			} catch(JDOObjectNotFoundException ex) {
////				ex.printStackTrace();
//				assertTrue(true);
//			}
//
//			try {
//				p1 = pm.getObjectById(Parent61.class, pid1);
//				Set set = p1.getChild61();
//				System.out.println("size of child: "+(set==null?"null":set.size()));
//				
//				assertTrue(set.size()==0);
//				
//			} catch(Exception ex) {
//				ex.printStackTrace();
//			}

			
			System.out.println("end of testAdd");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    }		
	
}
