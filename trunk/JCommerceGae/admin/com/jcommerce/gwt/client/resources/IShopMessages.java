package com.jcommerce.gwt.client.resources;

import com.google.gwt.i18n.client.Messages;
import com.jcommerce.gwt.client.form.GoodsTypeForm;
import com.jcommerce.gwt.client.panels.RegionPanel;
import com.jcommerce.gwt.client.panels.ShopConfigPanel;

public interface IShopMessages extends Messages,
		GoodsTypeForm.GoodsTypeFormMessage, ShopConfigPanel.Message, 
		RegionPanel.Message {
	String blankText(String fieldTitle);
}
