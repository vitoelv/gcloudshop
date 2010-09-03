package com.jcommerce.core.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.datastore.Blob;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsGallery;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.GoodsTypeForm;
import com.jcommerce.gwt.client.model.IGoods;


public class TestManagerImpl extends BaseDAOTestCase {
    protected void setUp() throws Exception {
    	System.out.println("TestManagerImpl setup");
    	super.setUp();
        log = LogFactory.getLog(TestManagerImpl.class);
    }
	public IDefaultManager getDefaultManager() {
		return (IDefaultManager)ctx.getBean("DefaultManager");
	}
	public CustomizedManager getCustomizedManager() {
		return (CustomizedManager)ctx.getBean("CustomizedManager");
	}
    
    public void testCreateAttribute() {
    	System.out.println("start of testCreateAttribute");
    	try {
    		
    		IDefaultManager manager = getDefaultManager();
    		
    		Map<String, Object> props1 = new HashMap<String, Object>();
    		props1.put(GoodsTypeForm.CAT_NAME, "abcde");
    		
        	ModelObject to1 = (ModelObject)GoodsType.class.newInstance();
            MyPropertyUtil.form2To(to1,props1);
    		
    		String gtid = manager.txadd(to1);
    		System.out.println("gt.getPkId(): "+gtid);
    		

    		
    		
    		
    		Map<String, Object> props2 = new HashMap<String, Object>();
    		props2.put(AttributeForm.GOODS_TYPE, gtid);
    		props2.put(AttributeForm.ATTR_NAME, "yyy");
    		
        	ModelObject to2 = (ModelObject)Attribute.class.newInstance();
            MyPropertyUtil.form2To(to2, props2);
    		
    		String id = manager.txadd(to2);
    		
    		System.out.println("id: "+id);
    		
    		
    		GoodsType gt = (GoodsType)manager.get(GoodsType.class.getName(), gtid);
    		Set attrs = gt.getAttributes();
    		
//    		System.out.println("attrs size: "+attrs.size());
    		// leon: loading of associated objs requires access within tx. see DefaultManagerImpl.get comments
    		System.out.println("attrs size: "+attrs.size()+", set: "+attrs);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		assertTrue(false);
    	}
    	
    	
    }
    
    
    public void testQueryAttribute() {
    	System.out.println("start of testQueryAttribute");
    	try {
    		
    		IDefaultManager manager = getDefaultManager();
    		Map<String, Object> props1 = new HashMap<String, Object>();
    		props1.put(GoodsTypeForm.CAT_NAME, "abcde");
    		
        	ModelObject to1 = (ModelObject)GoodsType.class.newInstance();
            MyPropertyUtil.form2To(to1,props1);
    		
    		String gtid = manager.txadd(to1);
    		System.out.println("gt.getPkId(): "+gtid);
    		
    		Map<String, Object> props2 = new HashMap<String, Object>();
    		props2.put(AttributeForm.GOODS_TYPE, gtid);
    		props2.put(AttributeForm.ATTR_NAME, "yyy");
    		
        	ModelObject to2 = (ModelObject)Attribute.class.newInstance();
            MyPropertyUtil.form2To(to2, props2);
    		
    		String id = manager.txadd(to2);
    		System.out.println("id: "+id);
    		
    		
    		// work 1
    		Criteria criteria = new Criteria();
    		Condition cond = new Condition();
    		cond.setField(AttributeForm.GOODS_TYPE);
    		cond.setOperator(Condition.EQUALS);
    		cond.setValue(gtid);
    		criteria.addCondition(cond);
    		
    		cond = new Condition();
    		cond.setField(AttributeForm.ATTR_NAME);
    		cond.setOperator(Condition.EQUALS);
    		cond.setValue("yyy");
    		criteria.addCondition(cond);
    		
    		List res = new ArrayList();
    		int length = manager.getList(res, Attribute.class.getName(), criteria, 0, 5);
    		int size = res.size();
    		System.out.println("Test case 1.... size: "+size+", length="+length);
    		
    		// validat
    		assertTrue(size==length && size==1);
    		
    		
    		// work 2
    		res = new ArrayList();
    		length = manager.getList(res, Attribute.class.getName(), null, 0, 5);
    		size = res.size();
    		System.out.println("Test case 2....  size: "+size+", length="+length);
    		
    		// validat 
    		assertTrue(size==length && size==1);
    		
    		// work 3
    		Attribute att = (Attribute)manager.get(Attribute.class.getName(), id);
    		GoodsType gt = att.getGoodsType();
    		System.out.println("Test case 3....  gt: "+gt);
    		
    		// validat
    		assertTrue(gt!=null && "abcde".equals(gt.getCatName()));
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		assertTrue(false);
    	}
    	
    	
    }
    
    
    public void testQueryGoodsTypeWithAttrCount() {
    	System.out.println("start of testQueryGoodsTypeWithAttrCount");
    	try {
    		// prepare
    		IDefaultManager manager = getDefaultManager();
    		Map<String, Object> props1 = new HashMap<String, Object>();
    		props1.put(GoodsTypeForm.CAT_NAME, "abcde");
    		
        	ModelObject to1 = (ModelObject)GoodsType.class.newInstance();
            MyPropertyUtil.form2To(to1,props1);
    		
    		String gtid = manager.txadd(to1);
    		System.out.println("gt.getPkId(): "+gtid);
    		
    		
    		Map<String, Object> props2 = new HashMap<String, Object>();
    		props2.put(AttributeForm.GOODS_TYPE, gtid);
    		props2.put(AttributeForm.ATTR_NAME, "yyy");
        	ModelObject to2 = (ModelObject)Attribute.class.newInstance();
            MyPropertyUtil.form2To(to2, props2);
    		String id = manager.txadd(to2);
    		System.out.println("id: "+id);
    		
    		Map<String, Object> props3 = new HashMap<String, Object>();
    		props3.put(AttributeForm.GOODS_TYPE, gtid);
    		props3.put(AttributeForm.ATTR_NAME, "yyy");
    		
        	ModelObject to3 = (ModelObject)Attribute.class.newInstance();
            MyPropertyUtil.form2To(to3, props3);
    		
    		id = manager.txadd(to3);
    		System.out.println("id: "+id);    		
    		
    		
    		
    		
    		// work
    		List<GoodsType> res = new ArrayList<GoodsType>();
    		CustomizedManager cm = getCustomizedManager();
    		int length = cm.getGoodsTypeListWithAttrCount(res, 0, 100, null);
    		
    		
    		int size = res.size();
    		long attCount = res.get(0).getAttrcount();
    		System.out.println("Test case 1.... size: "+size+", length="+length+", attcount="+attCount);
    		
    		
    		
    		// validat
    		assertTrue(size==length && size==1 && 2==attCount);
    		
    		

    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		assertTrue(false);
    	}
    	
    	
    }
    
    public void testUpdateAttribute() {
    	System.out.println("start of testUpdateAttribute");
    	try {
    		
    		
    		// prepare
    		IDefaultManager manager = getDefaultManager();
    		GoodsType gt1 = new GoodsType();
    		gt1.setCatName("xxx");
    		String gtid = manager.txadd(gt1);
    		System.out.println("gt.getPkId(): "+gtid);
    		
    		Map<String, Object> props2 = new HashMap<String, Object>();
    		props2.put(AttributeForm.GOODS_TYPE, gtid);
    		props2.put(AttributeForm.ATTR_NAME, "yyy");
        	ModelObject to2 = (ModelObject)Attribute.class.newInstance();
            MyPropertyUtil.form2To(to2, props2);
    		String id = manager.txadd(to2);
    		System.out.println("id: "+id);
    		
    		
    		// work
    		Map<String, Object> props3 = new HashMap<String, Object>();
    		props3.put(AttributeForm.GOODS_TYPE, gtid);
    		props3.put(AttributeForm.ATTR_NAME, "zzz");
    		
        	ModelObject to3 = (ModelObject)Attribute.class.newInstance();
            MyPropertyUtil.form2To( to3, props3);
            to3.setPkId(id);
            
            manager.txupdate(to3);
    		
            
            // verify
            Attribute att = (Attribute)manager.get(Attribute.class.getName(), id);
            String name = att.getAttrName();
            System.out.println("name: "+name);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		assertTrue(false);
    	}
    	
    	
    }
    

	public void testxx() throws Exception{
		GoodsType gt = GoodsType.class.newInstance();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("xxx", gt);
		
		Object obj = map.get("xxx");
		boolean res = obj instanceof ModelObject;
		System.out.println("res: "+res);
	}
    public void testCreateBrand() {
    	System.out.println("start of testCreateBrand");
    	try {
//    		IDefaultManager manager = getDefaultManager();
//    		Map<String, Object> props = new HashMap<String, Object>();
//    		props.put(IBrand.NAME, "xxx");
//    		
//        	ModelObject to1 = (ModelObject)Brand.class.newInstance();
//            MyPropertyUtil.form2To(to1, props);
//            
//    		String modelName = Brand.class.getName();
//    		System.out.println("modelName: "+modelName);
//    		String id = manager.add(to1);
//    		
//    		System.out.println("id: "+id);
    		
    		CustomizedManager manager = getCustomizedManager();
//    		BrandForm to = new BrandForm(ModelNames.BRAND);
//    		to.set(IBrand.NAME, "brand1");
//    		FileForm dsf = new FileForm();
//    		dsf.setContent("xxx".getBytes());
//    		dsf.setFileName("abc.jpg");
//    		dsf.setMimeType("IMG");
//    		to.set(IBrand.LOGO, to);
    		
    		Brand to = new Brand();
    		to.setBrandName("brand1");
    		DSFile logo = new DSFile();
    		logo.setContent(new Blob("xxx".getBytes()));
    		logo.setFileName("xyz.jpg");
//    		to.setLogo(logo);

    		String bid = manager.addBrand(to);
    		
    		
    		System.out.println("bid: "+bid);
    		
    		
    		
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		assertTrue(false);
    	}
    	
    }
    
    public void testUpdateGoodsType() {
    	System.out.println("start of testUpdateGoodsType");
    	try {
    		// prepare
    		IDefaultManager manager = getDefaultManager();
    		GoodsType to1 = new GoodsType();
    		to1.setCatName("xxx");
    		String gtid = manager.txadd(to1);
    		System.out.println("id: "+gtid);
    		
    		Map<String, Object> props2 = new HashMap<String, Object>();
    		props2.put(AttributeForm.GOODS_TYPE, gtid);
    		props2.put(AttributeForm.ATTR_NAME, "yyy");
        	ModelObject to2 = (ModelObject)Attribute.class.newInstance();
            MyPropertyUtil.form2To(to2, props2);
    		String id = manager.txadd(to2);
    		System.out.println("id: "+id);
    		
    		
    		// work
    		
    		Map<String, Object> props1 = new HashMap<String, Object>();
    		props1.put(GoodsTypeForm.CAT_NAME, "yyy");
    		props1.put(GoodsTypeForm.PK_ID, gtid);
    		
        	ModelObject to3 = (ModelObject)GoodsType.class.newInstance();
            MyPropertyUtil.form2To(to3, props1);
    		
    		manager.txupdate(to3);
    		
    		System.out.println("after update");

    		GoodsType gt2 = (GoodsType)manager.get(GoodsType.class.getName(), gtid);
    		String res = gt2.getCatName();
    		System.out.println("res: "+res);
    		
    		Set set = gt2.getAttributes();
    		System.out.println("size: "+set.size()+", set:"+set);
    		
    		assertTrue("yyy".equals(res));
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		assertTrue(false);
    	}
    }
    
    public void testDeleteGoodsTypeCascadeAttributes() {
    	System.out.println("start of testDeleteGoodsTypeCascadeAttributes");
    	try {
    		// prepare
    		IDefaultManager manager = getDefaultManager();
    		GoodsType to1 = new GoodsType();
    		to1.setCatName("xxx");
    		String gtid = manager.txadd(to1);
    		System.out.println("id: "+gtid);
    		
    		Map<String, Object> props2 = new HashMap<String, Object>();
    		props2.put(AttributeForm.GOODS_TYPE, gtid);
    		props2.put(AttributeForm.ATTR_NAME, "yyy");
        	ModelObject to2 = (ModelObject)Attribute.class.newInstance();
            MyPropertyUtil.form2To(to2, props2);
    		String id = manager.txadd(to2);
    		System.out.println("id: "+id);
    		
    		
    		// work
    		manager.txdelete(GoodsType.class.getName(), gtid);
    			

    		// validate
    		Criteria criteria = new Criteria();
    		Condition cond = new Condition();
    		cond.setField(AttributeForm.GOODS_TYPE);
    		cond.setOperator(Condition.EQUALS);
    		cond.setValue(gtid);
    		criteria.addCondition(cond);
    		
    		List res = manager.getList(Attribute.class.getName(), criteria);
    		int size = res.size();
    		System.out.println("size: "+size);
    		
    		assertTrue(0==size);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		assertTrue(false);
    	}
    }
    public void testUpdateBrand() {
    	System.out.println("start of testUpdateBrand");
		PersistenceManager pm = PMF.get().getPersistenceManager();
    	try {
    		// prepare
    		IDefaultManager manager = getDefaultManager();
    		Brand to1 = new Brand();
    		to1.setBrandName("xxx");
    		String bid = manager.txadd(to1);
    		System.out.println("id: "+bid);

    		
    		
    		// work
    		Brand b2 = new Brand();
    		b2.setPkId(bid);
    		b2.setBrandName("yyy");
    		manager.txupdate(b2);

    		System.out.println("after update");

    		Brand b3 = (Brand)manager.get(Brand.class.getName(), bid);
    		String res = b3.getBrandName();
    		System.out.println("res: "+res);
    		
    		assertTrue("yyy".equals(res));
    		
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		assertTrue(false);
    	} finally {
    		pm.close();
    	}
    }
    
    public void testAddGoods() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
    	clearDS();
    	Goods goods = new Goods();
    	goods.setIsBest(true);
    	goods.setIsHot(false);
    	goods.setGoodsName("mygoods");
    	GoodsGallery g = new GoodsGallery();
    	g.setImgDesc("abc");
    	DSFile f = new DSFile();
    	f.setContent(new Blob("xxx".getBytes()));
    	f.setFileName("myfile.txt");
    	g.setImageFile(f);
    	g.setImage("myfile.txt");
    	goods.getGalleries().add(g);
    	
    	CustomizedManager cm = getCustomizedManager();
    	cm.addGoods(goods);
    	
    	
        Criteria c1 = new Criteria();
        Condition cond1 = new Condition();
        cond1.setField(IGoods.IS_BEST);
        cond1.setOperator(Condition.EQUALS);
        cond1.setValue("true");
    	c1.addCondition(cond1);
        List<Goods> res =  (List<Goods>)getDefaultManager().getList(ModelNames.GOODS, c1);
    	System.out.println("size: "+res.size());
    	
    	String hdoql= "select from com.jcommerce.core.model.Goods  where  bestSold == bestSoldParam parameters  boolean bestSoldParam";
    	Query query = pm.newQuery(hdoql);
    	res = (List<Goods>)query.execute(new Boolean(true));
    	System.out.println("size: "+res.size());
    	res = (List<Goods>)query.execute("true");
    	System.out.println("size: "+res.size());
    	
//        Criteria c1 = new Criteria();
//        Condition cond1 = new Condition();
//        cond1.setField(IGoods.IS_HOT);
//        cond1.setOperator(Condition.EQUALS);
//        cond1.setValue("true");
//        c1.addCondition(cond1);
//        List<Goods> res =  (List<Goods>)getDefaultManager().getList(ModelNames.GOODS, c1);
//    	System.out.println("size: "+res.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
    }
}
