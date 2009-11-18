package com.jcommerce.gwt.client;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.util.GWTUtils;

public abstract class ContentWidget extends ContentPanel {
	
    public void log(String s) {
    	
    	StringBuffer buf = new StringBuffer();
    	buf.append("[").append(this.getClass().getName()).append("]:").append(s);
    	Logger.getClientLogger().log(buf.toString());
    	System.out.println(buf.toString());
    }
	
    public ContentWidget() {    	
        super();        
        init();
    }

//    public ContentWidget(Layout layout) {
//        super(layout);
//        init();
//    }

//    public boolean add(Widget panel) {
//    	TableData td = new TableData();
//    	td.setWidth("100%");
//    	contentPanel.add(panel);
//    	return true;
//    }
    public boolean add(Component panel) {
    	TableData td = new TableData();
    	td.setWidth("100%");
    	add(panel, td);
    	return true;
    }
    public boolean removeMyPanel(Widget panel) {
        remove(panel);
        return true;
    }
    
    private void init() {
    	System.out.println("initlizing... "+this.getClass().getName());
//    	this.setLayout(new TableLayout(1));
//    	TableData td = new TableData();
//    	td.setWidth("100%");
    	TableLayout tl = new TableLayout(1);
        tl.setCellSpacing(20);
        setLayout(tl);

//        this.setBodyBorder(false);
        
        // Add the name
//        HTML nameWidget = new HTML("<b>"+getName()+"</b>");
        // nameWidget.setStyleName(DEFAULT_STYLE_NAME + "-name");
        setHeaderVisible(false);

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
   
//    protected ContentPanel getContentPanel() {
//        return contentPanel;
//    }
    

    protected abstract PageState getCurState();

	@Override
	protected void onRender(Element parent, int pos) {
		// TODO Auto-generated method stub
		super.onRender(parent, pos);
        ContentPanel title = new ContentPanel();
        TableLayout tl = new TableLayout(2);
        title.setLayout(tl);
        title.setHeaderVisible(false);
        tl.setCellPadding(10);
//        title.setSpacing(10);
        Html html = new Html("<B>"+Resources.constants.ManagementCenter()+"</B> - "+this.getName());
        TableData td = new TableData();
        td.setHorizontalAlign(Style.HorizontalAlignment.LEFT);
        td.setWidth("50%");
        title.add(html, td);
        
        td = new TableData();
        td.setHorizontalAlign(Style.HorizontalAlignment.RIGHT);
        td.setWidth("50%");
        if(getShortCutButton()!=null) {
        	title.add(this.getShortCutButton(), td);
        } else {
        	title.add(new Html("TODO: override getShortCutButton"), td);
        }
        add(title);
	}
    
}
