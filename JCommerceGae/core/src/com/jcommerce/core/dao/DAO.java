package com.jcommerce.core.dao;

import java.util.Collection;
import java.util.List;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Criteria;

/**
 * Data Access Object (DAO) interface. Common methods for each interface
 * could be added here.
 *
 * @author Bob Chen
 */
public interface DAO {
    public List getList(String modelName, Criteria criteria, int firstRow, int maxRow);
    public String add(ModelObject to);
    public String attach (ModelObject to);
    
//    public String add(String modelName, ModelObject data, String parentId, String parentType);
    public boolean delete (String modelName, String id);
    public boolean delete (ModelObject obj);
    
    public int getCount(String modelName, Criteria criteria);
    
    public void deleteAll(Collection<ModelObject> objs);
    
	public ModelObject get (String modelName, String id);
    
	public boolean update (ModelObject to);
	
//    public Class getModelClass();
}
