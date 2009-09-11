package com.jcommerce.gwt.server;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Blob;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsGallery;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.form.FileForm;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsAttr;
import com.jcommerce.gwt.client.model.IGoodsGallery;
import com.jcommerce.gwt.server.util.ServerFormatUtil;

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
    		
    		Object bestSold = (Object)form.get(IGoods.IS_BEST);
    		System.out.println("bestSold: "+bestSold);
    		
    		String id = (String)form.get(IGoods.PK_ID);

    		
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
    		
    		String id = (String)form.get(IGoods.PK_ID);
    		System.out.println("id: ["+id+"]");
    		Goods goods = (Goods)manager.get(Goods.class.getName(), id);
    		DSFile imageFile = goods.getImageFile();
    		String imageFileId = imageFile.getPkId();
    		System.out.println("imageFileId: "+imageFileId);
//    		manager.delete(DSFile.class.getName(), imageFileId);
    		
//    		manager.delete(DSFile.class.getName(), thumbFileId);
    		
    		Set<GoodsGallery> galleries = goods.getGalleries();
    		GoodsGallery gallery = null;
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
        		galleryFile = (DSFile)manager.get(DSFile.class.getName(), galleryFile.getPkId());
        		gallery = (GoodsGallery)manager.get(GoodsGallery.class.getName(), gallery.getPkId());
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
		
		
		String sDateStart = (String)form.get(IGoods.PROMOTE_START_DATE);
		String sDateEnd = (String)form.get(IGoods.PROMOTE_END_DATE);
		// will cause error: GWT.create() is only usable in client code!
//		to.setPromoteStartDate(DateTimeFormat.getShortDateFormat().parse(sDateStart).getTime());
//		to.setPromoteEndDate(DateTimeFormat.getShortDateFormat().parse(sDateEnd).getTime());
		if(StringUtils.isNotBlank(sDateStart)) {
			to.setPromoteStartDate(ServerFormatUtil.parseShortDate(sDateStart).getTime());
		}
		if(StringUtils.isNotBlank(sDateEnd)) {
			to.setPromoteEndDate(ServerFormatUtil.parseShortDate(sDateEnd).getTime());
		}
		
		to.setImageFile(imageFile);
		to.setThumbFile(thumbFile);

		
		Set<GoodsGallery> galleries = to.getGalleries();
		Map<String, Map<String, Object>> nestedGalleryForms = (Map<String, Map<String, Object>>)form.get(IGoods.GALLERIES);
		if(nestedGalleryForms!=null) {
			for(Map<String, Object> attrs : nestedGalleryForms.values()) {
				String gid = (String)attrs.get(IGoodsGallery.PK_ID);
				GoodsGallery gallery = new GoodsGallery();
				gallery.setImgDesc((String)attrs.get(IGoodsGallery.IMG_DESC));
				if(StringUtils.isEmpty(gid)) {
					// new gallery
					try {
					fileForm = (FileForm)attrs.get(IGoodsGallery.IMAGE);
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
					gallery.setPkId(gid);
					galleries.add(gallery);
				}

			}
		}

		Set<GoodsAttr> goodsAttributes = to.getAttributes();
		Map<String, Map<String, Object>> nestedGoodsAttributeForms = (Map<String, Map<String, Object>>)form.get(IGoods.ATTRIBUTES);
		if(nestedGoodsAttributeForms!=null) {
			for(Map<String, Object> attrs : nestedGoodsAttributeForms.values()) {
				GoodsAttr ga = new GoodsAttr();
				ga.setAttrId((String)attrs.get(IGoodsAttr.ATTR_ID));
				ga.setAttrValue((String)attrs.get(IGoodsAttr.ATTR_VALUE));
				goodsAttributes.add(ga);
			}
		}
		
		

		return to;
	}
	
	public void debug(String s) {
		System.out.println("[GoodsGWTAction]: "+s);
	}

}
