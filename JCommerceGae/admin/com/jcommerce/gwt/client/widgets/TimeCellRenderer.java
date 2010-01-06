package com.jcommerce.gwt.client.widgets;

import java.util.Date;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.jcommerce.gwt.client.form.BeanObject;

public class TimeCellRenderer implements GridCellRenderer<BeanObject> {
	
	GridView view;
	
	String format = "yy-MM-dd HH:mm:ss";

	public TimeCellRenderer(Grid grid) {
    	this.view = grid.getView();
    }
	public TimeCellRenderer(){
		
	}


	@Override
	public Object render(BeanObject model, String property, ColumnData config,
			int rowIndex, int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {
		if((Long)model.get(property) != 0L ){
			Date dateTime = new Date((Long)model.get(property));
			DateTimeFormat formatter = DateTimeFormat.getFormat(format);
			String timeStr = formatter.format(dateTime);
			return timeStr;
		}
		else{
			return "";
		}
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}

}
