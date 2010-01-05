package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.resources.Resources;

public class CommentForm extends BeanObject implements IComment {
	
	public static String validate(Map<String, Object> props) {
		String error = null;
		
		return error;
		
	}
	
	public static HiddenField<String> getRankField() {
		HiddenField<String> field = new HiddenField<String>();
		field.setName(COMMENT_RANK);
		return field;
	}
	public static TextField<String> getUserNameField() {
		TextField<String> field = new TextField<String>();
		field.setName(USER_NAME);
		return field;
	}
	public static TextField<String> getCommentTypeField() {
		TextField<String> field = new TextField<String>();
		field.setName(COMMENT_TYPE);
		return field;
	}
	public static TextField<String> getIDField() {
		TextField<String> field = new TextField<String>();
		field.setName(PK_ID);
		return field;
	}
	public static TextField<String> getIdValueField() {
		TextField<String> field = new TextField<String>();
		field.setName(ID_VALUE);
		return field;
	}
	public static TextField<String> getIpAddressField() {
		TextField<String> field = new TextField<String>();
		field.setName(IP_ADDRESS);
		return field;
	}
	public static TextField<String> getAddTimeField() {
		TextField<String> field = new TextField<String>();
		field.setName(ADD_TIME);
		return field;
	}
	public static TextField<String> getStatusField() {
		TextField<String> field = new TextField<String>();
		field.setName(STATUS);
		return field;
	}
	public static HiddenField<String> getUserIdField() {
		HiddenField<String> field = new HiddenField<String>();
		field.setName(USER_ID);
		return field;
	}
	public static HiddenField<String> getParentIdField() {
		HiddenField<String> field = new HiddenField<String>();
		field.setName(PARENT_ID);
		return field;
	}
	public static TextArea getContentField(String fieldTitle) {
		TextArea field = new TextArea();
		field.setName(CONTENT);
		field.setAllowBlank(false);
		TextArea.TextFieldMessages tfm = field.new TextFieldMessages();
		tfm.setBlankText(Resources.messages.blankText(fieldTitle));
		field.setMessages(tfm);
		return field;
	}
	public static TextField<String> getEmailField() {
		TextField<String> field = new TextField<String>();
		field.setName(EMAIL);
		return field;
	}
	
}
