package com.jcommerce.gwt.client.widgets;


import java.util.Date;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.jcommerce.gwt.client.form.BeanObject;

public class ConsigneeCellRenderer implements GridCellRenderer<BeanObject> {
    
	GridView view;
	
	String consignee;
	String tel;
	String address;

	public String getConsignee() {
		return consignee;
	}


	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel == null ? "" : tel;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address == null ? "" : address;
	}


	public ConsigneeCellRenderer(Grid grid) {
    	this.view = grid.getView();
    }
    

	public Object render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {   
		
		return consignee + "[TEL:" + tel + "]<br>" + address;
	}
}

