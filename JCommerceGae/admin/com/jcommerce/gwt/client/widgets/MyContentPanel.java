package com.jcommerce.gwt.client.widgets;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;

public class MyContentPanel extends ContentPanel{
	
	public void setHeading(String text, String buttonLabel) {
		super.setHeading(text);
		Button button = new Button(buttonLabel);
		head.insertTool(button, 0);
	}
}
