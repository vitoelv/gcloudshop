/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.ModelObject;

public interface IDefaultManager {
	
	public ModelObject get (String modelName, String id);
	public ModelObject get(String modelName, Long longId);
	
	public int getCount(String modelName, Criteria criteria);
	public int getList (List res, String modelName, Criteria criteria, final int firstRow, final int maxRow);
	public List getList (String modelName, Criteria criteria);
	public List getList (String modelName, Criteria criteria, final int firstRow, final int maxRow);
	public void getListOfChild(List res, String modelName, String fieldName, String parentId);
	
	public String txadd (ModelObject to);
	public String txattach (ModelObject to);
	public boolean txupdate (ModelObject to);
	public boolean txdelete (String modelName, String id);
	
	

}
