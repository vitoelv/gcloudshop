package com.jcommerce.core.service.config;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_ARTICLE_NUMBER;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_CART_CONFIRM;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_COMMENTS_NUMBER;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_COMMENT_CHECK;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_HISTORY_NUMBER;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_ONE_STEP_BUY;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_PAGE_SIZE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_ADDRESS;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_COPYRIGHT;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_COUNTRY;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_DESC;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_KEYWORDS;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_MSN;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_NAME;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_NOTICE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_POSTCODE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_QQ;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_SERVICE_EMAIL;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_SERVICE_PHONE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_SKYPE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_TITLE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_WW;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOP_YM;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOW_GOODSWEIGHT;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOW_GOODS_IN_CART;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOW_MARKETPRICE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SHOW_ORDER_TYPE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SORT_ORDER_METHOD;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_SORT_ORDER_TYPE;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_KEY_TIME_FORMAT;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_TYPE_OPTIONS;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_TYPE_SELECT;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_TYPE_TEXT;
import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.CFG_TYPE_TEXTAREA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.service.impl.DefaultManagerImpl;
import com.jcommerce.core.util.ResourceUtil;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.to.ShopConfigWrapper;

public class ShopConfigManagerImpl extends DefaultManagerImpl implements IShopConfigManager {


	private static Locale parseLocale(String locale) {
		if(StringUtils.isBlank(locale)) {
			locale = "zh"; // by default
		}
		try {
			Locale loc = new Locale(locale);
			return loc;
		} catch (Exception ex) {
			return Locale.CHINESE;
		}
		
	}
	
	public SortedMap<Integer, List<ShopConfigMeta>> getCombinedShopConfigMetaMap(String locale) {
//		SortedMap<Integer, List<ShopConfigMeta>> res = new TreeMap<Integer, List<ShopConfigMeta>>();
		SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap = getDefaultShopConfigMap(parseLocale(locale));
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
	public ShopConfigWrapper getCachedShopConfig(String locale) {
		try {
			
			if (!isCacheValid) {
				cachedScw.clear();
				// concurrent access entering this block will cause problem
				// however it's ok since ShopConfig won't be often changed.
				
				// another way is to store the cache in session so that concurrent users won't affect each other
				// however that would be more expensive and maynot 100% solve the problem, 
				// (confliction happens when concurrent read/write DS happens and we may not have transaction protect)
				SortedMap<Integer, List<ShopConfigMeta>> map = getCombinedShopConfigMetaMap(locale);
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


	
	private static final SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap_en;
	private static final SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap_zh;
	
	static {
		// TODO support two languages at this time
		defaultShopConfigMap_en = initDefaultShopConfigMap(Locale.ENGLISH);
		defaultShopConfigMap_zh = initDefaultShopConfigMap(Locale.CHINESE);
	}
	
	public static SortedMap<Integer, List<ShopConfigMeta>> getDefaultShopConfigMap(Locale locale) {
		if(locale.equals(Locale.ENGLISH)) {
			return defaultShopConfigMap_en;
		}
		else {
			return defaultShopConfigMap_zh;
		}
	}
	
	public static SortedMap<Integer, List<ShopConfigMeta>> initDefaultShopConfigMap(Locale locale) {
		/*
		 *  we define the data structure in order to keep the order it displayed on GUI. there are three concerns: 
		 *   1. order of groups
		 *   2. order of fields
		 *   3. order of selection options | radio/checkbox buttons
		 */
		
		SortedMap<Integer, List<ShopConfigMeta>> defaultShopConfigMap = new TreeMap<Integer, List<ShopConfigMeta>>();
		List<ShopConfigMeta> metaList = null;
		ResourceBundle bundle = ResourceUtil.getShopConfigResource(locale);
		
		// TODO use and i18n of tip string (last parameter of ShopConfigMeta)
		metaList = new ArrayList<ShopConfigMeta>();
//		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_NAME,	"gCouldShop", CFG_TYPE_TEXT, "商店名称", null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_NAME,	"gCouldShop", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_NAME"), null, null));
//		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_TITLE, "gCouldShop 演示站", CFG_TYPE_TEXT, "商店标题", null, "商店的标题将显示在浏览器的标题栏"));		
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_TITLE, "gCouldShop 演示站", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_TITLE"), null, bundle.getString("CFG_KEY_SHOP_TITLE_NOTICE")));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_DESC, "gCouldShop 演示站", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_DESC"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_KEYWORDS, "gCouldShop 演示站", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_KEYWORDS"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_COUNTRY, "1", CFG_TYPE_OPTIONS, bundle.getString("CFG_KEY_SHOP_COUNTRY"), 
				new String[][]{new String[]{"1",bundle.getString("CFG_KEY_SHOP_COUNTRY_CHINA")}, new String[]{"2",bundle.getString("CFG_KEY_SHOP_COUNTRY_AMERICA")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_ADDRESS, "", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_ADDRESS"), null , null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_POSTCODE, "", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_POSTCODE"), null , null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_QQ, "800120110,10001", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_QQ"), null , bundle.getString("CFG_KEY_SHOP_QQ_NOTICE")));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_WW, "911119991", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_WW"), null, bundle.getString("CFG_KEY_SHOP_WW_NOTICE")));	
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_SKYPE, "", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_SKYPE"), null, bundle.getString("CFG_KEY_SHOP_SKYPE_NOTICE")));	
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_YM, "", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_YM"), null, bundle.getString("CFG_KEY_SHOP_YM_NOTICE")));	
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_MSN, "", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_MSN"), null, bundle.getString("CFG_KEY_SHOP_MSN_NOTICE")));	
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_SERVICE_EMAIL, "", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_SERVICE_EMAIL"), null, null));	
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_SERVICE_PHONE, "", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_SERVICE_PHONE"), null, null));	
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_USER_NOTICE, 
				"用户中心公告！"	, CFG_TYPE_TEXTAREA, bundle.getString("CFG_KEY_USER_NOTICE"), null, bundle.getString("CFG_KEY_USER_NOTICE_NOTICE")));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_NOTICE, 
				"欢迎光临手机网,我们的宗旨：诚信经营、服务客户！<MARQUEE onmouseover=this.stop() onmouseout=this.start() scrollAmount=3><U><FONT color=red><P>咨询电话010-10124444  010-21252454 8465544</P></FONT></U></MARQUEE>"
				, CFG_TYPE_TEXTAREA, bundle.getString("CFG_KEY_SHOP_NOTICE"), null, bundle.getString("CFG_KEY_SHOP_NOTICE_NOTICE")));
//		metaList.add(new ShopConfigMeta(CFG_GROUP_SHOP_INFO, CFG_KEY_SHOP_COUNTRY, "", CFG_TYPE_OPTIONS, "所在国家", null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), CFG_KEY_SHOP_COPYRIGHT, "Copyright © 2008-2009 GCSHOP 版权所有，并保留所有权利。", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_SHOP_COPYRIGHT"), null, null));	
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOP_INFO"), IShopConfigMeta.CFG_KEY_SHOP_REG_CLOSED, "0", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOP_REG_CLOSED"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
		
		defaultShopConfigMap.put(1, metaList);

		
		metaList = new ArrayList<ShopConfigMeta>();
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_BASIC"), CFG_KEY_COMMENT_CHECK, "1", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_COMMENT_CHECK"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_BASIC"), IShopConfigMeta.CFG_KEY_INTEGRAL_NAME, "积分", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_INTEGRAL_NAME"), null,  bundle.getString("CFG_KEY_INTEGRAL_NAME_NOTICE")));
		defaultShopConfigMap.put(2, metaList);
		
		metaList = new ArrayList<ShopConfigMeta>();
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), CFG_KEY_TIME_FORMAT, "yyyy-MMM-dd", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_TIME_FORMAT"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), CFG_KEY_COMMENTS_NUMBER, "5", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_COMMENTS_NUMBER"), null, bundle.getString("CFG_KEY_COMMENTS_NUMBER_NOTICE")));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), CFG_KEY_HISTORY_NUMBER, "5", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_HISTORY_NUMBER"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_COLLECTION_NUMBER, "5", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_COLLECTION_NUMBER"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), CFG_KEY_ARTICLE_NUMBER, "8", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_ARTICLE_NUMBER"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), CFG_KEY_PAGE_SIZE, "10", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_PAGE_SIZE"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), CFG_KEY_SORT_ORDER_TYPE, "shop_price", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SORT_ORDER_TYPE"), 
				new String[][]{new String[]{"goods_id",bundle.getString("CFG_SORT_GOODS_ID")}, new String[]{"shop_price",bundle.getString("CFG_SORT_SHOP_PRICE")}, new String[]{"last_update",bundle.getString("CFG_SORT_LAST_UPDATE")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), CFG_KEY_SORT_ORDER_METHOD, "ASC", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SORT_ORDER_TYPE"), 
				new String[][]{new String[]{"DESC",bundle.getString("CFG_SORT_DESC")}, new String[]{"ASC",bundle.getString("CFG_SORT_ASC")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), CFG_KEY_SHOW_ORDER_TYPE, "grid", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SORT_ORDER_TYPE"), 
				new String[][]{new String[]{"list",bundle.getString("CFG_SHOW_LIST")}, new String[]{"grid",bundle.getString("CFG_SHOW_GRID")}, new String[]{"text",bundle.getString("CFG_SHOW_TEXT")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_NAME_OF_REGION_1, "国家", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_NAME_OF_REGION_1"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_NAME_OF_REGION_2, "省", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_NAME_OF_REGION_2"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_NAME_OF_REGION_3, "市", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_NAME_OF_REGION_3"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_NAME_OF_REGION_4, "区", CFG_TYPE_TEXT, bundle.getString("CFG_KEY_NAME_OF_REGION_4"), null, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_DISPLAY"), IShopConfigMeta.CFG_KEY_PAGE_STYLE, "0", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_PAGE_STYLE"), 
				new String[][]{new String[]{"0",bundle.getString("CFG_PAGE_STYLE_1")}, new String[]{"1",bundle.getString("CFG_PAGE_STYLE_2")}}, null));
		defaultShopConfigMap.put(3, metaList);
		
		metaList = new ArrayList<ShopConfigMeta>();
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), CFG_KEY_CART_CONFIRM, "3", CFG_TYPE_OPTIONS, bundle.getString("CFG_KEY_CART_CONFIRM"), 
				new String[][]{new String[]{"1",bundle.getString("CFG_KEY_CART_CONFIRM_TYPE_1")}, new String[]{"2",bundle.getString("CFG_KEY_CART_CONFIRM_TYPE_2")},
				new String[]{"3",bundle.getString("CFG_KEY_CART_CONFIRM_TYPE_3")}, new String[]{"4",bundle.getString("CFG_KEY_CART_CONFIRM_TYPE_4")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), CFG_KEY_ONE_STEP_BUY, "0", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_ONE_STEP_BUY"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), CFG_KEY_SHOW_GOODS_IN_CART, "3", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOW_GOODS_IN_CART"), new String[][]{new String[]{"1",bundle.getString("CFG_SHOW_GOODS_IN_CART_TEXT")}, new String[]{"2",bundle.getString("CFG_SHOW_GOODS_IN_CART_IMG")}, new String[]{"3",bundle.getString("CFG_SHOW_GOODS_IN_CART_BOTH")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_SHOPPING_FLOW"), IShopConfigMeta.CFG_KEY_SHOW_GOODS_ATTRIBUTE, "0", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOW_GOODS_ATTRIBUTE"), new String[][]{new String[]{"1",bundle.getString("Yes")}, new String[]{"0",bundle.getString("No")}}, null));
		defaultShopConfigMap.put(4, metaList);
		
		metaList = new ArrayList<ShopConfigMeta>();
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_GOODS"), CFG_KEY_SHOW_GOODSWEIGHT, "1", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOW_GOODSWEIGHT"), 
				new String[][]{new String[]{"1",bundle.getString("CFG_DISPLAY")}, new String[]{"0",bundle.getString("CFG_CONCEAL")}}, null));
		metaList.add(new ShopConfigMeta(bundle.getString("CFG_GROUP_GOODS"), CFG_KEY_SHOW_MARKETPRICE, "1", CFG_TYPE_SELECT, bundle.getString("CFG_KEY_SHOW_MARKETPRICE"), 
				new String[][]{new String[]{"1",bundle.getString("CFG_DISPLAY")}, new String[]{"0",bundle.getString("CFG_CONCEAL")}}, null));	
		defaultShopConfigMap.put(5, metaList);
		return defaultShopConfigMap;
	}


}
