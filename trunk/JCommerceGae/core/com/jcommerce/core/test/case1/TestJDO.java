package com.jcommerce.core.test.case1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.test.BaseDAOTestCase;

public class TestJDO extends BaseDAOTestCase {
    protected String getDbStorePath() {
    	return "D:/logs/Datastore1";
    }
    protected boolean needCleanOnStartup() {
    	return true;
    }
    
    
	public void testAddPerson() throws Exception{
		System.out.println("start of testAddPerson");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			Person p = new Person();
//			p.setId("_"+UUIDHexGenerator.newUUID());
			p.setName("xxx");
			
			Person p1 = pm.makePersistent(p);
			
			pm.currentTransaction().commit();
			System.out.println("id="+p1.getId());
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
	
	public void testAddPersonWithSetString() throws Exception{
		System.out.println("start of testAddPersonWithSetString");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			clearDS();
			pm.currentTransaction().begin();
			Person p = new Person();
			p.setKeyName("p1");
			p.setName("xxx");
			p.getAliasList().add("123");
			p.getAliasList().add("456");
			Person p1 = pm.makePersistent(p);
			System.out.println("id="+p1.getId());
			
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			
			p = new Person();
			p.setKeyName("p2");
			p.setName("yyy");
			p.getAliasList().add("123yyy");
			p.getAliasList().add("456yyy");
			p1 = pm.makePersistent(p);
			System.out.println("id="+p1.getId());
			pm.currentTransaction().commit();
			
			
			
//			pm.currentTransaction().begin();
			Query query = pm.newQuery(Person.class);
			List<Person> persons = (List<Person>)query.execute();
			for(Person person:persons) {
				System.out.println("id:"+person.getId()+", name: "+person.getName());
			}
//			pm.currentTransaction().commit();
			System.out.println("size="+persons.size());
			
			
			query = pm.newQuery("select from com.jcommerce.core.test.case1.Person");
			persons = (List<Person>)query.execute();
			for(Person person:persons) {
				System.out.println("id:"+person.getId()+", name: "+person.getName());
			}
//			pm.currentTransaction().commit();
			System.out.println("size="+persons.size());
			
			
			pm.currentTransaction().begin();
			query = pm.newQuery(Person.class);
			query.setResult("count(this)");
			Integer res = (Integer)query.execute();
			System.out.println("count="+res);
			pm.currentTransaction().commit();
			
			IDefaultManager manager = getDefaultManager();
			persons = new ArrayList<Person>();
			manager.getList(persons, Person.class.getName(), null, -1, -1);
			System.out.println("size="+persons.size());
			
			System.out.println("start of testAddPersonWithSetString");
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
	
	public void testAddPersonAndAddress() throws Exception{
		System.out.println("start of testAddPersonAndAddress");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			Person p = new Person();
//			String pid = "_"+UUIDHexGenerator.newUUID();
//			p.setId(pid);
			p.setName("xxx");
			
			pm.makePersistent(p);
			String pid = p.getId();
			
			pm.currentTransaction().commit();
			System.out.println("pid="+pid);
			
			
			
			pm.currentTransaction().begin();
			Address a = new Address();
			a.setLoc("zzz");
			
			// relationship
			p.getAddresses().add(a);
			// reverse relationship
			// a.setPerson(p);
			pm.makePersistent(p);
			pm.currentTransaction().commit();
			
			pm.currentTransaction().begin();
			Person p1 = (Person)pm.getObjectById(Person.class, pid);
			Set addresses = p1.getAddresses();
			System.out.println("size :"+addresses.size());
			Address a1 = (Address)addresses.iterator().next();
			String aid = a1.getId();
			System.out.println("aid: "+aid);
			pm.currentTransaction().commit();
			
			
			pm.currentTransaction().begin();
			Address a2 = (Address)pm.getObjectById(Address.class, aid);
			Person p2 = a2.getPerson();
			System.out.println("p2: "+p2.getName());
			pm.currentTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
	
	
	public void testAddPersonAndAddressReverse() throws Exception{
		System.out.println("start of testAddPersonAndAddressReverse");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			Person p = new Person();
//			String pid = "_"+UUIDHexGenerator.newUUID();
//			p.setId(pid);
			p.setName("xxx");
			
			pm.makePersistent(p);
			String pid = p.getId();
			
			pm.currentTransaction().commit();
			System.out.println("pid="+pid+", p="+p);
			
			
			
			pm.currentTransaction().begin();
			Address a = new Address();
			a.setLoc("zzz");
//			Person p3 = pm.getObjectById(Person.class, pid);
//			System.out.println("p3="+p3);
			// reverse relationship
			 a.setPerson(p);
			pm.makePersistent(a);
			pm.currentTransaction().commit();
			
			pm.currentTransaction().begin();
			Person p1 = (Person)pm.getObjectById(Person.class, pid);
			Set addresses = p1.getAddresses();
			System.out.println("size :"+addresses.size());
			Address a1 = (Address)addresses.iterator().next();
			String aid = a1.getId();
			System.out.println("aid: "+aid);
			pm.currentTransaction().commit();
			
			
			pm.currentTransaction().begin();
			Address a2 = (Address)pm.getObjectById(Address.class, aid);
			Person p2 = a2.getPerson();
			System.out.println("p2: "+p2.getName());
			pm.currentTransaction().commit();
			
			pm.close();
			System.out.println("size after tx:"+p1.getAddresses().size());
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
	
	
	public void testQueryAddressByPerson() throws Exception{
		System.out.println("start of testQueryAddressByPerson");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// prepare
			pm.currentTransaction().begin();
			Person p = new Person();
			p.setName("xxx");
			pm.makePersistent(p);
			String pid = p.getId();
			pm.currentTransaction().commit();
			System.out.println("pid="+pid+", p="+p);

			pm.currentTransaction().begin();
			Address a = new Address();
			a.setLoc("zzz");
			a.setPerson(p);
			pm.makePersistent(a);
			pm.currentTransaction().commit();
			
			// work
			pm.currentTransaction().begin();

			// select from Attribute  where  goodsType == goodsTypeParam parameters  GoodsType goodsTypeParam
			String jdoql = "select from com.jcommerce.core.test.case1.Address where person == personParam parameters com.jcommerce.core.test.case1.Person personParam";
			Query q1 = pm.newQuery(jdoql);
			List<Address> res = (List<Address>)q1.execute(pid);

			
//			Query q1 = pm.newQuery(Address.class);
//			q1.setFilter("person == personPara && loc == locPara");
//			q1.declareParameters("Person personPara, String locPara");
//			List<Address> res = (List<Address>)q1.execute(pid, "zzz11");
			
			int size = res.size();
			System.out.println("size: "+size);
			pm.currentTransaction().commit();
			
			// validate
			assertTrue(1==size);

		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
	
	public void testAddCategory() {
		System.out.println("start of testAddCategory");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {

//			pm.currentTransaction().begin();
//			Category p = new Category();
//			p.setName("yyy");
//			pm.makePersistent(p);
//			String pid = p.getId();
//			System.out.println("pid="+pid);
//			pm.currentTransaction().commit();
//
//			
//			pm.currentTransaction().begin();
//			Category c = new Category();
//			c.setName("xxx");
//			
//			p.addChild(c);
////			c.setParent(p);
//
//			pm.makePersistent(p);
//			String cid = c.getId();
//			System.out.println("cid="+cid);	
//			
//			pm.currentTransaction().commit();
//			
//
//			// validation
//			pm.currentTransaction().begin();
//			Category p2 = pm.getObjectById(Category.class, pid);
//			Category c2 = (Category)p2.getChildren().iterator().next();
//			String c2Name = c2.getName();
//			System.out.println("c2Name: "+c2Name);
//			pm.currentTransaction().commit();
//			
//			assertTrue("xxx".equals(c2Name));
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
	
	public void testAddCategoryReverse() {
		System.out.println("start of testAddCategoryReverse");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {

//			pm.currentTransaction().begin();
//			Category c = new Category();
//			c.setName("xxx");
//			pm.makePersistent(c);
//			String cid = c.getId();
//			System.out.println("cid="+cid);			
//			pm.currentTransaction().commit();
//			
//			
//			pm.currentTransaction().begin();
//			Category p = new Category();
//			p.setName("yyy");
//			p.getChildren().add(c);
//			pm.makePersistent(p);
//			String pid = p.getId();
//			System.out.println("pid="+pid);
//			pm.currentTransaction().commit();
//
//			
//
//			
//
//			// validation
//			pm.currentTransaction().begin();
//			Category c2 = pm.getObjectById(Category.class, cid);
//			Category p2 = c2.getParent();
//			String p2Name = p2.getName();
//			System.out.println("p2Name: "+p2Name);
//			Category c3 = (Category)p2.getChildren().iterator().next();
//			String c3Name = c3.getName();
//			System.out.println("c3Name: "+c3Name);
//			pm.currentTransaction().commit();
//			
//			assertTrue("yyy".equals(p2Name) && "xxx".equals(c3Name));
			
		}catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}finally {
			pm.close();
		}
	}
}
