package com.jcommerce.gwt.client.widgets;


import java.util.Date;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.jcommerce.gwt.client.form.BeanObject;

public class OrderTimeCellRenderer implements GridCellRenderer<BeanObject> {
    
	GridView view;
	
	long addTime;
	String user = null;
	
	public long getAddTime() {
		return addTime;
	}


	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public OrderTimeCellRenderer(Grid grid) {
    	this.view = grid.getView();
    }
    

	public Object render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {   
		
		Date time = new Date(addTime);
		DateTimeFormat formatter = DateTimeFormat.getFormat("MM-dd HH:mm");
		String timeStr = formatter.format(time);
		if(user == null) {
			return timeStr;
		}
		else {
			return user + "<br>" + timeStr;
		}
	}
}

