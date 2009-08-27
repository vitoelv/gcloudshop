package com.jcommerce.gwt.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Blob;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BrandForm;
import com.jcommerce.gwt.client.form.FileForm;
import com.jcommerce.gwt.client.model.IBrand;

public class BrandGWTAction extends BaseGWTHttpAction {
    
	public void add(Map<String, Object> form) {
    	
    	IDefaultManager manager = getDefaultManager();
		
//		CustomizedManager manager = getCustomizedManager();
    	String res = null;
    	try {
    		String logoFileId = null;
    		FileForm fileForm = (FileForm)form.get(IBrand.BRAND_LOGO);
    		if(fileForm!=null) {
    			DSFile file = new DSFile();
    			file.setContent(new Blob(fileForm.getContent()));
    			logoFileId = manager.txadd(file);
    		}
    		
    		Brand to = form2To(form);
    		to.setLogoFileId(logoFileId);
        	res = manager.txadd(to);
    		
    		
    		
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
    		FileForm fileForm = (FileForm)form.get(IBrand.BRAND_LOGO);
    		if(fileForm!=null) {
    			DSFile file = new DSFile();
    			file.setContent(new Blob(fileForm.getContent()));
    			file.setFileName(fileForm.getFileName());
    			file.setMimeType(fileForm.getMimeType());
    			newLogoFileId = manager.txadd(file);
    		}
    		System.out.println("newLogoFileId: "+newLogoFileId);
    		
    		String id = (String)form.get(IBrand.PK_ID);
    		System.out.println("id: "+id);
    		Brand brand = (Brand)manager.get(Brand.class.getName(), id);
    		String oldLogoFileId = brand.getLogoFileId();
    		System.out.println("oldLogoFileId: "+oldLogoFileId);
    		
    		if(StringUtils.isNotEmpty(oldLogoFileId)) {
    			manager.txdelete(DSFile.class.getName(), oldLogoFileId);
    		}

    		Brand to = form2To(form);
    		to.setLogoFileId(newLogoFileId);
        	res = manager.txupdate(to);
    		
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	
	public void delete(Map<String, Object> form) {
		
    	IDefaultManager manager = getDefaultManager();
    	boolean res = false;
    	try {
    		
    		String id = (String)form.get(IBrand.PK_ID);
    		System.out.println("id: ["+id+"]");
    		Brand brand = (Brand)manager.get(Brand.class.getName(), id);
    		String oldLogoFileId = brand.getLogoFileId();
    		System.out.println("oldLogoFileId: "+oldLogoFileId);
    		if(StringUtils.isNotEmpty(oldLogoFileId)) {
    			manager.txdelete(DSFile.class.getName(), oldLogoFileId);
    		}
        	res = manager.txdelete(Brand.class.getName(), id);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	public Brand form2To(Map<String, Object> form) {
		Brand to = new Brand();
		
		form.put(IBrand.BRAND_LOGO, ((FileForm)form.get(IBrand.BRAND_LOGO)).getFileName());
//		Map<String, Object> props = new HashMap<String, Object>();
//		for(String name:form.keySet()) {
//			Object value = form.get(name);
//			if(value instanceof FileForm) {
//				FileForm ff = (FileForm)value;
//				props.put(name, ff.getFileName());
//			}
//			props.put(name, value);
//		}

//		
//		props.put(IBrand.LOGOFILEID, file.getPkId());
		
		BrandForm bean = new BrandForm(Brand.class.getName(), form);
		MyPropertyUtil.form2To(to, bean.getProperties());
		return to;
	}
}
