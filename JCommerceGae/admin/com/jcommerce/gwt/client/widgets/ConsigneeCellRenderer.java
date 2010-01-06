package com.jcommerce.gwt.client.widgets;


import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;

public class ConsigneeCellRenderer implements GridCellRenderer<BeanObject> {
    
	GridView view;
	public ConsigneeCellRenderer() {
    }
    

	public Object render(BeanObject model, String property, ColumnData config,
			final int rowIndex, final int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {   
		return model.getString(IOrderInfo.CONSIGNEE) + "[TEL:" + model.getString(IOrderInfo.TEL) + "]<br>" + model.getString(IOrderInfo.ADDRESS);
	}
}

