package com.jcommerce.core.service.config;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.service.impl.DefaultManagerImpl;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.web.to.ShopConfigWrapper;

public class ShopConfigManagerImpl extends DefaultManagerImpl implements IShopConfigManager {

	
	public static final SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap;
	

	
	/*
	 *  we define the data structure in order to keep the order it displayed on GUI. there are three concerns: 
	 *   1. order of groups
	 *   2. order of fields
	 *   3. order of selection options | radio/checkbox buttons
	 */
	static {
		defaultShopConfigMap = new TreeMap<Integer, List<ShopConfigMeta>>();
		List<ShopConfigMeta> metaList = null;
		
		metaList = new ArrayList<ShopConfigMeta>();
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_NAME,	"gCouldShop", CFG_TYPE_TEXT, "商店名称", null, null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_TITLE, "gCouldShop 演示站", CFG_TYPE_TEXT, "商店标题", null, "商店的标题将显示在浏览器的标题栏"));		
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_DESC, "gCouldShop 演示站", CFG_TYPE_TEXT, "商店描述", null, null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_KEYWORDS, "gCouldShop 演示站", CFG_TYPE_TEXT, "商店关键字", null, null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_COUNTRY, "1", CFG_TYPE_SELECT, "所在国家", 
				new String[][]{new String[]{"1","中国"}, new String[]{"2","美国"}}, null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_ADDRESS, "", CFG_TYPE_TEXT, "详细地址", null , null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_POSTCODE, "", CFG_TYPE_TEXT, "邮编", null , null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_QQ, "800120110,10001", CFG_TYPE_TEXT, "客服QQ号码", null , null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_WW, "911119991", CFG_TYPE_TEXT, "淘宝旺旺", null, null));	
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_SKYPE, "", CFG_TYPE_TEXT, "Skype", null, null));	
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_YM, "", CFG_TYPE_TEXT, "Yahoo Messenger", null, null));	
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_MSN, "", CFG_TYPE_TEXT, "MSN Messenger", null, null));	
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_SERVICE_EMAIL, "", CFG_TYPE_TEXT, "客服邮件地址", null, null));	
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_SERVICE_PHONE, "", CFG_TYPE_TEXT, "客服电话", null, null));	
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_NOTICE, 
				"欢迎光临手机网,我们的宗旨：诚信经营、服务客户！<MARQUEE onmouseover=this.stop() onmouseout=this.start() scrollAmount=3><U><FONT color=red><P>咨询电话010-10124444  010-21252454 8465544</P></FONT></U></MARQUEE>"
				, CFG_TYPE_TEXTAREA, "商店公告", null, "以上内容将显示在首页商店公告中,注意控制公告内容长度不要超过公告显示区域大小。"));
//		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_COUNTRY, "", CFG_TYPE_OPTIONS, "所在国家", null, null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_COPYRIGHT, "Copyright © 2008-2009 GCSHOP 版权所有，并保留所有权利。", CFG_TYPE_TEXT, "版权信息", null, null));	
		defaultShopConfigMap.put(1, metaList);
		
		metaList = new ArrayList<ShopConfigMeta>();
		metaList.add(new ShopConfigMeta(CFG_GROUP_DISPLAY, CFG_KEY_TIME_FORMAT, "yyyy-MMM-dd", CFG_TYPE_TEXT, "时间格式", null, null));
		defaultShopConfigMap.put(3, metaList);
		
		metaList = new ArrayList<ShopConfigMeta>();
		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOPPING_FLOW, CFG_KEY_CART_CONFIRM, "3", CFG_TYPE_OPTIONS, "购物车确定提示", 
				new String[][]{new String[]{"1","提示用户，点击“确认”进购物车"}, new String[]{"2","提示用户，点击“取消”进购物车"},
				new String[]{"3","直接进入购物车"}, new String[]{"4","不提示并停留在当前页面"}}, null));
		defaultShopConfigMap.put(4, metaList);
		
		metaList = new ArrayList<ShopConfigMeta>();
		metaList.add(new ShopConfigMeta(CFG_GROUP_GOODS, CFG_KEY_SHOW_GOODSWEIGHT, "1", CFG_TYPE_SELECT, "是否显示重量", 
				new String[][]{new String[]{"1","显示"}, new String[]{"0","不显示"}}, null));
		metaList.add(new ShopConfigMeta(CFG_GROUP_GOODS, CFG_KEY_SHOW_MARKETPRICE, "1", CFG_TYPE_SELECT, "是否显示市场价格", 
				new String[][]{new String[]{"1","显示"}, new String[]{"0","不显示"}}, null));	
		defaultShopConfigMap.put(5, metaList);
	}
	
	public SortedMap<Integer, List<ShopConfigMeta>> getCombinedShopConfigMetaMap() {
//		SortedMap<Integer, List<ShopConfigMeta>> res = new TreeMap<Integer, List<ShopConfigMeta>>();
		try {
			List<ShopConfig> scs = getList(ModelNames.SHOPCONFIG, null);
			Map<String, ShopConfig> values = new HashMap<String, ShopConfig>();
			for(ShopConfig sc : scs) {
				String code = sc.getCode();
				values.put(code, sc);
			}
			
			for(Integer key : defaultShopConfigMap.keySet()) {
				List<ShopConfigMeta> defaultList = defaultShopConfigMap.get(key);
				for(ShopConfigMeta defaultt : defaultList) {
					String code = defaultt.getCode();
					ShopConfig sc = values.get(code);
					if(sc!=null) {
						defaultt.setPkId(sc.getPkId());
						defaultt.setValue(sc.getValue());
					}
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return defaultShopConfigMap;
	}
	
	public Boolean saveShopConfig(List<ShopConfig> tos) {
		try {
			for(ShopConfig to : tos) {
				String pkId = to.getPkId();
				String code = to.getCode();
				String value = to.getValue();
				if(StringUtils.isEmpty(pkId)) {
					ShopConfig po = new ShopConfig();
					po.setCode(code);
					po.setValue(value);
					txadd(po);
				}else {
					ShopConfig po = (ShopConfig)get(ModelNames.SHOPCONFIG, pkId);
					po.setValue(value);
					txattach(po);
				}
			}
			
			isCacheValid = false;
			
			return true;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		
	}
	
	public boolean isCacheValid=false;
	public ShopConfigWrapper cachedScw = new ShopConfigWrapper();
	public ShopConfigWrapper getCachedShopConfig() {
		try {
			
			if (!isCacheValid) {
				cachedScw.clear();
				// concurrent access entering this block will cause problem
				// however it's ok since ShopConfig won't be often changed.
				
				// another way is to store the cache in session so that concurrent users won't affect each other
				// however that would be more expensive and maynot 100% solve the problem, 
				// (confliction happens when concurrent read/write DS happens and we may not have transaction protect)
				
				SortedMap<Integer, List<ShopConfigMeta>> map = getCombinedShopConfigMetaMap();
				for (List<ShopConfigMeta> list : map.values()) {
					for (ShopConfigMeta scm : list) {
						String code = scm.getCode();
						String value = scm.getValue();
						cachedScw.put(code, value);
					}
				}
				isCacheValid = true;
			}
			return cachedScw;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
