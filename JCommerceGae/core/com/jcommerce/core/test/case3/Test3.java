package com.jcommerce.core.test.case3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDataStoreTestCase;

public class Test3 extends BaseDataStoreTestCase {
    public boolean needCleanOnStartup() {
    	return true;
    }
    
	public void test1() {
		// this is the case submitted to code.google about cascadingly delete issue
		System.out.println("start of test1");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			
			// prepare 
			pm.currentTransaction().begin();
			Parent3 p = new Parent3();
			p.setKeyName("p1");
			p.setName("xxx");
			
			Parent3 p1 = pm.makePersistent(p);
			
			pm.currentTransaction().commit();
			System.out.println("id="+p1.getId());
			
			pm.currentTransaction().begin();
			Child3 c = new Child3();
			c.setKeyName("c1");
			c.setName("xxx");
			c.setParent3(p);
			Child3 c1 = pm.makePersistent(c);
			
			pm.currentTransaction().commit();
			System.out.println("id="+c1.getId());
			

			// query
			Query query = pm.newQuery(Parent3.class);
			List<Parent3> parent3s = (List<Parent3>)query.execute();
			System.out.println("size="+parent3s.size());
			for(Parent3 parent3:parent3s) {
				System.out.println("id:"+parent3.getId()+", name: "+parent3.getName());
				Set<Child3> set = parent3.getChild3s();
				System.out.println("child size: "+set.size());
				assertTrue(1==set.size());
				
				pm.currentTransaction().begin();
				// NOTE!!! exception thrown here
				pm.deletePersistent(parent3);
				pm.currentTransaction().commit();
				
			}


			

			
			assertTrue(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
		
		
	}
	
	public void test2() {
		// workaround of cascadingly delete of parent/child
		
		System.out.println("start of test2");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			
			// prepare 
			pm.currentTransaction().begin();
			Parent3 p = new Parent3();
			p.setKeyName("p1");
			p.setName("xxx");
			
			Parent3 p1 = pm.makePersistent(p);
			
			pm.currentTransaction().commit();
			System.out.println("id="+p1.getId());
			
			pm.currentTransaction().begin();
			Child3 c = new Child3();
			c.setKeyName("c1");
			c.setName("xxx");
			c.setParent3(p);
			Child3 c1 = pm.makePersistent(c);
			
			pm.currentTransaction().commit();
			System.out.println("id="+c1.getId());
			

			// query
			Query query = pm.newQuery(Parent3.class);
			List<Parent3> parent3s = (List<Parent3>)query.execute();
			System.out.println("size="+parent3s.size());
			for(Parent3 parent3:parent3s) {
				System.out.println("id:"+parent3.getId()+", name: "+parent3.getName());
				Set<Child3> set = parent3.getChild3s();
				System.out.println("child size: "+set.size());
				assertTrue(1==set.size());
				
				pm.currentTransaction().begin();
				Child3 c2 = set.iterator().next();
				pm.deletePersistent(c2);
				pm.deletePersistent(parent3);
				pm.currentTransaction().commit();
				
			}


			

			
			assertTrue(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
		
		
	}
	@PersistenceCapable(identityType = IdentityType.APPLICATION)
	public static class Child3 {
	    @PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
		private String id;
		
	    @Persistent
	    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	    private String keyName;
	    
	    @Persistent
	    private Parent3 parent3;
	    
	    @Persistent
	    private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getKeyName() {
			return keyName;
		}

		public void setKeyName(String keyName) {
			this.keyName = keyName;
		}

		public Parent3 getParent3() {
			return parent3;
		}

		public void setParent3(Parent3 parent3) {
			this.parent3 = parent3;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	
	@PersistenceCapable(identityType = IdentityType.APPLICATION)
	public static class Parent3 {
	    @PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
		private String id;
		
	    @Persistent
	    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	    private String keyName;
	    
	    @Persistent
	    private String name;

	    @Persistent(mappedBy="parent3")
	    private Set<Child3> child3s = new HashSet<Child3>();
	    
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getKeyName() {
			return keyName;
		}

		public void setKeyName(String keyName) {
			this.keyName = keyName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Set<Child3> getChild3s() {
			return child3s;
		}

		public void setChild3s(Set<Child3> child3s) {
			this.child3s = child3s;
		}
	    
	    
	    
	}
}
