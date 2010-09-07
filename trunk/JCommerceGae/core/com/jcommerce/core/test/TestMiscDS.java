package com.jcommerce.core.test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.impl.PMF;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.service.Order;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IComment;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


public class TestMiscDS extends BaseDAOTestCase {
	@Override
    public String getDbStorePath() {
    	return "D:/JCommerce/JCommerceGae/war";
//    	return "D:/JCommerce/test/abc.bin";
//    	return "D:/JCommerce/JCommerceGae/testdatastore";
    }
	
	@Override
	public boolean needCleanOnStartup() {
    	return false;
    }
	
	
	public void testQueryWithOrder() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            
            String id = "agpnY2xvdWRzaG9wcg4LEgVHb29kcyIDX2cwDA";
                
            String jdoql = "select from com.jcommerce.core.model.Comment"+
                " where  idValue == idValueParam "
                
                +" parameters  java.lang.String idValueParam "
            +" order by addTime desc ";
            Query query = pm.newQuery(jdoql);
            List<Object> paras = new ArrayList<Object>();
            paras.add(id);
            List result = (List)query.executeWithArray(paras.toArray());
            
            System.out.println("size of result: "+result.size());
            
            for(Object o : result) {
                Comment comment = (Comment)o;
                System.out.println("content="+comment.getContent()+", addTime="+comment.getAddTime());
            }
            
            IDefaultManager manager = getDefaultManager();
            Criteria c = new Criteria();
            c.addCondition(new Condition(IComment.ID_VALUE, Condition.EQUALS, id));
            c.addOrder(new Order(IComment.ADD_TIME, Order.DESCEND));
            int page = 1, size=5;
            List<Comment> comments = manager.getList(ModelNames.COMMENT, c, (page-1)*size, size);

            System.out.println("size of result: "+result.size());
            for(Object o : comments) {
                Comment comment = (Comment)o;
                System.out.println("content="+comment.getContent()+", addTime="+comment.getAddTime());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        } finally {
            pm.close();
        }
    }
	
	public void testQueryShopConfig() {
		IDefaultManager manager = getDefaultManager();
		List res = new ArrayList();
		String className = ShopConfig.class.getName();
		manager.getList(res, className, null, -1, -1);
		System.out.println("size: "+res.size());
		
		
	}
	
	public void testQueryGoodsAttr() {
	    
//	    String gtId = "agpnY2xvdWRzaG9wchMLEglHb29kc1R5cGUiBF9ndDEM";
//	    Criteria criteria = new Criteria();
//        Condition cond = new Condition();
//        cond.setField(AttributeForm.GOODS_TYPE);
//        cond.setOperator(Condition.EQUALS);
//        cond.setValue(gtId);
//        criteria.addCondition(cond);    
//        IDefaultManager manager = getDefaultManager();
//        List list = manager.getList(ModelNames.ATTRIBUTE, criteria);
//        
//        System.out.println("size: "+list.size());
//        
//        String s = ((Attribute)list.get(0)).getGoodsType().getPkId();
//        System.out.println("gtId="+s);
        
        
	    try {
	    String goodsId = "agpnY2xvdWRzaG9wcg4LEgVHb29kcyIDX2cwDA";
	       Criteria criteria = new Criteria();
//	        criteria.addCondition(new Condition(IGoodsAttr.GOODS, Condition.EQUALS,goodsId));
	        IDefaultManager manager = getDefaultManager();
	        List list = manager.getList(ModelNames.GOODSATTR, criteria);
	        
	        System.out.println("size: "+list.size());
	        for(Object o : list) {
	            GoodsAttr ga = (GoodsAttr)o;
	            Goods goods = ga.getGoods();
	            String gid = goods==null? "null" : goods.getPkId();
	            
	            System.out.println("ga="+ga.getKeyName()+", gtId="+gid);
	        }
	        
	        
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    
//        try {
//            String goodsId = "agpnY2xvdWRzaG9wcg4LEgVHb29kcyIDX2cwDA";
//               Criteria criteria = new Criteria();
////              criteria.addCondition(new Condition(IGoodsAttr.GOODS, Condition.EQUALS,goodsId));
//                IDefaultManager manager = getDefaultManager();
//                List list = manager.getList(ModelNames.GOODSGALLERY, criteria);
//                
//                System.out.println("size: "+list.size());
//                for(Object o : list) {
//                    GoodsGallery ga = (GoodsGallery)o;
//                    Goods goods = ga.getGoods();
//                    String gid = goods==null? "null" : goods.getPkId();
//                    
//                    System.out.println("ga="+ga.getKeyName()+", gtId="+gid);
//                }
//                
//                
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }	    
	    
//	        PersistenceManager pm = PMF.get().getPersistenceManager();
//	        try {
//	            String goodsId = "agpnY2xvdWRzaG9wcg4LEgVHb29kcyIDX2cwDA";   
//	            String jdoql = "SELECT FROM com.jcommerce.core.model.GoodsAttr"+
//                " WHERE goods == goodsParam "
//                
//                +" parameters  com.jcommerce.core.model.Goods goodsParam ";
//	            
//	            Query query = pm.newQuery(jdoql);
//	            List<Object> paras = new ArrayList<Object>();
//	            paras.add(goodsId);
//	            List result = (List)query.executeWithArray(paras.toArray());
//	            
//	            System.out.println("size of result: "+result.size());
//	        }catch (Exception e) {
//	            e.printStackTrace();
//	            assertTrue(false);
//	        } finally {
//	            pm.close();
//	        }
	    
	    
	}
	
	
	
	public void testDeleteBrand() {
		IDefaultManager manager = getDefaultManager();
		List<Brand> res = new ArrayList<Brand>();
		manager.getList(res, Brand.class.getName(), null, -1, -1);
		
		for(Brand brand:res) {
			String id = brand.getPkId();
			System.out.println("id="+id);
			Brand b = (Brand)manager.get(Brand.class.getName(), id);
			System.out.println("brand: "+b);
		}
		
	}
	
	public void testExtractKey() {
		System.out.println("start of testExtractKey");
		try {
			clearDS();
			// load data
			IDefaultManager mgr = getDefaultManager();

			String pid = null;
			Brand p = null;
			DSFile c1=null, c2=null;
			clearDS();
			
			String bkn = "bkn";
			String fkn1 = "fkn1";
			String fkn2 = "fkn2";
			
			
			p = new Brand();
			p.setKeyName(bkn);
			p.setBrandName("aaaaa");
			c1 = new DSFile();
			c1.setKeyName(fkn1);
			c1.setFileName("ff1");
			p.setLogoFile(c1);
			String fid1 = KeyFactory.keyToString(new KeyFactory.Builder("Brand",bkn).addChild("DSFile", fkn1).getKey());
			p.setLogoFileId(fid1);
			mgr.txattach(p);
			

			pid = p.getPkId();
			String c1id = c1.getPkId();


			// verify the relation
			p = (Brand)mgr.get(Brand.class.getName(), pid);
			DSFile c = p.getLogoFile();
			System.out.println("c1id: "+c1id+", cname: "+c.getKeyName());
			assertTrue(fid1.equals(c1id));

			
			// check the key
			Key key = KeyFactory.stringToKey(c1id);
			System.out.println("key: "+key);
			System.out.println("keyname: "+key.getName()+", kind: "+key.getKind());
			
			Key pkey = key.getParent();
			System.out.println("pkey: "+pkey);
			if(pkey!=null) {
				System.out.println("p keyname: "+pkey.getName()+", p kind: "+pkey.getKind());
				
			}
			
			Key ppkey = pkey.getParent();
			System.out.println("ppkey: "+ppkey);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("end of testExtractKey");
	}
}
