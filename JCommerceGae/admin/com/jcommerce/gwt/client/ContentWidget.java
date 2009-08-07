package com.jcommerce.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public abstract class ContentWidget extends LayoutContainer {
    ContentPanel contentPanel = new ContentPanel();
    
    private Map<String, Widget> widgets = new HashMap<String, Widget>();

    FlexTable table = new FlexTable();
    
    public ContentWidget() {    	
        super();        
        init();
    }

    public ContentWidget(Layout layout) {
        super(layout);
        init();
    }

    public boolean add(Widget panel) {
    	contentPanel.add(panel);
    	return true;
    }
    public boolean add(Component panel) {
    	contentPanel.add(panel);
    	return true;
    }
    public boolean removeMyPanel(Widget panel) {
        contentPanel.remove(panel);
        return true;
    }
    
    private void init() {
    	System.out.println("initlizing... "+this.getClass().getName());
        super.add(contentPanel);
        
        contentPanel.setLayout(new RowLayout(Orientation.VERTICAL));
        
        // Add the name
//        HTML nameWidget = new HTML("<b>"+getName()+"</b>");
        // nameWidget.setStyleName(DEFAULT_STYLE_NAME + "-name");
        contentPanel.setHeaderVisible(false);

        // Add the description
        //HTML descWidget = new HTML(getDescription());
        // descWidget.setStyleName(DEFAULT_STYLE_NAME + "-description");
        // contentPanel.add(descWidget);
        
//        table.setCellSpacing(6);
        
//        contentPanel.add(table);
    }
    
    /**
     * Get the description of this example.
     * 
     * @return a description for this example
     */
    public abstract String getDescription();

    /**
     * Get the name of this example to use as a title.
     * 
     * @return a name for this example
     */
    public abstract String getName();

    protected Button getShortCutButton() {
    	return null;
    }
    protected void refresh() {
    	System.out.println("refresh did nothing!!!!");
    }
   
    protected ContentPanel getContentPanel() {
        return contentPanel;
    }
    
    protected IShopServiceAsync getService() {
        return Utils.getService();
    }
    
    protected abstract PageState getCurState();
    
}