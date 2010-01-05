package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;

public class MoneyFormatCellRenderer implements GridCellRenderer<BeanObject> {
    
	GridView view;
	double money;
	
	public void setMoney(double money) {
		this.money = money;
	}
	
	public MoneyFormatCellRenderer(Grid grid) {
    	this.view = grid.getView();
    }
    

	public Object render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {   
		
		return "￥" + money;
	}
}

