package com.jcommerce.gwt.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.jcommerce.gwt.client.Logger;
import com.jcommerce.gwt.client.form.BeanObject;

public class FormUtils {
    public static void log(String s) {
    	
    	StringBuffer buf = new StringBuffer();
    	buf.append("[FormUtils]:").append(s);
    	Logger.getClientLogger().log(buf.toString());
    	System.out.println(buf.toString());
    }
    
    
	public static Map<String, Object> getPropsFromForm(FormPanel formPanel) {
    	List<Field<?>> fields = formPanel.getFields();
    	
    	Map<String, Object> props = new HashMap<String, Object>();
    	for(Field field:fields) {
    		String name = field.getName();
    		Object value = field.getValue();
    		if(name ==null || value == null) {
    			continue;
    		}
    		// TODO handle CheckBoxes which are special
    		if(field instanceof RadioGroup) {
    			Radio selected = (Radio)value;
    			name = selected.getName();

    			value = selected.getValueAttribute();
    		}
    		if(field instanceof ComboBox) {
    			ComboBox box = (ComboBox)field;
    			String key = box.getValueField();
    			value = ((BeanObject)value).get(key);
    		}
    		log("name: "+field.getName()+", value: ("+value+")");
    		props.put(name, value);
    	}
    	return props;
	}
}
