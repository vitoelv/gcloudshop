package com.jcommerce.gwt.client.resources;

import com.google.gwt.i18n.client.Constants;
import com.jcommerce.gwt.client.panels.article.ArticleCatListPanel;
import com.jcommerce.gwt.client.panels.article.ArticleCatPanel;
import com.jcommerce.gwt.client.panels.goods.AttributeListPanel;
import com.jcommerce.gwt.client.panels.goods.AttributePanel;
import com.jcommerce.gwt.client.panels.goods.BrandListPanel;
import com.jcommerce.gwt.client.panels.goods.BrandPanel;
import com.jcommerce.gwt.client.panels.goods.CategoryListPanel;
import com.jcommerce.gwt.client.panels.goods.CategoryPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsListPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsTypeListPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsTypePanel;
import com.jcommerce.gwt.client.panels.goods.CommentListPanel;
import com.jcommerce.gwt.client.panels.goods.CommentPanel;
import com.jcommerce.gwt.client.panels.member.ShippingAddressPanel;
import com.jcommerce.gwt.client.panels.member.UserListPanel;
import com.jcommerce.gwt.client.panels.member.UserPanel;
import com.jcommerce.gwt.client.panels.order.OrderUserPanel;
import com.jcommerce.gwt.client.panels.privilege.AdminListPanel;
import com.jcommerce.gwt.client.panels.privilege.AdminUserPanel;
import com.jcommerce.gwt.client.panels.system.PaymentMetaListPanel;
import com.jcommerce.gwt.client.panels.system.RegionListPanel;
import com.jcommerce.gwt.client.panels.system.RegionPanel;
import com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel;
import com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel;
import com.jcommerce.gwt.client.panels.data.ImportPanel;
import com.jcommerce.gwt.client.panels.data.ExportPanel;
import com.jcommerce.gwt.client.panels.order.OrderListPanel;
import com.jcommerce.gwt.client.panels.order.SearchOrderPanel;
import com.jcommerce.gwt.client.panels.order.MergeOrderPanel;
import com.jcommerce.gwt.client.panels.order.OrderDetailPanel;
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
			AttributePanel.Constants,RegionListPanel.Constants,UserListPanel.Constants,UserPanel.Constants,
			AdminUserPanel.Constants,AdminListPanel.Constants,ShippingAddressPanel.Constants,
			ArticleCatListPanel.Constants,
			ImportPanel.Constants, ExportPanel.Constants, OrderListPanel.Constants,
			CommentListPanel.Constants,CommentPanel.Constants,SearchOrderPanel.Constants,
			ArticleCatPanel.Constants, MergeOrderPanel.Constants, OrderDetailPanel.Constants
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
  String ManagementCenter();
  String mainTitle();
  
  String categoryGoods();
  String categoryOrder();
  String categorySystem();
  String categoryUser();
  String categoryPrivilege();
  String categoryData();
  String categoryArticle();
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
  
  String OperationFailure();
  String OperationSuccessful();
  
  String deleteConfirmTitle();
  String deleteConfirmContent();
  
}
