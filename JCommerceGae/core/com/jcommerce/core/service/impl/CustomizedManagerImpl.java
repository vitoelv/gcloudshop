package com.jcommerce.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsGallery;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.core.util.UUIDHexGenerator;
import com.jcommerce.core.util.UUIDLongGenerator;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.model.IGoodsGallery;

public class CustomizedManagerImpl extends DefaultManagerImpl implements CustomizedManager {

    public CustomizedManagerImpl() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private DAO dao;
    
    public int getGoodsTypeListWithAttrCount(List<GoodsType> resultSet, int firstRow,
            int maxRow, Criteria criteria) {
    	try {
    		System.out.println("CustomizedManagerImpl.getGoodsTypeListWithAttrCount()");
        
    		List<GoodsType> res = dao.getList(GoodsType.class.getName(), criteria, firstRow, maxRow);
    		for(GoodsType gt:res) {
        		String gtid = gt.getPkId();
    			Criteria c2 = new Criteria();
        		Condition cond = new Condition();
        		cond.setField(AttributeForm.GOODS_TYPE);
        		cond.setOperator(Condition.EQUALS);
        		cond.setValue(gtid);
        		c2.addCondition(cond);
        		
    			int attcount = dao.getCount(Attribute.class.getName(), c2);
    			gt.setAttrcount(attcount);
    		}
    		
    		resultSet.addAll(res);
    		
    		return getDao().getCount(GoodsType.class.getName(), criteria);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }

    
    public String addBrand(Brand to) {
    	try {
    		populateIdWithPo(to);
			String res = dao.add(to);
			
//			DSFile logo = to.getLogo();
//			String id = logo.getPkId();
//			System.out.println("id: "+id);

			// NOTE: This does not work
			// throw exception: 
			// can't update the same entity twice in a transaction or operation
//			to.setLogoFileId(id);
			return res;
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    public String addGoods(Goods to) {
    	try {
    		String goodskn = UUIDHexGenerator.newUUID();
    		to.setKeyName(goodskn);
    		// TODO need overcome the checkbox issue
    		// just for test with Web Home page. 
//    		to.setIsBest(true);
//    		to.setIsNew(true);
//    		to.setIsHot(true);
    		to.setLongId(UUIDLongGenerator.newUUID());
    		
    		Set<GoodsGallery> galleries = to.getGalleries();
    		
    		
			for(GoodsGallery gallery:galleries) {
				String gkn = UUIDHexGenerator.newUUID();
				gallery.setKeyName(gkn);
//				String gid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild("Gallery", gkn).getKey());
//				gallery.setKeyName(gkn);
//				gallery.setId(gid);
				
				DSFile file = gallery.getImageFile();
				String fkn = UUIDHexGenerator.newUUID();
				String fid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", fkn).getKey());
				file.setKeyName(fkn);
//				file.setId(fid);
				gallery.setImageFileId(fid);
				gallery.setLongId(UUIDLongGenerator.newUUID());
				
			}
			
			// TODO temporary. to ensure image not empty
			if(galleries.size()>0) {
				GoodsGallery gallery = (GoodsGallery)galleries.iterator().next();
				to.setImageFileId(gallery.getImageFileId());
				// TODO temporary
				to.setThumbFileId(gallery.getImageFileId());
			}
			
			Set<GoodsAttr> gts = to.getAttributes();
			for(GoodsAttr gt:gts) {
				String gkn = UUIDHexGenerator.newUUID();
				String gid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsAttr.class.getSimpleName(), gkn).getKey());
				gt.setKeyName(gkn);
//				gt.setId(gid);
				gt.setLongId(UUIDLongGenerator.newUUID());
			}			
			
			String res = txattach(to);
			
			
			// verify,  debug only 
			for(GoodsGallery gallery:galleries) {
				System.out.println("galleryId: "+gallery.getPkId());
			}
			String goodsId = to.getPkId();
			System.out.println("goodsId="+goodsId);
			
			Criteria criteria = new Criteria();
			Condition cond = new Condition();
			cond.setField(IGoodsGallery.GOODS_ID);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(goodsId);
			criteria.addCondition(cond);
			List<GoodsGallery> t1 = new ArrayList<GoodsGallery>();
			super.getList(t1, ModelNames.GALLERY, criteria, -1, -1);
			System.out.println("size: "+t1.size());
			
			
			return res;
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	
    	
    }
    public boolean updateGoods(Goods to) {
    	try {
    		String id = to.getPkId();
    		System.out.println("id: "+id);
    		Goods po = (Goods)get(Goods.class.getName(), id);
    		if(po==null) {
    			// TODO 
    		}
    		
    		System.out.println("1) size of po.gallery: "+po.getGalleries().size());
    		
    		String goodskn = po.getKeyName();
    		
    		MyPropertyUtil.copySimpleProperties(po, to);
    		// TODO image/thumb
    		
    		

    		
    		
    		Set<GoodsGallery> galleries = to.getGalleries();
			for(GoodsGallery gallery:galleries) {
				if(StringUtils.isNotEmpty(gallery.getPkId())) {
					// existing gallery
					for(GoodsGallery gpo : po.getGalleries()) {
						if(gpo.getPkId().equals(gallery.getPkId())) {
							gpo.setImgDesc(gallery.getImgDesc());
							break;
						}
					}
//					dao.update(gallery);
//					gallery.setImageFileId(gallery.getImageFile().getPkId());
				} else {
					// new gallery
					String gkn = UUIDHexGenerator.newUUID();
					gallery.setKeyName(gkn);
					DSFile file = gallery.getImageFile();
					String fkn = UUIDHexGenerator.newUUID();
					file.setKeyName(fkn);
					String fid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", fkn).getKey());
					gallery.setImageFileId(fid);
					gallery.setLongId(UUIDLongGenerator.newUUID());
					po.getGalleries().add(gallery);
				}
			}
			
			System.out.println("2) size of po.gallery: "+po.getGalleries().size());
			
			if(galleries.size()>0 && StringUtils.isEmpty(po.getImageFileId())) {
				GoodsGallery gallery = (GoodsGallery)galleries.iterator().next();
				po.setImageFileId(gallery.getImageFileId());
			}
			
			// This will fail due to the #91 Issue
			po.getAttributes().clear();
			Set<GoodsAttr> gts = to.getAttributes();
			for(GoodsAttr gt:gts) {
				String gkn = UUIDHexGenerator.newUUID();
				gt.setKeyName(gkn);
				gt.setLongId(UUIDLongGenerator.newUUID());
				po.getAttributes().add(gt);
				
			}
			
			
			txattach(po);
			
			return true;
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	
    	
    }
    // for test case1 
//    public String addPerson(Person to, Address a) {
//    	try {
//    		populateIdWithPo(to);
//			String res = dao.add(to);
//			
//			a.setPerson(to);
//			dao.add(a);
//			
//			return res;
//    	}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//    	
//    	
//    }

	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}
    
    


}
