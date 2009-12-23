package com.jcommerce.gwt.client.form;

import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.resources.Resources;

public class UserForm  extends BeanObject implements IUser{

	public UserForm() {
		super();
	}
	
	public static TextField<String> getNameField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(USER_NAME);
		field.setMaxLength(10);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));		
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getEmailField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setName(EMAIL);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		field.setRegex("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		tfm.setRegexText(Resources.constants.User_wrongemail());
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getMsnField() {
		TextField<String> field = new TextField<String>();
		field.setName(MSN);
		return field;
	}
	
	public static TextField<String> getPasswordField(String fieldTitle) {
		TextField<String> field = new TextField<String>();
		field.setPassword(true);
		field.setName(PASSWORD);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	
	public static NumberField getCreditField() {
		NumberField field = new NumberField();
		field.setName(CREDIT_LINE);
		field.setAllowDecimals(false);
		field.setAllowNegative(false);
		return field;
	}
	
	public static TextField<String> getQqField() {
		TextField<String> field = new TextField<String>();
		field.setName(QQ);
		field.setRegex("^[0-9]*$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		//tfm.setRegexText("QQ号码只能为数字");
		tfm.setRegexText(Resources.constants.User_wrongqq());
		field.setMessages(tfm);
		return field;
	}

	public static TextField<String> getOfficePhoneField() {
		TextField<String> field = new TextField<String>();
		field.setName(OFFICE_PHONE);
		field.setRegex("^[0-9]*$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
	//	tfm.setRegexText("电话号码只能为数字");
		tfm.setRegexText(Resources.constants.User_wrongphone());
		field.setMessages(tfm);
		return field;
	}

	public static TextField<String> getHomePhomeField() {
		TextField<String> field = new TextField<String>();
		field.setName(HOME_PHONE);
		field.setRegex("^[0-9]*$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
//		tfm.setRegexText("电话号码只能为数字");
		tfm.setRegexText(Resources.constants.User_wrongphone());
		field.setMessages(tfm);
		return field;
	}
	
	public static TextField<String> getMobilePhomeField() {
		TextField<String> field = new TextField<String>();
		field.setName(MOBILE_PHONE);
		field.setRegex("^[0-9]*$");
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
//		tfm.setRegexText("电话号码只能为数字");
		tfm.setRegexText(Resources.constants.User_wrongphone());
		field.setMessages(tfm);
		return field;
	}

	public static DateField getPromoteEndDateField() {
		DateField field = new DateField();
		field.setName(BIRTHDAY);
		return field;
	}

	public static TextField<String> getQuestionField() {
		TextField<String> field = new TextField<String>();
		field.setName(QUESTION);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getAnswerField() {
		TextField<String> field = new TextField<String>();
		field.setName(ANSWER);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getUserMoneyField() {
		TextField<String> field = new TextField<String>();
		field.setName(USER_MONEY);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getFrozenMoneyField() {
		TextField<String> field = new TextField<String>();
		field.setName(FROZEN_MONEY);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getAddressIdField() {
		TextField<String> field = new TextField<String>();
		field.setName(ADDRESS_ID);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getRegTimeField() {
		TextField<String> field = new TextField<String>();
		field.setName(REG_TIME);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getLastLoginField() {
		TextField<String> field = new TextField<String>();
		field.setName(LAST_LOGIN);
		field.setVisible(false);
		return field;
	}

	public static TextField<String> getLastIpField() {
		TextField<String> field = new TextField<String>();
		field.setName(LAST_IP);
		field.setVisible(false);
		return field;
	}
}
