package com.jcommerce.core.service.impl;

import java.util.List;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CustomizedManager;
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



	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}
    
    


}
