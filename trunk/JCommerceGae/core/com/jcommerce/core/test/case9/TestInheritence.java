package com.jcommerce.core.test.case9;

import javax.jdo.PersistenceManager;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.core.util.UUIDLongGenerator;

public class TestInheritence extends BaseDAOTestCase {
	@Override
	protected String getDbStorePath() {
		return "D:/temp/case9";
	}

	@Override
	protected boolean needCleanOnStartup() {
		return true;
	}
	
	public void testAdd() {
		
		System.out.println("start testAdd... ");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			pm.currentTransaction().begin();
			Parent9 p = new Parent9();
			p.setName("abc");
			p.setLongId(UUIDLongGenerator.newUUID());
			
			pm.makePersistent(p);
			pm.currentTransaction().commit();
			pm.close();
			
			pm = PMF.get().getPersistenceManager();
			String pid = p.getPkId();
			System.out.println("pid="+pid);
			
			Parent9 p2 = pm.getObjectById(Parent9.class, pid);
			Long lid = p2.getLongId();
			String name = p2.getName();
			
			System.out.println("name="+name+", lid="+lid);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		System.out.println("end testAdd... ");
		
	}
	
	public void testRandom() {
		
		for(int i=0;i<100;i++) {
			Long ld = UUIDLongGenerator.newUUID();
			System.out.println("ld="+ld);
		}
		
	}
}
