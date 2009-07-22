package com.jcommerce.gwt.server;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Blob;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttribute;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.form.FileForm;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.model.IGallery;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsAttribute;

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
    	String res = null;
    	try {
    		
    		Goods to = form2To(form);
    		res = cm.addGoods(to);

    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	
	
	public void update(Map<String, Object> form) {
		CustomizedManager cm = super.getCustomizedManager();
    	try {
    		
    		Object bestSold = (Object)form.get(IGoods.BESTSOLD);
    		System.out.println("bestSold: "+bestSold);
    		
    		String id = (String)form.get(IGoods.ID);

    		
    		Goods to = form2To(form);
        	cm.updateGoods(to);

    		
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
    		DSFile imageFile = goods.getImageFile();
    		String imageFileId = imageFile.getId();
    		System.out.println("imageFileId: "+imageFileId);
//    		manager.delete(DSFile.class.getName(), imageFileId);
    		
//    		manager.delete(DSFile.class.getName(), thumbFileId);
    		
    		Set<Gallery> galleries = goods.getGalleries();
    		Gallery gallery = null;
    		DSFile galleryFile = null;
    		if(galleries.size()>0) {
    			gallery = galleries.iterator().next();
    			galleryFile = gallery.getImageFile();
    		}
    		
        	res = manager.txdelete(Goods.class.getName(), id);
    
        	// verify cascade delete
        	
        	imageFile = (DSFile)manager.get(DSFile.class.getName(), imageFileId);
        	System.out.println("imageFile should be null: "+imageFile);
        	if(gallery!=null) {
        		galleryFile = (DSFile)manager.get(DSFile.class.getName(), galleryFile.getId());
        		gallery = (Gallery)manager.get(Gallery.class.getName(), gallery.getId());
        		System.out.println("gallery should be null: gallery");
        	}
        	
        	
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
	
	
	public Goods form2To(Map<String, Object> form) {
		Goods to = new Goods();
		
		DSFile imageFile = null;
		FileForm fileForm = (FileForm)form.get(IGoods.IMAGE);
		if(fileForm!=null) {
			imageFile = getFile(fileForm);
    		form.put(IGoods.IMAGE, ((FileForm)form.get(IGoods.IMAGE)).getFileName());    			
		}

		DSFile thumbFile = null;
		fileForm = (FileForm)form.get(IGoods.THUMB);
		if(fileForm!=null) {
			thumbFile = getFile(fileForm);
    		form.put(IGoods.THUMB, ((FileForm)form.get(IGoods.THUMB)).getFileName());
		}
		
		GoodsForm bean = new GoodsForm(Goods.class.getName(), form);
		MyPropertyUtil.form2To(to, bean.getProperties());
		
		
		to.setImageFile(imageFile);
		to.setThumbFile(thumbFile);

		
		Set<Gallery> galleries = to.getGalleries();
		Map<String, Map<String, Object>> nestedGalleryForms = (Map<String, Map<String, Object>>)form.get(IGoods.GALLERIES);
		if(nestedGalleryForms!=null) {
			for(Map<String, Object> attrs : nestedGalleryForms.values()) {
				String gid = (String)attrs.get(IGallery.ID);
				Gallery gallery = new Gallery();
				gallery.setDescription((String)attrs.get(IGallery.DESCRIPTION));
				if(StringUtils.isEmpty(gid)) {
					// new gallery
					try {
					fileForm = (FileForm)attrs.get(IGallery.IMAGE);
					DSFile file = getFile(fileForm);
					if(StringUtils.isEmpty(file.getFileName())) {
						// no file uploaded.
						// this happens when keeping empty in the default upload slot
						continue;
					}
					gallery.setImage(file.getFileName());
					gallery.setImageFile(file);
					galleries.add(gallery);
					} catch (Exception ex) {
						// TODO find out the real cause of NPE
						ex.printStackTrace();
					}
				} else {
					// existing file
					gallery.setId(gid);
					galleries.add(gallery);
				}

			}
		}

		Set<GoodsAttribute> goodsAttributes = to.getAttributes();
		Map<String, Map<String, Object>> nestedGoodsAttributeForms = (Map<String, Map<String, Object>>)form.get(IGoods.ATTRIBUTES);
		if(nestedGoodsAttributeForms!=null) {
			for(Map<String, Object> attrs : nestedGoodsAttributeForms.values()) {
				GoodsAttribute ga = new GoodsAttribute();
				ga.setAttributeId((String)attrs.get(IGoodsAttribute.ATTRIBUTEID));
				ga.setValue((String)attrs.get(IGoodsAttribute.VALUE));
				goodsAttributes.add(ga);
			}
		}
		
		

		return to;
	}
	
	public void debug(String s) {
		System.out.println("[GoodsGWTAction]: "+s);
	}

}
