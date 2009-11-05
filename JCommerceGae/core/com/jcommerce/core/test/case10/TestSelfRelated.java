package com.jcommerce.core.test.case10;

import javax.jdo.PersistenceManager;

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
			pm.currentTransaction().begin();
			MyRegion p = new MyRegion();
			p.setKeyName("p1");
			p.setName("abc");
			p.setLongId(UUIDLongGenerator.newUUID());
			
			p = pm.makePersistent(p);
			String pid = p.getPkId();
			System.out.println("pid="+pid);
			pm.currentTransaction().commit();
			pm.close();
			
			

			
			pm = PMF.get().getPersistenceManager();
			pm.currentTransaction().begin();
			MyRegion c = new MyRegion();
			c.setKeyName("c1");
			c.setName("xxx");
			p = pm.getObjectById(MyRegion.class, pid);
//			p.getChildren().add(c);
			pm.makePersistent(c);
			pm.makePersistent(p);
			pm.currentTransaction().commit();
			
			String cid = c.getPkId();
			System.out.println("cid="+cid);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		System.out.println("end testAdd... ");
	}
	
	
	
}
