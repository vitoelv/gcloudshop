package com.jcommerce.gwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Blob;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.form.FileForm;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.model.IGallery;
import com.jcommerce.gwt.client.model.IGoods;

public class GoodsGWTAction extends BaseGWTHttpAction {
	
	public DSFile getFile(FileForm fileForm) {
		DSFile file = new DSFile();
		file.setContent(new Blob(fileForm.getContent()));
		file.setFileName(fileForm.getFileName());
		file.setMimeType(fileForm.getMimeType());
		return file;
	}
	
	public void add(Map<String, Object> form) {
		
		CustomizedManager cm = super.getCustomizedManager();
    	IDefaultManager manager = getDefaultManager();
    	String res = null;
    	try {
    		String imageFileId = null;
    		FileForm fileForm = (FileForm)form.get(IGoods.IMAGE);
    		if(fileForm!=null) {
    			DSFile file = getFile(fileForm);
    			imageFileId = manager.add(file);
    		}

    		String thumbFileId = null;
    		fileForm = (FileForm)form.get(IGoods.THUMB);
    		if(fileForm!=null) {
    			DSFile file = getFile(fileForm);
    			thumbFileId = manager.add(file);
    		}
    		
    		
    		Goods to = form2To(form);
    		to.setImageFileId(imageFileId);
    		to.setThumbFileId(thumbFileId);
    		
    		List<Gallery> galleries = new ArrayList<Gallery>();
    		
    		Map<String, Map<String, Object>> nestedForms = (Map<String, Map<String, Object>>)form.get(IGoods.GALLERIES);
    		if(nestedForms!=null) {
    			for(Map<String, Object> attrs : nestedForms.values()) {
    				Gallery gallery = new Gallery();
    				String gimageFileId = null;
    				gallery.setDescription((String)attrs.get(IGallery.DESCRIPTION));
    				fileForm = (FileForm)attrs.get(IGallery.IMAGE);
    				DSFile file = getFile(fileForm);
    				gimageFileId = manager.add(file);
    				gallery.setImage(file.getFileName());
    				gallery.setImageFileId(gimageFileId);
    				galleries.add(gallery);
//    				to.getGalleries().add(gallery);
    			}
    		}
    		
    		res = cm.addGoods(to, galleries);
//        	res = manager.add(to);

    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	
	
	public void update(Map<String, Object> form) {
		CustomizedManager cm = super.getCustomizedManager();
    	IDefaultManager manager = getDefaultManager();
    	boolean res;
    	try {
    		
    		Object bestSold = (Object)form.get(IGoods.BESTSOLD);
    		System.out.println("bestSold: "+bestSold);
    		
    		String id = (String)form.get(IGoods.ID);
    		System.out.println("id: "+id);
    		Goods goods = (Goods)manager.get(Goods.class.getName(), id);
    		if(goods==null) {
    			// TODO 
    		}
    		
    		String newImageFileId = null;
    		FileForm fileForm = (FileForm)form.get(IGoods.IMAGE);
    		if(fileForm!=null) {
    			DSFile file = new DSFile();
    			file.setContent(new Blob(fileForm.getContent()));
    			file.setFileName(fileForm.getFileName());
    			file.setMimeType(fileForm.getMimeType());
    			newImageFileId = manager.add(file);
    		}

    		String newThumbFileId = null;
    		fileForm = (FileForm)form.get(IGoods.THUMB);
    		if(fileForm!=null) {
    			DSFile file = new DSFile();
    			file.setContent(new Blob(fileForm.getContent()));
    			file.setFileName(fileForm.getFileName());
    			file.setMimeType(fileForm.getMimeType());
    			newThumbFileId = manager.add(file);
    		}
    		
    		
    		
    		String imageFileId = goods.getImageFileId();
    		manager.delete(DSFile.class.getName(), imageFileId);
    		
    		String thumbFileId = goods.getThumbFileId();
    		manager.delete(DSFile.class.getName(), thumbFileId);
    		
    		Goods to = form2To(form);
    		to.setImageFileId(newImageFileId);
    		to.setThumbFileId(newThumbFileId);
    		
    		List<Gallery> galleries = new ArrayList<Gallery>();
    		
    		Map<String, Map<String, Object>> nestedForms = (Map<String, Map<String, Object>>)form.get(IGoods.GALLERIES);
    		if(nestedForms!=null) {
    			for(Map<String, Object> attrs : nestedForms.values()) {
    				Gallery gallery = new Gallery();
    				String gid = (String)attrs.get(IGallery.ID);
    				if(StringUtils.isNotEmpty(gid)) {
    					//existing
    					gallery.setId(gid);
    				}
    				gallery.setDescription((String)attrs.get(IGallery.DESCRIPTION));
    				String gimageFileId = (String)attrs.get(IGallery.IMAGEFILEID);
    				if(StringUtils.isNotEmpty(gimageFileId)) {
    					// file do not change
    					gallery.setImage((String)attrs.get(IGallery.IMAGE));
    					gallery.setImageFileId(gimageFileId);
    				} else {
    					fileForm = (FileForm)attrs.get(IGallery.IMAGE);
    					DSFile file = getFile(fileForm);
    					gimageFileId = manager.add(file);
    					gallery.setImage(file.getFileName());
    					gallery.setImageFileId(gimageFileId);
    				}
    				
					galleries.add(gallery);
    			}
    		}
    		
        	cm.updateGoods(to, galleries);

    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	public void delete(Map<String, Object> form) {
		
    	IDefaultManager manager = getDefaultManager();
    	boolean res = false;
    	try {
    		
    		String id = (String)form.get(IGoods.ID);
    		System.out.println("id: ["+id+"]");
    		Goods goods = (Goods)manager.get(Goods.class.getName(), id);
    		String imageFileId = goods.getImageFileId();
    		System.out.println("imageFileId: "+imageFileId);
    		manager.delete(DSFile.class.getName(), imageFileId);
    		
    		String thumbFileId = goods.getThumbFileId();
    		System.out.println("thumbFileId: "+thumbFileId);
    		manager.delete(DSFile.class.getName(), thumbFileId);
    		
        	res = manager.delete(Goods.class.getName(), id);
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	public Goods form2To(Map<String, Object> form) {
		Goods to = new Goods();
		
		form.put(IGoods.IMAGE, ((FileForm)form.get(IGoods.IMAGE)).getFileName());
		form.put(IGoods.THUMB, ((FileForm)form.get(IGoods.THUMB)).getFileName());
		
		GoodsForm bean = new GoodsForm(Goods.class.getName(), form);
		MyPropertyUtil.form2To(to, bean.getProperties());
		return to;
	}
	

}
