package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.widgets.MyHTMLEditor;

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
		idField.setName(PK_ID);
		return idField;
	}
	
	public static TextField<String> getNameField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(GOODS_NAME);
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
		field.setName(BRAND_ID);
		field.setDisplayField(IBrand.BRAND_NAME);
        field.setValueField(IBrand.PK_ID);
		return field;
	}
	
	public static ComboBox<BeanObject> getGoodsTypeIdField() {
		ComboBox<BeanObject> field = new ComboBox<BeanObject>();
		field.setName(GOODS_TYPE_ID);
		field.setDisplayField(IGoodsType.CAT_NAME);
        field.setValueField(IGoodsType.PK_ID);
		return field;
	}
	
	public static ListField<BeanObject> getCategoryIdsField() {
		ListField<BeanObject> field = new ListField<BeanObject>();
		field.setName(CATEGORY_IDS);
        field.setDisplayField(ICategory.CAT_NAME);
        field.setValueField(ICategory.PK_ID);
		return field;
	}
	public static TextField<String> getSnField() {
		TextField<String> field = new TextField<String>();
		field.setName(GOODS_SN);
		return field;
	}
	
	public static NumberField getShopPriceField() {
		NumberField field = new NumberField();
		field.setName(SHOP_PRICE);
		field.setAllowNegative(false);
		return field;
	}
	public static NumberField getMarketPriceField() {
		NumberField field = new NumberField();
		field.setName(MARKET_PRICE);
		field.setAllowNegative(false);
		return field;
	}
	public static NumberField getGiveIntegralField() {
		NumberField field = new NumberField();
		field.setName(GIVE_INTEGRAL);
		field.setAllowNegative(true);
		field.setAllowDecimals(false);
		return field;
	}
	public static NumberField getRankIntegralField() {
		NumberField field = new NumberField();
		field.setName(RANK_INTEGRAL);
		field.setAllowNegative(true);
		field.setAllowDecimals(false);
		return field;
	}
	
	public static NumberField getIntegralField() {
		NumberField field = new NumberField();
		field.setName(INTEGRAL);
		field.setAllowNegative(true);
		field.setAllowDecimals(false);
		return field;
	}
	public static CheckBox getIsPromoteField() {
		CheckBox field = new CheckBox();
		field.setName(IS_PROMOTE);
		return field;
	}
	public static NumberField getPromotePriceField() {
		NumberField field = new NumberField();
		field.setName(PROMOTE_PRICE);
		field.setAllowNegative(false);
		return field;
	}
	public static DateField getPromoteStartDateField() {
		DateField field = new DateField();
		field.setName(PROMOTE_START_DATE);
		return field;
	}
	public static DateField getPromoteEndDateField() {
		DateField field = new DateField();
		field.setName(PROMOTE_END_DATE);
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
	public static NumberField getWeightField(String fieldTitle) {
		NumberField field = new NumberField();
		field.setName(GOODS_WEIGHT);
		field.setAllowNegative(false);
		return field;
	}
	
	public static NumberField getNumberField(String fieldTitle) {
		NumberField field = new NumberField();
		field.setName(GOODS_NUMBER);
		field.setAllowNegative(false);
		field.setAllowDecimals(false);
		return field;
	}
	
	public static CheckBox getHotSoldField() {
		CheckBox field = new CheckBox();
		field.setName(IS_HOT);
		field.setValueAttribute("true");
		field.setAutoValidate(true);
		return field;
	}
	public static CheckBox getNewAddedField() {
		CheckBox field = new CheckBox();
		field.setName(IS_NEW);
		field.setValueAttribute("true");
		field.setAutoValidate(true);
		return field;
	}
	public static CheckBox getBestSoldField() {
		CheckBox field = new CheckBox();
		field.setName(IS_BEST);
		field.setValueAttribute("true");
		field.setAutoValidate(true);
		return field;
	}
	
	public static CheckBox getIsOnSaleField() {
		CheckBox field = new CheckBox();
		field.setName(IS_ON_SALE);
		field.setValueAttribute("true");
		field.setAutoValidate(true);
		return field;
	}
	
	public static HtmlEditor getDescField() {
		MyHTMLEditor field = new MyHTMLEditor();
		field.setName(GOODS_DESC);

		field.setAutoValidate(true);
		return field;
	}
}
