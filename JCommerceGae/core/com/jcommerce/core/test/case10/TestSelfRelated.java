package com.jcommerce.core.test.case10;

import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.core.util.UUIDLongGenerator;

public class TestSelfRelated extends BaseDAOTestCase {
	@Override
	protected String getDbStorePath() {
		return "D:/temp/case10";
	}

	@Override
	protected boolean needCleanOnStartup() {
		return true;
	}
	
	public void testCascadeDelete() {
		System.out.println("start testAdd... ");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			// prepare
			pm.currentTransaction().begin();
			MyRegion p = new MyRegion();
			p.setKeyName("p1");
			p.setName("abc");
			p.setLongId(UUIDLongGenerator.newUUID());
			
			p = pm.makePersistent(p);
			pm.currentTransaction().commit();
			pm.close();
			String pid = p.getPkId();
			System.out.println("pid="+pid);			
			

			
			pm = PMF.get().getPersistenceManager();
			pm.currentTransaction().begin();
			MyRegion c = new MyRegion();
			c.setKeyName("c1");
			c.setName("xxx");
			p = pm.getObjectById(MyRegion.class, pid);
//			p.getChildren().add(c);
//			pm.makePersistent(p);
			c.setMyparent(p);
			pm.makePersistent(c);

			// will fail at this point
			// javax.jdo.JDOFatalUserException: Field com.jcommerce.core.test.case10.MyRegion.myparent should be able to provide a reference to its parent but the entity does not have a parent.  Did you perhaps try to establish an instance of com.jcommerce.core.test.case10.MyRegion as the child of an instance of com.jcommerce.core.test.case10.MyRegion after the child had already been persisted?
			pm.currentTransaction().commit();
			
			String cid = c.getPkId();
			System.out.println("cid="+cid);
			
			
			// verify prepare
			pm = PMF.get().getPersistenceManager();
			p = pm.getObjectById(MyRegion.class, pid);
			Set set = p.getChildren();
			System.out.println("size of children: "+set.size());
			assertTrue(1==set.size());
			
			
			// cascade delete
			pm = PMF.get().getPersistenceManager();
			pm.currentTransaction().begin();
			p = pm.getObjectById(MyRegion.class, pid);
			pm.deletePersistent(p);
			pm.currentTransaction().commit();
			
			
			// verify cascade delete
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(MyRegion.class);
			List<MyRegion> res = (List<MyRegion>)q.execute();
			System.out.println("size of remained: "+res.size());
			assertTrue(0==res.size());
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		System.out.println("end testAdd... ");
	}
	
	
	
}
