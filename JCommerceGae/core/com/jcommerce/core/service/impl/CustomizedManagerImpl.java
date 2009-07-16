package com.jcommerce.core.service.impl;

import java.util.List;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.test.case1.Address;
import com.jcommerce.core.test.case1.Person;
import com.jcommerce.gwt.client.form.AttributeForm;

public class CustomizedManagerImpl extends DefaultManagerImpl implements CustomizedManager {

    public CustomizedManagerImpl() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private DAO dao;
    
    public int getGoodsTypeListWithAttrCount(List<GoodsType> resultSet, int firstRow,
            int maxRow, Criteria criteria) {
    	try {
    		System.out.println("CustomizedManagerImpl.getGoodsTypeListWithAttrCount()");
        
    		List<GoodsType> res = dao.getList(GoodsType.class.getName(), criteria, firstRow, maxRow);
    		for(GoodsType gt:res) {
        		String gtid = gt.getId();
    			Criteria c2 = new Criteria();
        		Condition cond = new Condition();
        		cond.setField(AttributeForm.GOODSTYPE);
        		cond.setOperator(Condition.EQUALS);
        		cond.setValue(gtid);
        		c2.addCondition(cond);
        		
    			int attcount = dao.getCount(Attribute.class.getName(), c2);
    			gt.setAttrcount(attcount);
    		}
    		
    		resultSet.addAll(res);
    		
    		return getDao().getCount(GoodsType.class.getName(), criteria);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }

    
    public String addBrand(Brand to) {
    	try {
    		populateIdWithPo(to);
			String res = dao.add(to);
			
//			DSFile logo = to.getLogo();
//			String id = logo.getId();
//			System.out.println("id: "+id);

			// NOTE: This does not work
			// throw exception: 
			// can't update the same entity twice in a transaction or operation
//			to.setLogoFileId(id);
			return res;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    public String addGoods(Goods to, List<Gallery> galleries) {
    	try {
    		populateIdWithPo(to);
			String res = dao.add(to);
			
			for(Gallery gallery:galleries) {
				gallery.setGoods(to);
				dao.add(gallery);
			}
			
			
			return res;
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	
    	
    }
    public String updateGoods(Goods to, List<Gallery> galleries) {
    	try {
    		populateIdWithPo(to);
			String res = dao.add(to);
			for(Gallery gallery:galleries) {
				gallery.setGoods(to);
				if(gallery.getId()!=null) {
					dao.update(gallery);
				} else {
					dao.add(gallery);
				}
			}
			return res;
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	
    	
    }
    // for test case1 
    public String addPerson(Person to, Address a) {
    	try {
    		populateIdWithPo(to);
			String res = dao.add(to);
			
			a.setPerson(to);
			dao.add(a);
			
			return res;
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	
    	
    }

	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}
    
    


}
