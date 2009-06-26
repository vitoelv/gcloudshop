package com.jcommerce.gwt.client.widgets;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.Utils;

public class ColumnPanel extends LayoutContainer {
    private Map<String, Widget> widgets;
    private int row;
    FlexTable table;
    
    public ColumnPanel() {
        super();
        init();
    }

    public ColumnPanel(Layout layout) {
        super(layout);
        init();
    }

    private void init() {
        widgets = new HashMap<String, Widget>();
        table = new FlexTable();
        table.setCellSpacing(6);        
        add(table);
        row = 0;
    }
    

    public void createPanel(String name, String label, Widget ctl) {
        if (label != null) {
        	//设置说明文字垂直方向向上对齐
        	table.getCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
            table.setText(row, 0, label);            
        }
        table.setWidget(row, 1, ctl);        
        row++;
        if (name != null && name.trim().length() > 0) {
            widgets.put(name, ctl);
        }
    }
    
    public void clearValues() {
        for (Iterator<String> it = widgets.keySet().iterator(); it.hasNext();) {
            String name = it.next();
            setValue(name, null);
        }
    }
    
    // by leon: a true reset
    public void reset() {
        remove(table);
        init();
    }
    
    public void resetTable() {
        for(int i=0;i<table.getRowCount();i++) {
            table.removeRow(i);
        }
        row=0;
        widgets = new HashMap<String, Widget>();
    }

    
    public void updateValues(Map<String, Object> values) {
    	//System.out.println("----------updateValues("+values);
        if (values == null) {
            throw new IllegalArgumentException("values == null");
        }
        
        for (Iterator<String> it = widgets.keySet().iterator(); it.hasNext();) {
            String name = it.next();            
            Object value = values.get(name);
            setValue(name, value);
        }
    }
    
    public Map<String, Object> getValues() {
        HashMap<String, Object> values = new HashMap<String, Object>();
        for (Iterator<String> it = widgets.keySet().iterator(); it.hasNext();) {
            String name = it.next();
            values.put(name, getValue(name));
        }
        //System.out.println("values:"+values);
        return values;
    }
    
    public Object getValue(String widgetName) {
        Widget widget = widgets.get(widgetName);
        if (widget instanceof NumberField) {
            return ((NumberField)widget).getValue();
        } else if (widget instanceof TextField) {
            return ((TextField)widget).getRawValue();
        } else if (widget instanceof TextBoxBase) {
            return ((TextBoxBase)widget).getText();
        } else if (widget instanceof ListBox) {
            return Utils.getSelectedValue((ListBox)widget);
        } else if (widget instanceof CheckBox) {
            return ((CheckBox)widget).isChecked();
        } else if (widget instanceof DateWidget) {
            return ((DateWidget)widget).getValue();
        } else if (widget instanceof ValueSelector) {
            return ((ValueSelector)widget).getValue();
        } else if (widget instanceof ChoicePanel){
        	return ((ChoicePanel)widget).getValue();
        } else if (widget instanceof Hidden){
            return ((Hidden)widget).getValue();            
        } else if (widget instanceof MultiValueSelector) {
            return ((MultiValueSelector)widget).getValue();
        } else if (widget instanceof FileUploader) {
            return ((FileUploader)widget).getValue();
        } else if (widget == null) {
            System.out.println("Widget not found: "+widgetName);
        } else {
            throw new RuntimeException("Unknown widget: "+widget.getClass().getName());
        }
        
        return null;
    }
    
    public void setValue(String widgetName, Object value) {
        Widget widget = widgets.get(widgetName);
        //System.out.println("setValue: widgetName:"+widgetName+" value:"+value+"　ｗｉｄｇｅｔ："+widget);
        if (widget instanceof TextBoxBase) {
            ((TextBoxBase)widget).setText(value == null ? "" : value+"");
        } else if (widget instanceof ListBox) {
            Utils.setSelectedValue((ListBox)widget, value+"");
        } else if (widget instanceof CheckBox) {
            ((CheckBox)widget).setChecked(value == null ? Boolean.FALSE : (Boolean)value);
        } else if (widget instanceof DateWidget) {
            ((DateWidget)widget).setValue((Date)value);
        } else if (widget instanceof ValueSelector) {
            ((ValueSelector)widget).setValue(value == null ? "" : value+"");
        } else if (widget instanceof ChoicePanel){
        	((ChoicePanel)widget).setSelectValue(String.valueOf(value));  
        } else if (widget instanceof Hidden){
            ((Hidden)widget).setValue(value==null?"":value+"");            
        } else if (widget instanceof MultiValueSelector) {
            ((MultiValueSelector)widget).setValue((Collection)value);
        } else if (widget instanceof FileUploader) {
            ((FileUploader)widget).setValue(value+"");
        } else if (widget instanceof Label){
        	((Label)widget).setText(String.valueOf(value));
        }else if (widget == null) {
            System.out.println("Widget not found: "+widgetName);
        } else {
            throw new RuntimeException("Unknown widget: "+widget.getClass().getName());
        }
    }
}
