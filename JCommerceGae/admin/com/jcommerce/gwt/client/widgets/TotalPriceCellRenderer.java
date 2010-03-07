package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderGoods;

public class TotalPriceCellRenderer implements GridCellRenderer<BeanObject> {
	
	GridView view;

	public TotalPriceCellRenderer(Grid grid) {
    	this.view = grid.getView();
    }
	public TotalPriceCellRenderer(){
		
	}
	public Object render(BeanObject model, String property, ColumnData config,
			int rowIndex, int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {
		Double price = model.get(IOrderGoods.GOODS_PRICE);
		Long num = model.get(IOrderGoods.GOODS_NUMBER);
		return price * num;
	}
}
