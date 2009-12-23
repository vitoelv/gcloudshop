package com.jcommerce.gwt.client.form;

import java.util.Date;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.resources.Resources;

public class AdminUserForm extends BeanObject implements IAdminUser {
	
	public AdminUserForm(){
		super();
	}
	public AdminUserForm(String modelName, Map<String, Object>props) {
		super(modelName,props);
	}
	public static String validate(Map<String, Object> props) {
		String error = null;
		String password = (String)props.get(PASSWORD);
		String confirmPassword = (String)props.get("confirmPassword");
		String oldPassword = (String)props.get("oldPassword");
		String email = (String)props.get("email");

		if( oldPassword == null ){
			if(!password.equals(confirmPassword)){
				error = "两次输入密码不一致\n";
			}
		}
		else{
			String newPassword = (String)props.get("newPassword");
			if(newPassword != null && !newPassword.equals(confirmPassword)){
				error = "两次输入密码不一致\n";
			}
		}
		
		return error;
		
	}
	
	public static TextField<String> getUserNameField(String fieldTitle){
		TextField<String> field = new TextField<String>();
		field.setName(USER_NAME);
		field.setMaxLength(10);
//		field.setMinLength(4);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);	
		return field;
	}
	
	public static TextField<String> getPasswordField(String fieldTitle){
		TextField<String> field = new TextField<String>();
		field.setName(PASSWORD);
		field.setMaxLength(10);
//		field.setMinLength(6);
		field.setPassword(true);
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);	
		return field;
	}
	
	public static TextField<String> getComfirmPasswordField(String fieldTitle){
		TextField<String> field = new TextField<String>();
		field.setName("confirmPassword");
		field.setMaxLength(10);
//		field.setMinLength(6);
		field.setPassword(true);
		field.setAutoValidate(true);
		field.setAllowBlank(true);
		return field;
	}
	
	public static TextField<String> getEmailField(String fieldTitle){
		TextField<String> field = new TextField<String>();
		field.setName(EMAIL);
		field.setRegex("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$");
		field.setAutoValidate(true);
		field.setAllowBlank(false);
		TextField<String>.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		TextField<String>.TextFieldMessages tfm2 = field.new TextFieldMessages();
		tfm2.setRegexText(Resources.messages.emailFormatText());
		field.setMessages(tfm2);	
		return field;
	}
	
	public static TextField<String> getOldPasswordField(String fieldTitle){
		TextField<String> field = new TextField<String>();
		field.setName("oldPassword");
		field.setMaxLength(10);
//		field.setMinLength(6);
		field.setPassword(true);
		field.setAutoValidate(true);
		field.setAllowBlank(true);
		return field;
	}
	
	public static TextField<String> getNewPasswordField(String fieldTitle){
		TextField<String> field = new TextField<String>();
		field.setName("newPassword");
		field.setMaxLength(10);
//		field.setMinLength(6);
		field.setPassword(true);
		field.setAutoValidate(true);
		field.setAllowBlank(true);
		return field;
	}
	
	public static HiddenField<String> getAddTimeField(){
		HiddenField<String> field = new HiddenField<String>();
		field.setName(ADD_TIME);
		field.setValue(new Date().getTime()+"");
		return field;
	}
	
	public static HiddenField<String> getActionListField(){
		HiddenField<String> field = new HiddenField<String>();
		field.setName(ACTION_LIST);
		return field;
	}
	
	public static HiddenField<String> getLastIpField(){
		HiddenField<String> field = new HiddenField<String>();
		field.setName(LAST_IP);
		return field;
	}
	
	public static HiddenField<String> getLastLoginField(){
		HiddenField<String> field = new HiddenField<String>();
		field.setName(LAST_LOGIN);
		return field;
	}
	
	public static HiddenField<String> getLangTypeField(){
		HiddenField<String> field = new HiddenField<String>();
		field.setName(LANG_TYPE);
		return field;
	}
	
	public static HiddenField<String> getNavListField(){
		HiddenField<String> field = new HiddenField<String>();
		field.setName(NAV_LIST);
		return field;
	}
	
	public static HiddenField<String> getTodoListField(){
		HiddenField<String> field = new HiddenField<String>();
		field.setName(TODOLIST);
		return field;
	}
}
