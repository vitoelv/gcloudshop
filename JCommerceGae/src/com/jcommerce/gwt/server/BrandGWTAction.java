package com.jcommerce.gwt.server;

import java.util.HashMap;
import java.util.Map;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.BrandForm;
import com.jcommerce.gwt.client.model.IBrand;

public class BrandGWTAction extends BaseGWTHttpAction {
    
	public void add(Map<String, Object> form) {
    	
    	IDefaultManager manager = getDefaultManager();
    	String res = null;
    	try {
    		String fileId = null;
    		DSFile file = (DSFile)form.get(IBrand.LOGO);
    		if(file!=null) {
    			fileId = manager.add(file);
    		}
    		
    		BrandForm bean = getForm(form);
    		
        	ModelObject to = (ModelObject)Class.forName(bean.getModelName()).newInstance();
            MyPropertyUtil.form2To(to, bean.getProperties());
        	res = manager.add(to);
    		
    		
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	
	public void update(Map<String, Object> form) {
		
		
    	IDefaultManager manager = getDefaultManager();
    	boolean res;
    	try {
    		String newLogoFileId = null;
    		DSFile file = (DSFile)form.get(IBrand.LOGO);
    		if(file!=null) {
    			newLogoFileId = manager.add(file);
    		}
    		System.out.println("newLogoFileId: "+newLogoFileId);
    		
    		String id = (String)form.get(IBrand.ID);
    		System.out.println("id: "+id);
    		Brand brand = (Brand)manager.get(Brand.class.getName(), id);
    		String oldLogoFileId = brand.getLogoFileId();
    		System.out.println("oldLogoFileId: "+oldLogoFileId);
    		
    		manager.delete(DSFile.class.getName(), oldLogoFileId);
    		
    		BrandForm bean = getForm(form);
    		
        	ModelObject to = (ModelObject)Class.forName(bean.getModelName()).newInstance();
            MyPropertyUtil.form2To(to, bean.getProperties());
        	res = manager.update(to);
    		
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	
	public void delete(Map<String, Object> form) {
		
    	IDefaultManager manager = getDefaultManager();
    	boolean res = false;
    	try {
    		
    		String id = (String)form.get(IBrand.ID);
    		System.out.println("id: ["+id+"]");
    		Brand brand = (Brand)manager.get(Brand.class.getName(), id);
    		String oldLogoFileId = brand.getLogoFileId();
    		System.out.println("oldLogoFileId: "+oldLogoFileId);
    		
    		manager.delete(DSFile.class.getName(), oldLogoFileId);
        	res = manager.delete(Brand.class.getName(), id);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	public BrandForm getForm(Map<String, Object> form) {
		Map<String, Object> props = new HashMap<String, Object>();
		DSFile file = (DSFile)form.get(IBrand.LOGO);
		for(String name:form.keySet()) {
			if(IBrand.LOGO.equals(name)) {
				props.put(name, file.getFilename());
			}
			else {
				props.put(name, form.get(name));
			}
		}

		
		props.put(IBrand.LOGOFILEID, file.getId());
		
		BrandForm bean = new BrandForm(Brand.class.getName(), props);
		return bean;
	}
}
