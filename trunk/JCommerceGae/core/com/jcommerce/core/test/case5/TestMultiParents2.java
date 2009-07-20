package com.jcommerce.core.test.case5;

import java.util.List;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;


/**
 *  case that multile parents with uni-direction owned relation to child 
 * @author yli
 *
 */
public class TestMultiParents2 extends BaseDAOTestCase {
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
		
		// 
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			

			clearDS();
			
			pm.currentTransaction().begin();
			Parent51 p1 = new Parent51();
			p1.setName("xxx");
			p1 = pm.makePersistent(p1);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Child5 a = new Child5();
			a.setName("x11");
//			a.setParent51(p1);
			p1.getChildren().add(a);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid = p1.getId();
			String aid = a.getId();
			System.out.println("pid: "+p1.getId()+", aid: "+a.getId());
			assertTrue(true);
			
			// cascade delete
			pm.currentTransaction().begin();
			pm.deletePersistent(p1);
			pm.currentTransaction().commit();
			// verify cascade delete
			try {
				a = pm.getObjectById(Child5.class, aid);
				assertTrue(false);
			} catch (JDOObjectNotFoundException e) {
				System.out.println("a: "+aid+" cannot find");
				assertTrue(true);
			}

			
			
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
			
			// add
			pm.currentTransaction().begin();
			Parent51 p1 = new Parent51();
			p1.setName("xxx");
			Child5 a = new Child5();
			a.setName("x11");
			p1.getChildren().add(a);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();

			String pid = p1.getId();
			String aid = a.getId();
			System.out.println("pid: "+p1.getId()+", aid: "+a.getId());
			assertTrue(true);
			
			// delete
			pm.currentTransaction().begin();
			pm.deletePersistent(p1);
			pm.currentTransaction().commit();
			// verify delete
			try {
				a = pm.getObjectById(Child5.class, aid);
				// TODO why?
				assertTrue(false);
			} catch (JDOObjectNotFoundException e) {
				System.out.println("a: "+aid+" cannot find");
				assertTrue(true);
			}
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
    
    
    public void testAdd3() {
		System.out.println("start of testAdd3");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			
			pm.currentTransaction().begin();
			Parent51 p1 = new Parent51();
			p1.setName("xxx");
			p1 = pm.makePersistent(p1);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Child5 a = new Child5();
			a.setName("x11");
			p1.getChildren().add(a);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid = p1.getId();
			String aid = a.getId();
			System.out.println("pid: "+p1.getId()+", aid: "+a.getId());
			assertTrue(true);


//			// cascade delete
//			pm.currentTransaction().begin();
//			pm.deletePersistent(p1);
//			pm.currentTransaction().commit();
//			// verify cascade delete
//			try {
//				a = pm.getObjectById(Child4.class, aid);
//				assertTrue(false);
//			} catch (JDOObjectNotFoundException e) {
//				System.out.println("a: "+aid+" cannot find");
//				assertTrue(true);
//			}			
			
			pm.currentTransaction().begin();
			Parent52 p2 = new Parent52();
			p2.setName("xxx");
			p2 = pm.makePersistent(p2);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			// 
			Child5 a2 = new Child5();
			a2.setName("x11");
			p2.getChildren2().add(a2);
			p2 = pm.makePersistent(p2);
			pm.currentTransaction().commit();
			
			String pid2 = p2.getId();
			String aid2 = a2.getId();
			System.out.println("pid2: "+p2.getId()+", aid2: "+a2.getId());
			assertTrue(true);

//			// cascade delete
//			pm.currentTransaction().begin();
//			pm.deletePersistent(p2);
//			pm.currentTransaction().commit();
//			// verify cascade delete
//			try {
//				a2 = pm.getObjectById(Child4.class, aid2);
//				assertTrue(false);
//			} catch (JDOObjectNotFoundException e) {
//				System.out.println("a2: "+aid2+" cannot find");
//				assertTrue(true);
//			}

			// exception: p1 and p2 are not in a Entity Group
//			pm.currentTransaction().begin();
//			pm.deletePersistent(p1);
//			pm.deletePersistent(p2);
//			pm.currentTransaction().commit();
			
			
			// failed.  
			// child are in different Entity Group
			Query q = pm.newQuery(Child5.class);
			List<Child5> childList = (List<Child5>)q.execute();
			System.out.println("childlist size: "+childList.size());
			
			pm.currentTransaction().begin();
			a = pm.getObjectById(Child5.class, aid);
			a2 = pm.getObjectById(Child5.class, aid2);
			pm.deletePersistent(a);
			pm.deletePersistent(a2);
//			for(Child5 child:childList) {
//				System.out.println("child name: "+child.getName());
//				pm.deletePersistent(child);
//			}
			pm.currentTransaction().commit();
			
			System.out.println("end of testAdd3");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
    
    
    public void testDeleteChild() {
		System.out.println("start of testDeleteChild");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			
			pm.currentTransaction().begin();
			Parent51 p1 = new Parent51();
			p1.setName("xxx");
			p1 = pm.makePersistent(p1);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Child5 a = new Child5();
			a.setName("x11");
			p1.getChildren().add(a);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid = p1.getId();
			String aid = a.getId();
			System.out.println("pid: "+p1.getId()+", aid: "+a.getId());
			assertTrue(true);

			// verify add
			p1 = (Parent51)pm.getObjectById(Parent51.class, pid);
			Set<Child5> set = p1.getChildren();
			int size = set.size();
			System.out.println("child size: "+size);
			a = set.iterator().next();
			System.out.println("1) child id: "+a.getId()+", name: "+a.getName());
			
			
			// delete
			pm.currentTransaction().begin();
			a = (Child5)pm.getObjectById(Child5.class, aid);
			System.out.println("2) child id: "+a.getId()+", name: "+a.getName());
			pm.deletePersistent(a);
			// have to do this manually
			// and this can be done within a txn, as they belongs to same Entity Group
			p1.getChildren().remove(a);
			pm.currentTransaction().commit();
			
			p1 = (Parent51)pm.getObjectById(Parent51.class, pid);
			set = p1.getChildren();
			size = set.size();
			System.out.println("child size: "+size);
			a = set.iterator().next();
			System.out.println("3) child id: "+a.getId()+", name: "+a.getName());
			
			
			System.out.println("end of testDeleteChild");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
    
    
    
    public void testDeleteChild51() {
		System.out.println("start of testDeleteChild51");
		// delete unowned side of unowned one-to-one relation
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			
			pm.currentTransaction().begin();
			Parent51 p1 = new Parent51();
			p1.setName("xxx");
			p1 = pm.makePersistent(p1);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Child51 a = new Child51();
			a.setName("x11");
			p1.setChild51(a);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid = p1.getId();
			String aid = a.getId();
			System.out.println("pid: "+p1.getId()+", aid: "+a.getId());
			assertTrue(true);

			p1 = (Parent51)pm.getObjectById(Parent51.class, pid);
			a = p1.getChild51();
			System.out.println("1) child id: "+a.getId()+", name: "+a.getName());
			
			
			pm.currentTransaction().begin();
			a = (Child51)pm.getObjectById(Child51.class, aid);
			System.out.println("2) child id: "+a.getId()+", name: "+a.getName());
			pm.deletePersistent(a);
			// have to do this manually
			// and this can be done within a txn, as they belongs to same Entity Group
			p1.setChild51(null);
			pm.currentTransaction().commit();
			
			p1 = (Parent51)pm.getObjectById(Parent51.class, pid);
			a = p1.getChild51();
			System.out.println("3) child id: "+a.getId()+", name: "+a.getName());
			
			System.out.println("end of testDeleteChild51");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
    
    public void testDeleteBothUniDirectionParents() {
		System.out.println("start of testDeleteBothUniDirectionParents");
		// delete unowned side of unowned one-to-one relation
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			// prepare
			pm.currentTransaction().begin();
			Parent51 p1 = new Parent51();
			p1.setName("xxx");
			p1 = pm.makePersistent(p1);
			
			pm.currentTransaction().commit();
			
			pm.currentTransaction().begin();
			Parent52 p2 = new Parent52();
			p2.setName("yyy");
			p2 = pm.makePersistent(p2);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Child5 a = new Child5();
			a.setName("x11");
			p1.getChildren().add(a);
//			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();

			pm.currentTransaction().begin();
			p2.getChildren2().add(a);
			pm.currentTransaction().commit();
			
			String pid1 = p1.getId();
			String pid2 = p2.getId();			
			String aid = a.getId();
			System.out.println("pid: "+p1.getId()+", pid2: "+p2.getId()+", aid: "+a.getId());
			assertTrue(true);


			
			System.out.println("end of testDeleteBothUniDirectionParents");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    }
    
}
