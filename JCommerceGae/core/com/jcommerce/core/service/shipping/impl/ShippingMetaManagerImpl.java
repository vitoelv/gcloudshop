package com.jcommerce.core.service.shipping.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.datanucleus.util.StringUtils;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.AreaRegion;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.service.impl.DefaultManagerImpl;
import com.jcommerce.core.service.shipping.IShippingMetaManager;
import com.jcommerce.core.service.shipping.IShippingMetaPlugin;
import com.jcommerce.core.service.shipping.ShippingAreaMeta;
import com.jcommerce.core.service.shipping.ShippingConfigMeta;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.core.util.UUIDHexGenerator;
import com.jcommerce.core.util.UUIDLongGenerator;
import com.jcommerce.gwt.client.ModelNames;

public class ShippingMetaManagerImpl extends DefaultManagerImpl implements IShippingMetaManager {
    private DAO dao;
    private String pluginFolder;
    
    public ShippingMetaManagerImpl() {
    	super();
    	init();
    }
    
    Map<String, IShippingMetaPlugin> metaRepo;
    public void init() {
        // TODO read pluginFolder and load modules:
//        File folder = new File(pluginFolder);
//        File[] pluginInfos = folder.listFiles();
//        for(File pluginInfo:pluginInfos) {
//            
//        }
        
        metaRepo = new HashMap<String, IShippingMetaPlugin>();
        EMS ems = new EMS();
        metaRepo.put(ems.getDefaultConfigMeta().getShippingCode(), ems);
        YuanTong yto = new YuanTong();
        metaRepo.put(yto.getDefaultConfigMeta().getShippingCode(), yto);

    }
    
    public double calculate(String shippingCode, double goodsWeight, double goodsAmount, Map<String, String> configValues) {
    	IShippingMetaPlugin plugin = metaRepo.get(shippingCode);
    	if(plugin == null) {
    		throw new RuntimeException("shipping plugin code: "+shippingCode+" is not loaded!!");
    	}
    	return plugin.calculate(goodsWeight, goodsAmount, configValues);
    }
    
	public List<ShippingConfigMeta> getCombinedShippingMetaList() {
        // 已加载的
        
        // 已安装到数据库中的
        List<Shipping> listData = dao.getList(ModelNames.SHIPPING, null);
        Map<String, Shipping> mapData = new HashMap<String, Shipping>();
        for(Shipping shipping:listData) {
            mapData.put(shipping.getShippingCode(), shipping);
        }
        
        List<ShippingConfigMeta> res = new ArrayList<ShippingConfigMeta>();
        ShippingConfigMeta meta = null;
        
        for(String code:metaRepo.keySet()) {
            IShippingMetaPlugin plugin = metaRepo.get(code);
            meta = new ShippingConfigMeta();
            
            if(mapData.containsKey(code)) {
                // 已在数据库中，使用数据库中的值
                Shipping shipping = mapData.get(code);
                meta.setPkId(shipping.getPkId());
                meta.setShippingName(shipping.getShippingName());
                meta.setShippingCode(shipping.getShippingCode());
                meta.setShippingDesc(shipping.getShippingDesc());
                meta.setSupportCod(shipping.getSupportCod());
                meta.setInstall(true);
            }
            else {
                meta.setShippingName(plugin.getDefaultConfigMeta().getShippingName());
                meta.setShippingCode(plugin.getDefaultConfigMeta().getShippingCode());
                meta.setShippingDesc(plugin.getDefaultConfigMeta().getShippingDesc());
                meta.setSupportCod(plugin.getDefaultConfigMeta().getSupportCod());
                meta.setInstall(false);
            }
            meta.setAuthor(plugin.getDefaultConfigMeta().getAuthor());
            meta.setWebsite(plugin.getDefaultConfigMeta().getWebsite());
            meta.setVersion(plugin.getDefaultConfigMeta().getVersion());
            res.add(meta);
            
        }
        return res;
	}

	public List<ShippingConfigMeta> getInstalledShippingMetaList() {
		try {
			List<ShippingConfigMeta> res = new ArrayList<ShippingConfigMeta>();
			List<Shipping> payments = getShippingList();
			for (Shipping payment : payments) {
				String code = payment.getShippingCode();
				IShippingMetaPlugin plugin = metaRepo.get(code);
//				ShippingConfigMeta meta = plugin.deserializeConfig(payment.getPayConfig());
				// copy common fields
//				BeanUtils.copyProperties(meta, payment);
//				res.add(meta);
			}
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public ShippingConfigMeta getShippingConfigMeta(String shippingId) {
        try {
            ShippingConfigMeta res = new ShippingConfigMeta();
            Shipping shipping = (Shipping)dao.get(ModelNames.SHIPPING, shippingId);

            // copy common fields
            BeanUtils.copyProperties(res, shipping);

            String code = shipping.getShippingCode();
            IShippingMetaPlugin plugin = metaRepo.get(code);
            if(StringUtils.isEmpty(res.getShippingPrint())) {
            	// fill with plugin default
            	res.setShippingPrint(plugin.getDefaultConfigMeta().getShippingPrint());
            }
            
            return res; 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	public ShippingAreaMeta getShippingAreaMeta(String shippingAreaId, String shippingId) {
        try {
        	System.out.println("shippingAreaId: "+shippingAreaId+", shippingId: "+shippingId);
            Shipping s = (Shipping)dao.get(ModelNames.SHIPPING, shippingId);
            String code = s.getShippingCode();
            
            IShippingMetaPlugin plugin = metaRepo.get(code);

        	
        	ShippingArea sa;
        	ShippingAreaMeta res;
        	if(shippingId == null) {
        		throw new RuntimeException("shippingId is NULL!");
        	}
        	if(shippingAreaId == null) {
        		sa = new ShippingArea();
        		res = plugin.getDefaultAreaMeta(); 
        	}
        	else {
        		sa = (ShippingArea)dao.get(ModelNames.SHIPPINGAREA, shippingAreaId);
        		res = plugin.deserializeConfig(sa.getConfigure());
        	}

            
            // copy common fields
            BeanUtils.copyProperties(res, sa);            
            
            return res; 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	
	public List<Shipping> getShippingList() {
		return (List<Shipping>)getDao().getList(ModelNames.SHIPPING, null);
	}

	public void install(String shippingCode) {
        try {
            
            IShippingMetaPlugin plugin = metaRepo.get(shippingCode);
            Shipping obj = new Shipping();
            ShippingConfigMeta meta = plugin.getDefaultConfigMeta();
            obj.setSupportCod(meta.getSupportCod());
            obj.setShippingCode(meta.getShippingCode());
            obj.setShippingName(meta.getShippingName());
            obj.setShippingDesc(meta.getShippingDesc());
            obj.setInsure(meta.getInsure());
            obj.setEnabled(true);
            dao.add(obj);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("install error:"+e.getMessage());
            throw e;
        }

	}

	public void saveShippingConfig(ShippingConfigMeta meta) {
        try {
            Shipping shipping = new Shipping();
            BeanUtils.copyProperties(shipping, meta);

            dao.update(shipping);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }

	}

	public void uninstall(String shippingId) {
        try {
        	dao.delete(ModelNames.SHIPPING, shippingId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
	}
    public boolean saveShippingArea(ShippingArea to, Map<String, Object> props) {
    	try {
			String saId = to.getPkId();
			String shippingId = to.getShipping().getPkId();
			Shipping shipping = (Shipping)get(ModelNames.SHIPPING, shippingId);
			String code = shipping.getShippingCode();
			IShippingMetaPlugin plugin = metaRepo.get(code);
			String configure = plugin.serializeConfig(props);
			to.setConfigure(configure);
			
			if (saId == null) {
				// alternatively, we could call txAdd and let the parent be attached there
				to.setShipping(shipping);
				
				// new
				String sakn = UUIDHexGenerator.newUUID();
				to.setKeyName(sakn);
				to.setLongId(UUIDLongGenerator.newUUID());
				
				for(AreaRegion ar : to.getAreaRegions()) {
					String arkn = UUIDHexGenerator.newUUID();	
//				String arid = KeyFactory.keyToString(new KeyFactory.Builder("ShippingArea",sakn).
//									addChild("AreaRegion", arkn).getKey());
					ar.setKeyName(arkn);
					ar.setLongId(UUIDLongGenerator.newUUID());
				}
		
				String res = txattach(to);
				
				// verify,  debug only 
				for(AreaRegion ar : to.getAreaRegions()) {
					System.out.println("arId: "+ar.getPkId());
				}
				saId = to.getPkId();
				System.out.println("saId="+saId);
			} else {
				
				ShippingArea po = (ShippingArea)get(ModelNames.SHIPPINGAREA, saId);
				MyPropertyUtil.copySimpleProperties(po, to);
				po.getAreaRegions().clear();
				for(AreaRegion ar : to.getAreaRegions()) {
					String arkn = UUIDHexGenerator.newUUID();	
//				String arid = KeyFactory.keyToString(new KeyFactory.Builder("ShippingArea",sakn).
//									addChild("AreaRegion", arkn).getKey());
					ar.setKeyName(arkn);
					ar.setLongId(UUIDLongGenerator.newUUID());
					po.getAreaRegions().add(ar);
				}
				txattach(po);
			}
			return true;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
    }
	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}

	public String getPluginFolder() {
		return pluginFolder;
	}

	public void setPluginFolder(String pluginFolder) {
		this.pluginFolder = pluginFolder;
	}

}
