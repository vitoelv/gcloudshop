package com.jcommerce.gwt.client.panels.system;

public interface IShopConfigMeta {
	
	public static final String CODE = "code";
	
	public static final String STORE_RANGE = "storeRange";
	public static final String TYPE = "type";
	public static final String LABEL = "label";
	public static final String GROUP = "group";
	public static final String TIP = "tip";
	
	public static final String PK_ID = "pkId";
	public static final String VALUE = "value";
	
	// TODO externalize the text string for g18n
	public static final String CFG_GROUP_SHOP_INFO = "网店信息";
	public static final String CFG_GROUP_BASIC = "基本设置";
	public static final String CFG_GROUP_DISPLAY = "显示设置";
	public static final String CFG_GROUP_SHOPPING_FLOW = "购物流程";
	public static final String CFG_GROUP_GOODS = "商品显示";
	
	// basic
	public static final String CFG_KEY_SHOP_NAME = "shop_name";
	public static final String CFG_KEY_SHOP_TITLE = "shop_title";
	public static final String CFG_KEY_SHOP_DESC = "shop_desc";
	public static final String CFG_KEY_SHOP_KEYWORDS = "shop_keywords";
	public static final String CFG_KEY_SHOP_NOTICE = "shop_notice";
	public static final String CFG_KEY_SHOP_COUNTRY = "shop_country";
	public static final String CFG_KEY_SHOP_QQ = "qq";
	public static final String CFG_KEY_SHOP_WW = "ww";
	public static final String CFG_KEY_SHOP_YM = "ym";
	public static final String CFG_KEY_SHOP_MSN = "msn";
	public static final String CFG_KEY_SHOP_SKYPE = "skype";
	
	// goods
	public static final String CFG_KEY_SHOW_MARKETPRICE = "showMarketprice";
	public static final String CFG_KEY_SHOW_GOODSWEIGHT = "showGoodsweight";
	
	// shopping_flow
	public static final String CFG_KEY_CART_CONFIRM = "cart_confirm";
	
	// display
	public static final String CFG_KEY_TIME_FORMAT = "time_format";
	public static final String CFG_KEY_COMMENT_CHECK = "comment_check";
	
	public static final String CFG_TYPE_SELECT = "select";
	public static final String CFG_TYPE_TEXT = "text";
	public static final String CFG_TYPE_TEXTAREA = "textArea";
	public static final String CFG_TYPE_OPTIONS = "options";
	
}
