package com.jcommerce.core.test.case5;

import java.util.List;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.core.test.case1.Person;


/**
 *  case that multiple parents with uni-direction owned relation to child 
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
    
    public void testQuery() {
    	System.out.println("start of testQuery");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			Parent51 p1 = new Parent51();
			p1.setName("xxx");
			p1 = pm.makePersistent(p1);

			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			p1 = new Parent51();
			p1.setName("yyy");
			p1 = pm.makePersistent(p1);

			pm.currentTransaction().commit();
			
			Query query = pm.newQuery(Parent51.class);
			List<Parent51> parents = (List<Parent51>)query.execute();
			for(Parent51 parent:parents) {
				System.out.println("id:"+parent.getPkId()+", name: "+parent.getName());
			}
			System.out.println("size="+parents.size());
			System.out.println("end of testQuery");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			pm.close();
		}
    }
    
    public void testAddTwoFieldofSameChildKind() {
		System.out.println("start of testAddTwoFieldofSameChildKind");
		
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
			
			Child51 c1 = new Child51();
			c1.setName("x11");
			p1.setChild51(c1);
			
			Child51 c2 = new Child51();
			c2.setName("x22");
			p1.setChild512(c2);
			
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid = p1.getPkId();
			String cid1 = c1.getPkId();
			String cid2 = c2.getPkId();
			System.out.println("pid: "+p1.getPkId()+", cid1: "+c1.getPkId()+", cid2: "+cid2);
			assertTrue(true);

			// verify add
			p1 = pm.getObjectById(Parent51.class, pid);
			c1 = p1.getChild51();
			c2 = p1.getChild512();
			System.out.println("c1: "+c1+", c2: "+c2);
			assertTrue(c1!=null && c2!=null);
			
			// cascade delete
			pm.currentTransaction().begin();
			pm.deletePersistent(p1);
			pm.currentTransaction().commit();
			// verify cascade delete
			try {
				System.out.println("search c1");
				c1 = pm.getObjectById(Child51.class, cid1);
			} catch (JDOObjectNotFoundException e) {
				System.out.println("c1: "+cid1+" cannot find");
				c1=null;
			}
			
			try {
				System.out.println("search c2");
				c2 = pm.getObjectById(Child51.class, cid2);
			} catch (JDOObjectNotFoundException e) {
				System.out.println("c2: "+cid2+" cannot find");
				c2=null;
			}
			
			// TODO why?
			// assertion fail
			assertTrue(c1==null && c2==null);

			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
			System.out.println("end of testAddTwoFieldofSameChildKind");
		}
		
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
			
			String pid = p1.getPkId();
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", aid: "+a.getPkId());
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

			String pid = p1.getPkId();
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", aid: "+a.getPkId());
			assertTrue(true);
			
			// delete
			pm.currentTransaction().begin();
			pm.deletePersistent(p1);
			pm.currentTransaction().commit();
			// verify delete
			try {
				a = pm.getObjectById(Child5.class, aid);
				System.out.println("id: "+a.getPkId()+", name: "+a.getName());
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
			
			String pid = p1.getPkId();
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", aid: "+a.getPkId());
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
			
			String pid2 = p2.getPkId();
			String aid2 = a2.getPkId();
			System.out.println("pid2: "+p2.getPkId()+", aid2: "+a2.getPkId());
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
			
			String pid = p1.getPkId();
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", aid: "+a.getPkId());
			assertTrue(true);

			// verify add
			p1 = (Parent51)pm.getObjectById(Parent51.class, pid);
			Set<Child5> set = p1.getChildren();
			int size = set.size();
			System.out.println("child size: "+size);
			a = set.iterator().next();
			System.out.println("1) child id: "+a.getPkId()+", name: "+a.getName());
			
			
			// delete
			pm.currentTransaction().begin();
			a = (Child5)pm.getObjectById(Child5.class, aid);
			System.out.println("2) child id: "+a.getPkId()+", name: "+a.getName());
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
			System.out.println("3) child id: "+a.getPkId()+", name: "+a.getName());
			
			
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
			
			String pid = p1.getPkId();
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", aid: "+a.getPkId());
			assertTrue(true);

			p1 = (Parent51)pm.getObjectById(Parent51.class, pid);
			a = p1.getChild51();
			System.out.println("1) child id: "+a.getPkId()+", name: "+a.getName());
			
			
			pm.currentTransaction().begin();
			a = (Child51)pm.getObjectById(Child51.class, aid);
			System.out.println("2) child id: "+a.getPkId()+", name: "+a.getName());
			pm.deletePersistent(a);
			// have to do this manually
			// and this can be done within a txn, as they belongs to same Entity Group
			p1.setChild51(null);
			pm.currentTransaction().commit();
			
			p1 = (Parent51)pm.getObjectById(Parent51.class, pid);
			a = p1.getChild51();
			System.out.println("3) child id: "+a.getPkId()+", name: "+a.getName());
			
			System.out.println("end of testDeleteChild51");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    	
    }
    
    public void testAttachMultiParents() {
		System.out.println("start of testAttachMultiParents");
		
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
			// Exception: org.datanucleus.exceptions.NucleusUserException: Detected attempt to establish !gmail.com:Parent52(2) as the parent 
			p2.getChildren2().add(a);
			pm.currentTransaction().commit();
			
			String pid1 = p1.getPkId();
			String pid2 = p2.getPkId();			
			String aid = a.getPkId();
			System.out.println("pid: "+p1.getPkId()+", pid2: "+p2.getPkId()+", aid: "+a.getPkId());
			assertTrue(true);


			
			System.out.println("end of testAttachMultiParents");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    }
    
    
    public void testReplaceChildAutoDeleteOld() {
		System.out.println("start of testReplaceChildAutoDeleteOld");
		
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
			Child51 c = new Child51();
			c.setName("x11");
			p1.setChild51(c);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid = p1.getPkId();
			String cid = c.getPkId();
			System.out.println("pid: "+pid+", cid: "+cid);
			
			// verify relation
			p1 = pm.getObjectById(Parent51.class, pid);
			c = p1.getChild51();
			System.out.println("cid: "+c.getPkId()+", cname:"+c.getName());
			assertTrue(cid.equals(c.getPkId()));

			// replace
			pm.currentTransaction().begin();
			Child51 c2 = new Child51();
			c2.setName("x11");
			p1.setChild51(c2);
			p1 = pm.makePersistent(p1);
			pm.currentTransaction().commit();
			
			String pid2 = p1.getPkId();
			String cid2 = c2.getPkId();
			System.out.println("pid2: "+pid2+", cid2: "+cid2);
			assertTrue(pid.equals(pid2));
			
			// verify new relation
			p1 = pm.getObjectById(Parent51.class, pid2);
			c2 = p1.getChild51();
			System.out.println("cid: "+c2.getPkId()+", cname:"+c2.getName());
			assertTrue(cid2.equals(c2.getPkId()));
			
			// verify old auto-deleted
			try {
				c = pm.getObjectById(Child51.class, cid);
				// This assertion will fail
				// TODO why?
				assertTrue(false);
			} catch (JDOObjectNotFoundException e) {
				assertTrue(true);
			}
			

			
			System.out.println("end of testReplaceChildAutoDeleteOld");
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
    }
}
