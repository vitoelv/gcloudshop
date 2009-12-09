package com.jcommerce.core.test.case11;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.test.BaseDAOTestCase;

public class TestSearchWithInandOr extends BaseDAOTestCase {
	@Override
	protected String getDbStorePath() {
		return "D:/temp/case11";
	}

	@Override
	protected boolean needCleanOnStartup() {
		return true;
	}
	
	public void testSearchWithOr() {
		System.out.println("start testSearchWithOr... ");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			String p1id=null, p2id=null;
			// prepare
			pm.currentTransaction().begin();
			Person p1 = new Person();
			p1.setName("p1");
			pm.makePersistent(p1);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Person p2 = new Person();
			p2.setName("p2");
			pm.makePersistent(p2);
			
			pm.currentTransaction().commit();
			
			p1id = p1.getPkId();
			p2id = p2.getPkId();
			System.out.println("p1id: "+p1id);
			System.out.println("p2id: "+p2id);
			
			// search
			String jdoql = "select from "+Person.class.getName()+" where pkId == p1id || pkId == p2id parameters String p1id, String p2id";
			Query q = pm.newQuery(jdoql);
			List<Person> res = (List<Person>)q.execute(p1id, p2id);
			System.out.println("size of res: "+res.size());
			assertTrue(2==res.size());
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		System.out.println("end testSearchWithOr... ");
	}
	
	
	
	public void testDeleteAll() {
		System.out.println("start testDeleteAll... ");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try {
			String p1id=null, p2id=null;
			// prepare
			pm.currentTransaction().begin();
			Person p1 = new Person();
			p1.setName("p1");
			pm.makePersistent(p1);
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			Person p2 = new Person();
			p2.setName("p2");
			pm.makePersistent(p2);
			
			pm.currentTransaction().commit();
			
			p1id = p1.getPkId();
			p2id = p2.getPkId();
			System.out.println("p1id: "+p1id);
			System.out.println("p2id: "+p2id);
			
			// deleteAll
			Collection<ModelObject> res = getDefaultManager().getList(Person.class.getName(), null);
			getDefaultManager().txdeleteall(res);
			System.out.println("finished");
			
			res = getDefaultManager().getList(Person.class.getName(), null);
			System.out.println("size after deleteall: "+res.size());
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		System.out.println("end testSearchWithOr... ");
	}
	
	
}
