/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;

public class DefaultManagerImpl implements IDefaultManager {
    private DAO dao;
    
    
	public ModelObject get (String modelName, String id) {
		ModelObject obj = dao.get(modelName, id);
//		if(obj instanceof GoodsType) {
//			GoodsType gt = (GoodsType)obj;
//			Set set = gt.getAttributes();
//			
//			// leon: size() won't cause loading
//			System.out.println("size 3: "+set.size()+", set: "+set);
//			debug("size 3: "+set.size());
//		}
//		
//		if(obj instanceof Attribute) {
//			Attribute att = (Attribute)obj;
//			GoodsType gt = att.getGoodsType();
//			System.out.println("gt: "+gt);
//		}
		return obj;
	}
	
	public boolean update (ModelObject to) {
		try {
			populateIdWithPo(to);
			return dao.update(to);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	
    public void setDao(DAO dao) {
        this.dao = dao;
    }
    
    public String attach (ModelObject to) {
    	try {
			String res = dao.attach(to);
			return res;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    public String add (ModelObject to) {
    	try {
    		populateIdWithPo(to);
			String res = dao.add(to);

			return res;
    		
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    public boolean delete (String modelName, String id) {
    	
    	boolean res = dao.delete(modelName, id);
    	
    	return res;
    }
    public List getList(String modelName, Criteria criteria) {
    	int totalLength = 0;
//        String hql = getHql(modelName, criteria);   
        String hql = "";
        System.out.println("modelName="+modelName+", hql="+hql);
        
        return dao.getList(modelName, criteria, -1, -1);

    }
    public int getList(List res, String modelName, Criteria criteria, int firstRow, int maxRow) {
    	int totalLength = 0;
//        String hql = getHql(modelName, criteria); 
    	String hql = "";
        List list = null;
        System.out.println("modelName="+modelName+", hql="+hql+", firstRow: "+firstRow+", maxRow: "+maxRow);
        
        if (maxRow < 0) {
            res.addAll(dao.getList(modelName, criteria, -1, -1));
        } else {
            res.addAll(dao.getList(modelName, criteria, firstRow, maxRow));
        }
        totalLength = dao.getCount(modelName, criteria);
        System.out.println("totalLength: "+totalLength);
        return totalLength;
    }
    
    public int getCount(Criteria criteria) {
//        String hql = getHql(criteria);
//        return dao.getCount(hql);
    	return 0;
    }
    
    

    
    public String getModelName() {
//        return dao.getModelClass().getSimpleName();
    	return null;
    }

	public List getList(String hsql) {
		List list = null;
//		list = dao.getList(hsql);
		return list;
	}   
	
	
    protected void populateIdWithPo(ModelObject to) {
    	try { 
    		
	        PropertyDescriptor srcDescriptors[] = BeanUtilsBean.getInstance().getPropertyUtils()
        		.getPropertyDescriptors(to);
	        for(PropertyDescriptor pd:srcDescriptors) {
	        	String name = pd.getName();
	        	Class type = pd.getPropertyType();
	        	debug("name="+name+", type="+type);
	        	if ( ModelObject.class.isAssignableFrom(type)) {
	        		ModelObject toPopulate = (ModelObject)PropertyUtils.getProperty(to, name);
	        		if(toPopulate == null) {
	        			// the form contains no info about associated object, just neglect
	        			continue;
	        		}
    				String id = toPopulate.getId();
    				
    				ModelObject po = get(toPopulate.getClass().getName(), id);
    				if(po==null) {
        				// TODO could be a new one    					
    				}
    				BeanUtils.setProperty(to, name, po);
	        	}
	        }
    		
    	} catch (Exception e) {
    		throw new RuntimeException (e);
    	}
    } 
    
    public void debug(String s) {
    	
    	System.out.println("DefaultManagerImpl: "+s);
    }
}
