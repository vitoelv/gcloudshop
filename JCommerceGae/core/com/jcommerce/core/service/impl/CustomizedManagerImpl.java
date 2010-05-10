package com.jcommerce.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.AreaRegion;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsGallery;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.config.IShopConfigManager;
import com.jcommerce.core.service.config.ShopConfigManagerImpl;
import com.jcommerce.core.util.DataStoreUtils;
import com.jcommerce.core.util.GoogleBaseUtil;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.core.util.UUIDLongGenerator;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.model.IAreaRegion;
import com.jcommerce.gwt.client.model.IGoodsGallery;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.util.SpringUtil;

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

    public void getShippingAreaWithRegionName(List<ShippingArea> resultSet, String shippingId) {
    	try {
    		System.out.println("CustomizedManagerImpl.getShippingAreaWithRegionName()");
    		Criteria criteria = new Criteria();
    		criteria.addCondition(new Condition(IShippingArea.SHIPPING, Condition.EQUALS, shippingId));
    		List<ShippingArea> list = dao.getList(ShippingArea.class.getName(), criteria);
    		for(ShippingArea sa:list) {
        		String saId = sa.getPkId();
    			Criteria c2 = new Criteria();
        		Condition cond = new Condition();
        		cond.setField(IAreaRegion.SHIPPING_AREA);
        		cond.setOperator(Condition.EQUALS);
        		cond.setValue(saId);
        		c2.addCondition(cond);
        		
    			List<AreaRegion> ars = dao.getList(AreaRegion.class.getName(), c2);
    			StringBuffer buf = new StringBuffer();
    			for(AreaRegion ar : ars) {
    				String rid = ar.getRegionId();
    				Region region = (Region)dao.get(Region.class.getName(), rid);
    				String rname = region==null? "": region.getRegionName();
    				if(buf.length()>0) {
    					buf.append(", ");
    				}
    				buf.append(rname);
    			}
    			sa.setRegionNames(buf.toString());
    		}
    		resultSet.addAll(list);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
    public String addBrand(Brand to) {
    	try {
    		String bkn = DataStoreUtils.genKeyName(to); 
    		to.setKeyName(bkn);
    		to.setLongId(UUIDLongGenerator.newUUID());
    		DSFile file = to.getLogoFile();
    		if (file != null) {
				String fkn = DataStoreUtils.genKeyName(file);
				String fid = KeyFactory.keyToString(new KeyFactory.Builder(
						"Brand", bkn).addChild("DSFile", fkn).getKey());
				file.setKeyName(fkn);
				to.setLogoFileId(fid);
			}
			
			String res = txattach(to); 
    		
    		
//    		populateIdWithPo(to);
//			String res = dao.add(to);
			
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
    
    public boolean updateBrand(Brand to) {
    	try {
    		String id = to.getPkId();
    		System.out.println("id: "+id);
    		Brand po = (Brand)get(Brand.class.getName(), id);
    		if(po==null) {
    			// TODO 
    		}
    		String bkn = po.getKeyName();
    		
    		
    		String fkn = null;
    		String fid = null;
    		DSFile newFile = null, oldFile = null;
    		MyPropertyUtil.copySimpleProperties(po, to);
    		if(to.getLogoFile()==null) {
    			// no new logofile, do nothing
    			
    		}
    		else {
    			oldFile = po.getLogoFile();
        		newFile = to.getLogoFile();
    			fkn = DataStoreUtils.genKeyName(newFile);
    			fid = KeyFactory.keyToString(new KeyFactory.Builder("Brand",bkn).addChild("DSFile", fkn).getKey());
    			newFile.setKeyName(fkn);
    			po.setLogoFileId(fid);
    			po.setLogoFile(newFile);
    			boolean suc = txdelete(oldFile);
    		}
    		
			String res = txattach(po);
			
			

			return true;
    	} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    public String addGoods(Goods to) {
    	try {
    		String goodskn = DataStoreUtils.genKeyName(to);
    		to.setKeyName(goodskn);
    		// TODO need overcome the checkbox issue
    		// just for test with Web Home page. 
//    		to.setIsBest(true);
//    		to.setIsNew(true);
//    		to.setIsHot(true);
    		to.setLongId(UUIDLongGenerator.newUUID());
    		
    		Set<GoodsGallery> galleries = to.getGalleries();
    		
    		
			for(GoodsGallery gallery:galleries) {
				String gkn = DataStoreUtils.genKeyName(gallery);
				gallery.setKeyName(gkn);
//				String gid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild("Gallery", gkn).getKey());
//				gallery.setKeyName(gkn);
//				gallery.setId(gid);
				
				DSFile file = gallery.getImageFile();
				String fkn = DataStoreUtils.genKeyName(file);
				String fid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", fkn).getKey());
				file.setKeyName(fkn);
//				file.setId(fid);
				gallery.setImageFileId(fid);
				
				DSFile thumbfile = gallery.getThumbFile();
				String tfkn = DataStoreUtils.genKeyName(thumbfile);
				String tfid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", tfkn).getKey());
				thumbfile.setKeyName(tfkn);				
				gallery.setThumbFileId(tfid);
				
				gallery.setLongId(UUIDLongGenerator.newUUID());
				
			}
			
			// TODO temporary. to ensure image not empty
			if(galleries.size()>0) {
				GoodsGallery gallery = (GoodsGallery)galleries.iterator().next();
				to.setImageFileId(gallery.getImageFileId());
				// TODO temporary
				to.setThumbFileId(gallery.getThumbFileId());
			}
			
			Set<GoodsAttr> gts = to.getAttributes();
			for(GoodsAttr gt:gts) {
				String gkn = DataStoreUtils.genKeyName(gt);
				String gid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsAttr.class.getSimpleName(), gkn).getKey());
				gt.setKeyName(gkn);
//				gt.setId(gid);
				gt.setLongId(UUIDLongGenerator.newUUID());
			}
			String res = txattach(to);
			
			GoogleBaseUtil gbUtil = new GoogleBaseUtil(SpringUtil.getShopConfigManager().getCachedShopConfig("en"));
			 String token = gbUtil.authenticate();
			 gbUtil.buildDataItem(to);
			 String gbdid = gbUtil.postItem(token);
			 to.setGoogleBaseDataId(gbdid);
			
			
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
			super.getList(t1, ModelNames.GOODSGALLERY, criteria, -1, -1);
			System.out.println("size: "+t1.size());
			
			res = txattach(to);
			
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
    		
    		
    		Set<GoodsGallery> toGalleries = to.getGalleries();
			for(GoodsGallery gallery:toGalleries) {
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
					String gkn = DataStoreUtils.genKeyName(gallery);
					gallery.setKeyName(gkn);
					DSFile file = gallery.getImageFile();
					String fkn = DataStoreUtils.genKeyName(file);
					file.setKeyName(fkn);
					String fid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", fkn).getKey());
					gallery.setImageFileId(fid);
					
					DSFile thumbfile = gallery.getThumbFile();
					String tfkn = DataStoreUtils.genKeyName(thumbfile);
					String tfid = KeyFactory.keyToString(new KeyFactory.Builder("Goods",goodskn).addChild(GoodsGallery.class.getSimpleName(), gkn).addChild("DSFile", tfkn).getKey());
					thumbfile.setKeyName(tfkn);				
					gallery.setThumbFileId(tfid);
					
					gallery.setLongId(UUIDLongGenerator.newUUID());
					po.getGalleries().add(gallery);
				}
			}
			
			System.out.println("2) size of po.gallery: "+po.getGalleries().size());
			

			Set<GoodsGallery> galleries = po.getGalleries();
			if(galleries.size()>0) {
				GoodsGallery gallery = (GoodsGallery)(galleries.iterator().next());
				po.setImageFileId(gallery.getImageFileId());
				po.setThumbFileId(gallery.getThumbFileId());
			} else {
				po.setImageFileId(null);
			}
				
			
			po.getAttributes().clear();
			Set<GoodsAttr> gts = to.getAttributes();
			for(GoodsAttr gt:gts) {
				String gkn = DataStoreUtils.genKeyName(gt);
				gt.setKeyName(gkn);
				gt.setLongId(UUIDLongGenerator.newUUID());
				po.getAttributes().add(gt);
			}
			
			System.out.println("System.out.println(updateResponse);");
			txattach(po);
			
			GoogleBaseUtil gbUtil = new GoogleBaseUtil(SpringUtil.getShopConfigManager().getCachedShopConfig("en"));
			String token = gbUtil.authenticate();
			gbUtil.buildDataItem(po);
			String updateResponse = gbUtil.updateItem( token , po.getGoogleBaseDataId());
			System.out.println(updateResponse);
			
			return true;
    	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	
    	
    }
    
    public void getAreaRegionListWithName(List<AreaRegion> resultSet, String shippingAreaId){
    	try {
    		Criteria c = new Criteria();
    		c.addCondition(new Condition(IAreaRegion.SHIPPING_AREA, Condition.EQUALS, shippingAreaId));
    		List<AreaRegion> ars = super.getList(ModelNames.AREAREGION, c);
    		for(AreaRegion ar : ars) {
    			String regionId = ar.getRegionId();
    			Region region = (Region)super.get(ModelNames.REGION, regionId);
    			ar.setRegionName(region.getRegionName());
    			resultSet.add(ar);
    		}
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
