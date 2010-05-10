/**
* Author: Bob Chen
*/

package com.jcommerce.core.service;

import java.util.Collection;
import java.util.List;

import com.jcommerce.core.model.ModelObject;

public interface IDefaultManager {
	
	public ModelObject get (String modelName, String id);
	public ModelObject get(String modelName, Long longId);
	
	public int getCount(String modelName, Criteria criteria);
	public int getList (List res, String modelName, Criteria criteria, final int firstRow, final int maxRow);
	
	/**
	 * Return a list of objects based on the list of ids
	 * However if object with a given id does not exist, will just neglect it rather than throw exception
	 * which implies the the size of the returned list could be less than the size of the input list
	 * 
	 * DataStore do not provide "In" or "Or". So this is just a multiple-call to dataStore and in-memory merge
	 * Use it in caution when the size of ids is large
	 * 
	 * @param modelName
	 * @param ids
	 * @return
	 */
	public List getListByIds (String modelName, List<String> ids);
	
	
	public List getList (String modelName, Criteria criteria);
	public List getList (String modelName, Criteria criteria, final int firstRow, final int maxRow);
	public void getListOfChild(List res, String modelName, String fieldName, String parentId);
	
	public String txadd (ModelObject to);
	public String txattach (ModelObject to);
	public boolean txupdate (ModelObject to);
	public boolean txdelete (String modelName, String id);
	public boolean txdelete (ModelObject po);
	public void deleteall (Collection<ModelObject> objs);
	
	

}
