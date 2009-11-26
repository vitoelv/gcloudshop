/**
* Author: Bob Chen
*/

package com.jcommerce.core.service.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;

public class DefaultManagerImpl implements IDefaultManager {
    private DAO dao;
    
    
    public String txadd (ModelObject to) {
    	try {
    		populateIdWithPo(to);
			String res = getDao().add(to);

			return res;
    		
    	} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    public String txattach (ModelObject to) {
    	try {
			String res = getDao().attach(to);
			return res;
    	} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
	/**
	 * update only simple attributes
	 * @param to
	 * @return
	 */
	public boolean txupdate (ModelObject to) {
		try {
			// TODO need not this?
//			populateIdWithPo(to);
			return getDao().update(to);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    public boolean txdelete (String modelName, String id) {
    	boolean res = getDao().delete(modelName, id);
    	return res;
    }
    public void txdeleteall (Collection<ModelObject> objs) {
    	getDao().deleteAll(objs);
    }
    
    
	public ModelObject get (String modelName, String id) {
		ModelObject obj = getDao().get(modelName, id);
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
	
	public ModelObject get(String modelName, Long longId) {
		ModelObject obj = getDao().get(modelName, longId);
		return obj;
	}
	


	
    public DAO getDao() {
        return this.dao;
    }
    public void setDao(DAO dao) {
        this.dao = dao;
    }
    

    public List getListByIds (String modelName, List<String> ids) {
    	debug("[getListByIds]: modelName="+modelName);
    	List res = new ArrayList();
    	for(String id : ids) {
    		ModelObject obj = getDao().get(modelName, id);
    		if(obj!=null) {
    			res.add(obj);
    		}
    	}
    	return res;
    	
    	
    }
    
    public List getList(String modelName, Criteria criteria) {
        debug("[getList]: modelName="+modelName);
        
        List res = new ArrayList();
        res.addAll(getDao().getList(modelName, criteria, -1, -1));
        
        return res; 

    }
    public List getList(String modelName, Criteria criteria, int firstRow, int maxRow) {
        debug("[getList]: modelName="+modelName+", firstRow: "+firstRow+", maxRow: "+maxRow);
        List res = new ArrayList();
        res.addAll(getDao().getList(modelName, criteria, firstRow, maxRow));
        
        return res;

    }
    public int getList(List res, String modelName, Criteria criteria, int firstRow, int maxRow) {
    	int totalLength = 0;
        debug("[getList]: modelName="+modelName+", firstRow: "+firstRow+", maxRow: "+maxRow);
        
        if (maxRow < 0) {
            res.addAll(getDao().getList(modelName, criteria, -1, -1));
        } else {
            res.addAll(getDao().getList(modelName, criteria, firstRow, maxRow));
        }
        totalLength = getDao().getCount(modelName, criteria);
        debug("[getList]: totalLength: "+totalLength);
        return totalLength;
    }
    
    public void getListOfChild(List res, String modelName, String fieldName, String parentId) {
    	try {
    		debug("[getListOfChild]: ");
			Criteria criteria = new Criteria();
    		Condition cond = new Condition();
    		cond.setField(fieldName);
    		cond.setOperator(Condition.EQUALS);
    		cond.setValue(parentId);
    		criteria.addCondition(cond);
    		
    		List<ModelObject> res1 = getDao().getList(modelName, criteria, -1, -1);
    		res.addAll(res1);
    		
//    		return dao.getCount(modelName, criteria);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
    
    public int getCount(String modelName, Criteria criteria) {
		try {
			return getDao().getCount(modelName, criteria);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	
    protected void populateIdWithPo(ModelObject to) {
    	try { 
    		
    		// TODO leon in fact the only Id need populate with PO is when adding a child whose parent exist
    		// even update need not populate, as we force update to be direct attributes only
    		// verify the idea and change it
	        PropertyDescriptor srcDescriptors[] = BeanUtilsBean.getInstance().getPropertyUtils()
        		.getPropertyDescriptors(to);
	        for(PropertyDescriptor pd:srcDescriptors) {
	        	String name = pd.getName();
	        	Class type = pd.getPropertyType();
	        	Object val = PropertyUtils.getProperty(to, name);
	        	debug("name="+name+", type="+type+", val="+val);
	        	if ( ModelObject.class.isAssignableFrom(type)) {
	        		// parent is just a method
	        		if("parent".equals(name)) {
	        			continue;
	        		}
	        		ModelObject toPopulate = (ModelObject)PropertyUtils.getProperty(to, name);
	        		if(toPopulate == null) {
	        			// the form contains no info about associated object, just neglect
	        			continue;
	        		}
    				String id = toPopulate.getPkId();
    				if(id==null) {
    					// TODO a new one
    				}
    				else {
    				ModelObject po = get(toPopulate.getClass().getName(), id);
    				if(po==null) {
        				// TODO could be a new one    					
    				}
    				BeanUtils.setProperty(to, name, po);
    				}
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
