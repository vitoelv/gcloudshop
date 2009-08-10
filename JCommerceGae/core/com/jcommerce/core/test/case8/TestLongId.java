package com.jcommerce.core.test.case8;

import java.util.Set;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.test.BaseDAOTestCase;


public class TestLongId extends BaseDAOTestCase {

	@Override
	protected String getDbStorePath() {
		return "D:/temp/case8";
	}

	@Override
	protected boolean needCleanOnStartup() {
		return true;
	}
	
	public void testLoad() {
		System.out.println("start testLoad... ");
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			String pn = "abc";
			String cn = "xxx";
			// prepare
			pm.currentTransaction().begin();
			Parent8 p = new Parent8();
			p.setName(pn);
			Child8 c1 = new Child8();
			c1.setName(cn);
			p.getChildren().add(c1);
			pm.makePersistent(p);
			pm.currentTransaction().commit();
			
			String pid = p.getId();
			Long pkid = p.getKeyId();
			String cid = c1.getId();
			Long ckid = c1.getKeyId();
			System.out.println("pid="+pid+", pkid="+pkid);
			System.out.println("cid="+cid+", ckid="+ckid);
			p = pm.getObjectById(Parent8.class, pid);
			c1 = pm.getObjectById(Child8.class, cid);
			
			// export
			
			// delete
			clearDS();
			
			
			// import
			pm.currentTransaction().begin();
			Parent8 p2 = new Parent8();
			p2.setKeyId(pkid);
			p2.setName(pn);
			String pid2 = KeyFactory.keyToString(
					new KeyFactory.Builder(Parent8.class.getSimpleName(), pkid).getKey());
			p2.setId(pid2);
			pm.makePersistent(p2);

			
			Child8 c2 = new Child8();
			c2.setKeyId(ckid);
			c2.setName(cn);
			String cid2 = KeyFactory.keyToString(
					new KeyFactory.Builder(Parent8.class.getSimpleName(), pkid)
					.addChild(Child8.class.getSimpleName(), ckid).getKey());
//			c2.setId(cid2);
			
			pm.makePersistent(c2);
			pm.currentTransaction().commit();
			
			Long pkid2 = p2.getKeyId();
			Long ckid2 = c2.getKeyId();
			System.out.println("pid2="+pid2+", pkid2="+pkid2);
			System.out.println("cid2="+cid2+", ckid2="+ckid2);
			assertTrue(pkid.equals(pkid2) && ckid.equals(ckid2));
			
			pid2 = p2.getId();
			cid2 = c2.getId();
			System.out.println("pid2="+pid2+", pkid2="+pkid2);
			System.out.println("cid2="+cid2+", ckid2="+ckid2);
			
			// verify
			Parent8 p3 = pm.getObjectById(Parent8.class, pid2);
			Child8 c3 = pm.getObjectById(Child8.class, cid2);
			
			Set<Child8> set = p3.getChildren();
			int size = set.size();
			System.out.println("size: "+size);
			
			assertTrue(size==1);
			if(size>0) {
				Child8 c4 = set.iterator().next();
				assertTrue(c4.getName().equals(cn));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("end testLoad...");
		
		
		
		
	}
	
	
	
}
