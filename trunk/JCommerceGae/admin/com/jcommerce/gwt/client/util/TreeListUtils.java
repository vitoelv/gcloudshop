package com.jcommerce.gwt.client.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IModelObject;

public class TreeListUtils {
	
	
	public static List<BeanObject> toTreeList(List<BeanObject> beans){
		Map<String,BeanObject> beansMap = new HashMap<String,BeanObject>();
		Map<String,List<BeanObject>> pcMap = new HashMap<String,List<BeanObject>>();
		for(BeanObject bean:beans){
			String treeParentId = bean.getString(IModelObject.TREE_PARENT_ID);

			beansMap.put(bean.getString(IModelObject.PK_ID), bean);
			List<BeanObject> children = pcMap.get(treeParentId);
			if(children == null){
				children = new ArrayList<BeanObject>();
				pcMap.put(treeParentId, children);
			}
			children.add(bean);
		}
		List<BeanObject> topLevel = pcMap.get(null);
		addChild(beansMap,pcMap,topLevel);
		return topLevel;
	}
	public static void addChild(Map<String,BeanObject> beansMap,Map<String,List<BeanObject>> pcMap,List<BeanObject> beans){
		for(BeanObject bean:beans){
			if(pcMap.containsKey(bean.getString(IModelObject.PK_ID))){
				addChild(beansMap,pcMap,pcMap.get(bean.getString(IModelObject.PK_ID)));
			}
			if(bean.getString(IModelObject.TREE_PARENT_ID)!=null){
				beansMap.get(bean.getString(IModelObject.TREE_PARENT_ID)).add(bean);
			}
		}
	}

}
