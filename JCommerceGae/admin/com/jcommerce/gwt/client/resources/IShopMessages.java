package com.jcommerce.gwt.client.resources;

import com.google.gwt.i18n.client.Messages;
import com.jcommerce.gwt.client.form.GoodsTypeForm;
import com.jcommerce.gwt.client.panels.goods.CommentPanel;
import com.jcommerce.gwt.client.panels.system.RegionPanel;


public interface IShopMessages extends Messages,
		GoodsTypeForm.GoodsTypeFormMessage, CommentPanel.CommentPanelMessage,
		RegionPanel.Message {
	String blankText(String fieldTitle);
	String emailFormatText();
}
