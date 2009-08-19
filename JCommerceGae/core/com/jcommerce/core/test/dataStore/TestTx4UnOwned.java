package com.jcommerce.core.test.dataStore;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDataStoreTestCase;

/*
 * To test tx behavior for a unonwned relationship
 * 
 */
public class TestTx4UnOwned extends BaseDataStoreTestCase {

	
    public boolean needCleanOnStartup() {
    	return true;
    }
	
	public void testAdd() {
		
		
		System.out.println("start of test1");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// test 
			pm.currentTransaction().begin();
			Parent p = new Parent();
			p.setName("xxx");
			
			Parent p1 = pm.makePersistent(p);
			System.out.println("id="+p1.getPkId());
			
			Child c = new Child();
			c.setName("xxx");
			c.setParentId(p.getPkId());
			Child c1 = pm.makePersistent(c);
			
			pm.currentTransaction().commit();
			System.out.println("id="+c1.getPkId());
			

			// query
//			Query query = pm.newQuery(Parent3.class);
//			List<Parent3> parent3s = (List<Parent3>)query.execute();
//			System.out.println("size="+parent3s.size());
//			for(Parent3 parent3:parent3s) {
//				System.out.println("id:"+parent3.getPkId()+", name: "+parent3.getName());
//				Set<Child3> set = parent3.getChild3s();
//				System.out.println("child size: "+set.size());
//				assertTrue(1==set.size());
//				
//				pm.currentTransaction().begin();
//				// NOTE!!! exception thrown here
//				pm.deletePersistent(parent3);
//				pm.currentTransaction().commit();
//				
//			}
			
			assertTrue(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
		
		
	}
	
	public void testDelete() {
		
		System.out.println("start of testDelete");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// test 
			pm.currentTransaction().begin();
			Parent p = new Parent();
			p.setName("xxx");
			
			Parent p1 = pm.makePersistent(p);
			System.out.println("id="+p1.getPkId());
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Child c = new Child();
			c.setName("xxx");
			c.setParentId(p.getPkId());
			Child c1 = pm.makePersistent(c);
			
			pm.currentTransaction().commit();
			System.out.println("id="+c1.getPkId());
			

			pm.currentTransaction().begin();
			pm.deletePersistent(p);
			pm.deletePersistent(c);
			
			pm.currentTransaction().commit();
			// query
//			Query query = pm.newQuery(Parent3.class);
//			List<Parent3> parent3s = (List<Parent3>)query.execute();
//			System.out.println("size="+parent3s.size());
//			for(Parent3 parent3:parent3s) {
//				System.out.println("id:"+parent3.getPkId()+", name: "+parent3.getName());
//				Set<Child3> set = parent3.getChild3s();
//				System.out.println("child size: "+set.size());
//				assertTrue(1==set.size());
//				
//				pm.currentTransaction().begin();
//				// NOTE!!! exception thrown here
//				pm.deletePersistent(parent3);
//				pm.currentTransaction().commit();
//				
//			}
			
			assertTrue(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
		System.out.println("end of testDelete");
		
	}
	
	
	@PersistenceCapable(identityType = IdentityType.APPLICATION)
	public static class Child {
	    @PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
		private String id;
			    
	    @Persistent
	    private String parentId;
	    
	    @Persistent
	    private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}



		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
	}
	
	
	@PersistenceCapable(identityType = IdentityType.APPLICATION)
	public static class Parent {
	    @PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
		private String id;
		
	    
	    @Persistent
	    private String name;

	    @Persistent
	    private Set<String> childIds = new HashSet<String>();
	    
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Set<String> getChildIds() {
			return childIds;
		}

		public void setChildIds(Set<String> childIds) {
			this.childIds = childIds;
		}

	    
	    
	    
	}
}
