package com.jcommerce.gwt.client.form;

import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TextField.TextFieldMessages;
import com.jcommerce.gwt.client.model.IShopConfig;
import com.jcommerce.gwt.client.resources.Resources;

public class ShopConfigForm extends BeanObject implements IShopConfig {

	public static HiddenField<String> getIdField(){
		HiddenField<String> idField = new HiddenField<String>();
		idField.setName(ID);
		return idField;
	}
	
	public static TextField<String> getNameField(String fieldTitle){
		TextField<String> nameField = new TextField<String>();
		nameField.setName(NAME);
		nameField.setMaxLength(30);
		nameField.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = nameField.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		nameField.setMessages(tfm);
		nameField.setAutoValidate(true);
		return nameField;
	}
	
	public static TextField<String> getTitleField(String fieldTitle){
		TextField<String> nameField = new TextField<String>();
		nameField.setName(TITLE);
		nameField.setAllowBlank(true);
		nameField.setAutoValidate(true);
		return nameField;
	}

	public static TextField<String> getDescField(String fieldTitle){
		TextField<String> nameField = new TextField<String>();
		nameField.setName(DESC);
		nameField.setAllowBlank(true);
		nameField.setAutoValidate(true);
		return nameField;
	}

	public static TextField<String> getKeywordsField(String fieldTitle){
		TextField<String> nameField = new TextField<String>();
		nameField.setName(KEYWORDS);
		nameField.setAllowBlank(true);
		nameField.setAutoValidate(true);
		return nameField;
	}

	public static TextField<String> getAddressField(String fieldTitle){
		TextField<String> nameField = new TextField<String>();
		nameField.setName(ADDRESS);
		nameField.setAllowBlank(true);
		nameField.setAutoValidate(true);
		return nameField;
	}


}
