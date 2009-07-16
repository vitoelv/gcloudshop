package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.resources.Resources;

public class GoodsForm extends BeanObject implements IGoods {
	
	public GoodsForm() {
		super();
	}
	public GoodsForm(String modelName) {
		super(modelName);
	}
	public GoodsForm(String modelName, Map<String, Object>props) {
		super(modelName,props);
	}
	public static HiddenField<String> getIdField() {
		HiddenField<String> idField = new HiddenField<String>();
		idField.setName(ID);
		return idField;
	}
	
	public static TextField<String> getNameField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(NAME);
		field.setMaxLength(10);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	
	public static ComboBox<BeanObject> getBrandIdField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName(BRANDID);
		field.setDisplayField(IBrand.NAME);
        field.setValueField(IBrand.ID);
		return field;
	}
	public static ListField<BeanObject> getCategoryIdsField() {
		ListField<BeanObject> field = new ListField<BeanObject>();
		field.setName(CATEGORYIDS);
        field.setDisplayField(ICategory.NAME);
        field.setValueField(ICategory.ID);
		return field;
	}
	public static TextField<String> getSnField() {
		TextField<String> field = new TextField<String>();
		field.setName(SN);
		return field;
	}
	
	public static FileUploadField getImageField() {
		FileUploadField field = new FileUploadField();
		field.setName(IMAGE);
		return field;
	}
	public static FileUploadField getThumbField() {
		FileUploadField field = new FileUploadField();
		field.setName(THUMB);
		return field;
	}
	public static TextField<String> getWeightField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(WEIGHT);
		return field;
	}
	
	public static TextField<String> getNumberField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(NUMBER);
		return field;
	}
	
	public static CheckBox getHotSoldField() {
		CheckBox field = new CheckBox();
		field.setName(HOTSOLD);
		field.setAutoValidate(true);
		return field;
	}
	public static CheckBox getNewAddedField() {
		CheckBox field = new CheckBox();
		field.setName(NEWADDED);
		field.setAutoValidate(true);
		return field;
	}
	public static CheckBox getBestSoldField() {
		CheckBox field = new CheckBox();
		field.setName(BESTSOLD);
		field.setAutoValidate(true);
		return field;
	}
	
	public static HtmlEditor getDescField() {
		HtmlEditor field = new HtmlEditor();
		field.setName(DESCRIPTION);
		field.setAutoValidate(true);
		return field;
	}
}
