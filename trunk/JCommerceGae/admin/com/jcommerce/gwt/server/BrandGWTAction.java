package com.jcommerce.gwt.server;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.form.BrandForm;
import com.jcommerce.gwt.client.form.FileForm;
import com.jcommerce.gwt.client.model.IBrand;

public class BrandGWTAction extends BaseGWTHttpAction {
    
	public void add(Map<String, Object> form) {
    	
    	IDefaultManager manager = getDefaultManager();

		CustomizedManager cm = getCustomizedManager();
    	String res = null;
    	try {
    		
    		Brand to = form2To(form);

    		
    		res = cm.addBrand(to);
    		
//    		to.setLogoFileId(logoFileId);
//        	res = manager.txadd(to);
    		
    		
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	
	public void update(Map<String, Object> form) {
		
		
    	IDefaultManager manager = getDefaultManager();
    	CustomizedManager cm = getCustomizedManager();
    	boolean res;
    	try {
//    		String newLogoFileId = null;
//    		FileForm fileForm = (FileForm)form.get(IBrand.LOGO_FILE);
//    		if(fileForm!=null) {
//    			DSFile file = new DSFile();
//    			file.setContent(new Blob(fileForm.getContent()));
//    			file.setFileName(fileForm.getFileName());
//    			file.setMimeType(fileForm.getMimeType());
//    			newLogoFileId = manager.txadd(file);
//    		}
//    		System.out.println("newLogoFileId: "+newLogoFileId);
//    		
//    		String id = (String)form.get(IBrand.PK_ID);
//    		System.out.println("id: "+id);
//    		Brand brand = (Brand)manager.get(Brand.class.getName(), id);
//    		String oldLogoFileId = brand.getLogoFileId();
//    		System.out.println("oldLogoFileId: "+oldLogoFileId);
//    		
//    		if(StringUtils.isNotEmpty(oldLogoFileId)) {
//    			manager.txdelete(DSFile.class.getName(), oldLogoFileId);
//    		}


    		Brand to = form2To(form);
//    		to.setLogoFileId(newLogoFileId);
//        	res = manager.txupdate(to);
    		
    		String oldFileId = to.getLogoFileId();
    		
    		res = cm.updateBrand(to);
    		
    		// debug only
			// verify
    		try {
    		String pkid = to.getPkId();
			Brand po = (Brand)manager.get(Brand.class.getName(), pkid);
			DSFile file = po.getLogoFile();
			String newFileId = po.getLogoFileId();
			if(file!=null) {
				System.out.println("must be equals: "+StringUtils.equals(file.getPkId(), newFileId));
				System.out.println("must NOT be equals: "+StringUtils.equals(oldFileId, newFileId));
			}
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    		
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
		
//		form.put(IBrand.BRAND_LOGO, ((FileForm)form.get(IBrand.BRAND_LOGO)).getFileName());
		
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
//		String logoFileId = null;
		FileForm fileForm = (FileForm)form.get(IBrand.LOGO_FILE);
		DSFile file = null;
		Image image = null;
		Transform resize = null;
		Image newImage = null;
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		
		if(fileForm!=null) {
			file = new DSFile();
//			file.setContent(new Blob(fileForm.getContent()));
			file.setFileName(fileForm.getFileName());
			file.setMimeType(fileForm.getMimeType());
			
			image = ImagesServiceFactory.makeImage(fileForm.getContent());
			resize = ImagesServiceFactory.makeResize(200, 200);
			newImage = imagesService.applyTransform(resize, image);
			file.setContent(new Blob(newImage.getImageData()));
//			logoFileId = manager.txadd(file);
		}
		
		// to avoid exception in form2To
		form.put(IBrand.LOGO_FILE, null);
		
		BrandForm bean = new BrandForm(Brand.class.getName(), form);
		MyPropertyUtil.form2To(to, bean.getProperties());

		to.setLogoFile(file);
		
		return to;
	}
}
