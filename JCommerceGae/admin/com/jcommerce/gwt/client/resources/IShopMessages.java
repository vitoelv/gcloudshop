package com.jcommerce.gwt.client.resources;

import com.google.gwt.i18n.client.Messages;
import com.jcommerce.gwt.client.form.GoodsTypeForm;
import com.jcommerce.gwt.client.panels.goods.CommentPanel;
import com.jcommerce.gwt.client.panels.system.RegionPanel;
import com.jcommerce.gwt.client.panels.order.MergeOrderPanel;
import com.jcommerce.gwt.client.panels.order.OrderGoodsPanel;
import com.jcommerce.gwt.client.panels.order.OrderListPanel;


public interface IShopMessages extends Messages,
		GoodsTypeForm.GoodsTypeFormMessage, CommentPanel.CommentPanelMessage,
		RegionPanel.Message, MergeOrderPanel.MergeOrderMessage, OrderGoodsPanel.Message 
		,OrderListPanel.Message{
	String blankText(String fieldTitle);
	String emailFormatText();
}
