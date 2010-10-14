package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IFeedback;

public class MsgTypeCellRenderer implements GridCellRenderer<BeanObject>  {

	@Override
	public Object render(BeanObject model, String property, ColumnData config,
			int rowIndex, int colIndex, ListStore<BeanObject> store,
			Grid<BeanObject> grid) {
		
		long msgType = model.get(property);
		if(msgType == IFeedback.TYPE_LEAVEWORD) {
			return "留言";
		} else if(msgType == IFeedback.TYPE_COMPLAINT) {
			return "投诉";
		} else if(msgType == IFeedback.TYPE_ASK) {
			return "询问";
		} else if(msgType == IFeedback.TYPE_AFTERMARKET) {
			return "售后";
		} else {
			return "求购";
		} 
	}

}
