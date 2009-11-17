package com.jcommerce.gwt.client.resources;

import com.google.gwt.i18n.client.Constants;
import com.jcommerce.gwt.client.panels.AttributeListPanel;
import com.jcommerce.gwt.client.panels.GoodsListPanel;
import com.jcommerce.gwt.client.panels.GoodsTypeListPanel;
import com.jcommerce.gwt.client.panels.RegionPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsPanel;
import com.jcommerce.gwt.client.panels.order.OrderUserPanel;
import com.jcommerce.gwt.client.panels.system.PaymentMetaListPanel;
import com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel;
import com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel;
import com.jcommerce.gwt.client.panels.BrandPanel;
import com.jcommerce.gwt.client.panels.GoodsTypePanel;
import com.jcommerce.gwt.client.panels.BrandListPanel;
import com.jcommerce.gwt.client.panels.CategoryPanel;
import com.jcommerce.gwt.client.panels.CategoryListPanel;
import com.jcommerce.gwt.client.panels.AttributePanel;
import com.jcommerce.gwt.client.panels.RegionListPanel;
/**
 * Constants used throughout the showcase.
 */

public interface IShopConstants extends Constants, 
			AttributeListPanel.Constants, ShopConfigConstants,OrderConstants, 
			PaymentMetaListPanel.Constants, GoodsTypeListPanel.Constants, 
			OrderUserPanel.Constants, 
			GoodsListPanel.Constants, GoodsPanel.Constants,
			RegionPanel.Constants, ShippingMetaListPanel.Constants,
			ShippingAreaListPanel.Constants,BrandPanel.Constants,GoodsTypePanel.Constants,
			BrandListPanel.Constants,CategoryPanel.Constants,CategoryListPanel.Constants,
			AttributePanel.Constants,RegionListPanel.Constants
			{

	
	boolean useJDO = true;
	
  /**
   * The available style themes that the user can select.
   */
  String[] STYLE_THEMES = {"standard", "chrome", "dark"};

  String mainMenuTitle();

  /**
   * @return the title of the application
   */
  String mainTitle();
  
  String categoryGoods();
  String categoryOrder();
  String categoryMember();
  String categorySystem();
  String categoryStatistics();
  
  String mainLinkHomepage();
  String mainLinkGuide();
  String mainLinkNotepad();
  String mainLinkRefresh();
  String mainLinkPersonalSetting();
  String mainLinkComment();
  String mainLinkView();
  String mainLinkCalc();
  String mainLinkAbout();

  String mainCommandClearCache();
  String mainCommandExit();

  String navHome();
  String navSetting();
  String navGoodsList();
  String navOrderList();
  String navComment();
  String navMemberList();
  
  String edit();
  String delete();
  String deleteSelected();  
  String save();
  String add();
  String reset();
  String action();
  String ok();

  
}
