package com.jcommerce.core.test.case1;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.test.BaseDAOTestCase;
import com.jcommerce.core.util.UUIDHexGenerator;

public class TestManager extends BaseDAOTestCase {
    public boolean needCleanOnStartup() {
    	return true;
    }
	public void testQueryAddressByPerson() throws Exception{
		System.out.println("start of testQueryAddressByPerson");
		IDefaultManager manager = getDefaultManager();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// prepare
			Person p = new Person();
			p.setName("xxx");
			String pid = manager.txadd(p);
			System.out.println("pid="+pid+", p="+p);
			System.out.println("p.getId="+p.getPkId());
			
			Address a = new Address();
			a.setLoc("zzz");
//			a.setPerson(p);
			
			String pkn = KeyFactory.stringToKey(p.getPkId()).getName();
			System.out.println("pkn="+pkn);
			pkn = p.getKeyName();
			System.out.println("pkn="+pkn);
			String buildaid = KeyFactory.keyToString(
					new KeyFactory.Builder("Person", pkn).addChild("Address", UUIDHexGenerator.newUUID()).getKey()
					);

			System.out.println("buildaid="+buildaid);
			a.setPkId(buildaid);
			
			String aid = manager.txadd(a);
			System.out.println("aid="+aid+", a="+a);
			
//			String akn = a.getKeyName();
//			Key key = KeyFactory.createKey("Address", akn);
//			String rebuildPK = KeyFactory.keyToString(key);
//			System.out.println("akn="+akn);
//			System.out.println("rebuildPK="+rebuildPK);
			
//			a = new Address();
//			a.setLoc("eee");
//			a.setPerson(p);
//			aid = manager.add(a);
//			System.out.println("aid="+aid+", a="+a);
			
			// work

			
			// select from Attribute  where  goodsType == goodsTypeParam parameters  GoodsType goodsTypeParam
			String jdoql = "select from com.jcommerce.core.test.case1.Address where person == personParam parameters com.jcommerce.core.test.case1.Person personParam";
			Query q1 = pm.newQuery(jdoql);
			List<Address> res = (List<Address>)q1.execute(pid);
			int size = res.size();
			System.out.println("size: "+size);

			// validate
			assertTrue(1==size);
			
			
			// work 2
			res = new ArrayList<Address>();
			manager.getListOfChild(res, Address.class.getName(), "person", pid);
			size = res.size();
			System.out.println("work2: size: "+size);
			// validate 2
			assertTrue(1==size);

			// cascalate delete
			manager.txdelete(Person.class.getName(), pid);
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
	
	
	
	public void testQueryAddressByPerson2() throws Exception{
		System.out.println("start of testQueryAddressByPerson2");
		IDefaultManager manager = getDefaultManager();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// prepare
			Person p = new Person();
			p.setName("xxx");
			
			Address a = new Address();
			a.setLoc("zzz");
//			a.setPerson(p);
			a.setKeyName(UUIDHexGenerator.newUUID());
			
			p.getAddresses().add(a);
			
			String pid = manager.txadd(p);

			System.out.println("pid="+pid+", p="+p);

			
			String aid = a.getPkId();
			System.out.println("aid="+aid+", a="+a);
			
//			a = new Address();
//			a.setLoc("eee");
//			a.setPerson(p);
//			aid = manager.add(a);
//			System.out.println("aid="+aid+", a="+a);
			
			// work

			
			// select from Attribute  where  goodsType == goodsTypeParam parameters  GoodsType goodsTypeParam
			String jdoql = "select from com.jcommerce.core.test.case1.Address where person == personParam parameters com.jcommerce.core.test.case1.Person personParam";
			Query q1 = pm.newQuery(jdoql);
			List<Address> res = (List<Address>)q1.execute(pid);
			int size = res.size();
			System.out.println("size: "+size);

			// validate
			assertTrue(1==size);
			
			a = res.get(0);
			p = a.getPerson();
			System.out.println("p: "+p);
			assertTrue(p!=null && "xxx".equals(p.getName()));
			
			// cascalate delete
			manager.txdelete(Person.class.getName(), pid);
			
			// work 2
//			res = new ArrayList<Address>();
//			manager.getListOfChild(res, Address.class.getName(), "person", pid);
//			size = res.size();
//			System.out.println("work2: size: "+size);
//			// validate 2
//			assertTrue(2==size);

		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
	public void testQueryAddressByPerson3() throws Exception{
		System.out.println("start of testQueryAddressByPerson3");
		CustomizedManager cm = super.getCustomizedManager();
		IDefaultManager manager = super.getDefaultManager();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// prepare
			Person p = new Person();
			p.setName("xxx");
			
			Address a = new Address();
			a.setLoc("zzz");
						
			String pid = null;//cm.addPerson(p, a);

			System.out.println("pid="+pid+", p="+p);

			String aid = a.getPkId();
			System.out.println("aid="+aid+", a="+a);
			
//			a = new Address();
//			a.setLoc("eee");
//			a.setPerson(p);
//			aid = manager.add(a);
//			System.out.println("aid="+aid+", a="+a);
			
			// work

			
			// select from Attribute  where  goodsType == goodsTypeParam parameters  GoodsType goodsTypeParam
			String jdoql = "select from com.jcommerce.core.test.case1.Address where person == personParam parameters com.jcommerce.core.test.case1.Person personParam";
			Query q1 = pm.newQuery(jdoql);
			List<Address> res = (List<Address>)q1.execute(pid);
			int size = res.size();
			System.out.println("size: "+size);

			// validate
			assertTrue(1==size);
			
			a = res.get(0);
			p = a.getPerson();
			System.out.println("p: "+p);
			assertTrue(p!=null && "xxx".equals(p.getName()));
			
			// cascalate delete
			manager.txdelete(Person.class.getName(), pid);
			
			// work 2
//			res = new ArrayList<Address>();
//			manager.getListOfChild(res, Address.class.getName(), "person", pid);
//			size = res.size();
//			System.out.println("work2: size: "+size);
//			// validate 2
//			assertTrue(2==size);

		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
}
