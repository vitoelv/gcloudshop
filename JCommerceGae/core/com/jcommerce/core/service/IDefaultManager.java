/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.ModelObject;

public interface IDefaultManager {
	public boolean delete (String modelName, String id);
	public int getList (List res, String modelName, Criteria criteria, final int firstRow, final int maxRow);
	public List getList (String modelName, Criteria criteria);
	public void getListOfChild(List res, String modelName, String fieldName, String parentId);
	public String add (ModelObject to);
	
	public String attach (ModelObject to);
	
	public int getCount(String modelName, Criteria criteria);
	
//	public String add (String modelName, Map<String, Object> props, String parentId, String parentType);
	
	public boolean update (ModelObject to);
	
	public ModelObject get (String modelName, String id);
}
