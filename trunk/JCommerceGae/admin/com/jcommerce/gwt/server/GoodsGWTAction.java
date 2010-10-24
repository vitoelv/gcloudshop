package com.jcommerce.gwt.server;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
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
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.gwt.server.util.ServerFormatUtil;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.util.SpringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

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
    		to.setAddTime(new Date().getTime());
    		res = cm.addGoods(to, getLocale());

    		
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
    		to.setLastUpdate(new Date().getTime());
        	cm.updateGoods(to, getLocale());

    		
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
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		
		ShopConfigWrapper scw = SpringUtil.getShopConfigManager().getCachedShopConfig(getLocale());
		String thumbSizeStr = scw.getString(IShopConfigMeta.CFG_KEY_GOODS_THUMB_SIZE);
		if(StringUtils.isEmpty(thumbSizeStr)) {
		    thumbSizeStr = "200,200"; //default
		}
		int thumbWidth=200, thumbHeight=200;
		try {
		    String[] ss = StringUtils.split(thumbSizeStr, ",");
		    thumbWidth = Integer.valueOf(ss[0].trim());
		    thumbHeight = Integer.valueOf(ss[1].trim());
		} catch (Exception ex) {
		    throw new RuntimeException("shopconfig: [CFG_KEY_GOODS_THUMB_SIZE] format is incorrect, value=["+thumbSizeStr+"]");
		}
		 
		
		
		DSFile imageFile = null;
		FileForm fileForm = (FileForm)form.get(IGoods.IMAGE);
		Image image = null;
		Transform resize = null;
		Image newImage = null;
		if(fileForm!=null) {
			imageFile = getFile(fileForm);
    		form.put(IGoods.IMAGE, ((FileForm)form.get(IGoods.IMAGE)).getFileName());    			
		}


		DSFile thumbFile = null;
		if(fileForm!=null) {
			thumbFile = new DSFile();
			image = ImagesServiceFactory.makeImage(fileForm.getContent());
			
			thumbFile.setFileName("thumb"+fileForm.getFileName());
			thumbFile.setMimeType(fileForm.getMimeType());
			
			resize = ImagesServiceFactory.makeResize(thumbWidth, thumbHeight);
			
			// always convert to png? 
			//, ImagesService.OutputEncoding.PNG
			newImage = imagesService.applyTransform(resize, image);
			
			thumbFile.setContent(new Blob(newImage.getImageData()));
    		form.put(IGoods.THUMB,thumbFile.getFileName());
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
					if(fileForm == null){
						continue;
					}
					DSFile file = getFile(fileForm);
					if(StringUtils.isEmpty(file.getFileName())) {
						// no file uploaded.
						// this happens when keeping empty in the default upload slot
						continue;
					}
					image = ImagesServiceFactory.makeImage(fileForm.getContent());
					//TODO load size from shopconfig
					// currently not using this Image field. instead using the Thumb
					resize = ImagesServiceFactory.makeResize(400, 400);
					newImage = imagesService.applyTransform(resize, image);
					file.setContent(new Blob(newImage.getImageData()));
					
					gallery.setImage(file.getFileName());
					gallery.setImageFile(file);
					
					
					thumbFile = new DSFile();
					thumbFile.setFileName("thumb"+fileForm.getFileName());
					thumbFile.setMimeType(fileForm.getMimeType());
					//TODO load size from shopconfig
					resize = ImagesServiceFactory.makeResize(thumbWidth, thumbHeight);
					newImage = imagesService.applyTransform(resize, image);
					
					thumbFile.setContent(new Blob(newImage.getImageData()));
					
					gallery.setThumb(thumbFile.getFileName());
					gallery.setThumbFile(thumbFile);
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
		List<Map<String, String>> attrList = new ArrayList<Map<String, String>>();
		if(nestedGoodsAttributeForms!=null) {
			for(Map<String, Object> attrs : nestedGoodsAttributeForms.values()) {
				String id = (String) attrs.get(IGoodsAttr.ATTR_ID);
				String[] ids = id.split(":");
				String attrId = (String) ids[0];
				String goodsId = (String) ids[1];
				Map<String, String>attrMap = new HashMap<String, String>();
				attrMap.put(attrId, (String)attrs.get(IGoodsAttr.ATTR_VALUE));
				
				if(!attrList.contains(attrMap)) {
					GoodsAttr ga = new GoodsAttr();
					ga.setAttrId(attrId);
					ga.setAttrValue((String)attrs.get(IGoodsAttr.ATTR_VALUE));
					String price = (String) attrs.get(IGoodsAttr.ATTR_PRICE);
					price = price.equals("") ? "0" : price;
					ga.setAttrPrice(price);
					ga.setGoodsId(goodsId);
					goodsAttributes.add(ga);
					attrList.add(attrMap);
				}
			}
		}
		
		Set<GoodsAttr> deleteAttr = new HashSet<GoodsAttr>();
		for(Iterator i = goodsAttributes.iterator() ; i.hasNext();) {
			GoodsAttr goodsAttr = (GoodsAttr) i.next();
			String attrId = goodsAttr.getAttrId();
			String value = goodsAttr.getAttrValue();
			Map<String, String> attrMap = new HashMap<String, String>();
			attrMap.put(attrId, value);
			if(!attrList.contains(attrMap)) {
				deleteAttr.add(goodsAttr);
			}
		}
		goodsAttributes.removeAll(deleteAttr);	

		return to;
	}
	
	public void debug(String s) {
		System.out.println("[GoodsGWTAction]: "+s);
	}

}
